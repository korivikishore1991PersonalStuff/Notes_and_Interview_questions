# Prmary Table creation:  
## Creation of a Table in Hive:  
```SQL
CREATE TABLE pageviews (userid STRING COMMENT 'A sample comment on a column', 
link STRING, 
came_from STRING) 
COMMENT 'This is the a sample table for ETL'
PARTITIONED BY (datestamp STRING) 
CLUSTERED BY (userid) INTO 10 BUCKETS
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
```  
  
## Insertion of values using static partition:  
```SQL
INSERT INTO TABLE pageviews PARTITION (datestamp = '2014-09-23')
  VALUES ('jsmith', 'mail.com', 'sports.com'), ('jdoe', 'mail.com', null);
  ```  
    
## Insertion of values using Dynamic partition:  
```SQL
set hive.exec.dynamic.partition=true
  
  
set hive.exec.dynamic.partition.mode=nonstrict;


INSERT INTO TABLE pageviews PARTITION (datestamp)
  VALUES ('tjohnson', 'sports.com', 'finance.com', '2014-09-23'), ('tlee', 'finance.com', null, '2014-09-21');
  ```  
  
## Testing:  
```SQL
select * from pageviews where came_from is NULL; 
```   
  
# External Staging table creation:  
  
## Input Preparations:  
```SQL
cat /tmp/part1
xxx,xx.com,,2018-09-10
yyy,yyy.com,sports.com,2018-09-10
jdoe,mail.com,,2018-09-10
tjohnson,sports.com,finance.com,2018-09-10

cat /tmp/part2
xxx,xx.com,,2018-09-10
yyy,yyy.com,sports.com,2018-09-10
jdoe,mail.com,,2018-09-10
tjohnson,sports.com,finance.com,2018-09-10

hadoop fs -mkdir /user/cloudera/hivetabledir

hadoop fs -copyFromLocal /tmp/part* /user/cloudera/hivetabledir

hadoop fs -ls /user/cloudera/hivetabledir

hadoop fs -cat /user/cloudera/hivetabledir/part*
xxx,xx.com,,2018-09-10
yyy,yyy.com,sports.com,2018-09-10
jdoe,mail.com,,2018-09-10
tjohnson,sports.com,finance.com,2018-09-10
xxx,xx.com,,2018-09-10
yyy,yyy.com,sports.com,2018-09-10
jdoe,mail.com,,2018-09-10
tjohnson,sports.com,finance.com,2018-09-10
```
  
## External Table Creation from data at HDFS location:  
```SQL
CREATE EXTERNAL TABLE pageviewstaging(userid STRING, 
link STRING, 
came_from STRING,
datestamp STRING)
COMMENT 'This is the staging table'
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '/user/cloudera/hivetabledir';
```
  
## Testing:  
```SQL
select * from pageviewstaging;
```
  
# Partitioned External Table  
## Input Preparations for Partitioned External Table:  
```SQL
[cloudera@quickstart ~]$ cat /tmp/partitionedpart1
1,1,1
2,2,2
3,3,3
4,4,4
5,5,5
[cloudera@quickstart ~]$ cat /tmp/partitionedpart2
6,6,6
7,7,7
8,8,8
9,9,9
10,10,10
[cloudera@quickstart ~]$ hadoop fs -mkdir /user/cloudera/hiveparttabledir
[cloudera@quickstart ~]$ hadoop fs -mkdir /user/cloudera/hiveparttabledir/date=2010-02-22
[cloudera@quickstart ~]$ hadoop fs -mkdir /user/cloudera/hiveparttabledir/date=2011-02-22
[cloudera@quickstart ~]$ hadoop fs -copyFromLocal /tmp/partitionedpart1 /user/cloudera/hiveparttabledir/date=2010-02-22
[cloudera@quickstart ~]$ hadoop fs -copyFromLocal /tmp/partitionedpart2 /user/cloudera/hiveparttabledir/date=2011-02-22
```
  

## External Partitioned Table Creation from data at HDFS location:  
```SQL
CREATE EXTERNAL TABLE user (
  userId INT,
  type INT,
  level INT
)
COMMENT 'User Infomation'
PARTITIONED BY (date String)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '/user/cloudera/hiveparttabledir/';
```
  
## Neccessary Partitions repairs:  
```SQL
MSCK REPAIR TABLE user;
```

## Testing:  
```SQL
select * from user;
```
   
# Staging data to Primary Table  
  
## Loading data into Primary Partitioned Table from external staging table:  
```SQL
insert into table pageviews partition(datestamp)
select *
from 
pageviewstaging;
```  
  
# UDF's in Hive  
  
## Prepare Python Script  
```SQL
[cloudera@quickstart ~]$ cat /tmp/udf.py
import sys
import datetime
for line in sys.stdin:
  line = line.strip()
  fname , lname = line.split('\t')
  l_name = lname.lower()
  print '::'.join([fname, str(l_name)])
```
  
## Prepare Input Data  
```SQL
[cloudera@quickstart ~]$ cat /tmp/mytableData
x	x
y	y
z	z
```
  
## Copy input data to HDFS  
```SQL
[cloudera@quickstart ~]$ hadoop fs -copyFromLocal /tmp/mytableData /user/cloudera/hivetabledir/
```
  
## Create table in Hive  
```SQL
hive>>CREATE table IF NOT EXISTS mytable(
fname STRING,
lname STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE;
```
  
## Load data into Hive table using Hive  
```SQL
hive>>LOAD DATA INPATH '/user/cloudera/hivetabledir/mytableData' OVERWRITE INTO TABLE mytable;
```
  
## add python file into Hive  
```SQL
hive>>add FILE /tmp/udf.py;
```
  
## Apply UDF's on table into Hive  
```SQL
hive>>SELECT TRANSFORM(fname, lname) USING 'python udf.py' AS (fname, l_name) FROM mytable;
```
  
# Beeline  
## Querying using variable and bash in Beeline  
```sql
var="AS"

beeline -u $beeline_connect -e "select t.x, count(t.x) as Row_Count from db.t where t.x=\"${var}\" group by t.x;" &> Shell_retrival.log
```  
  
## Querying using --hivevar and sql file in Beeline  
```sql
#vi /home/dir/Script/query.hql
#select t.x, count(t.x) as Row_Count from db.t where t.x="${var}" group by t.x;
beeline -u $beeline_connect --hivevar var="AS" -f /home/dir/Script/query.hql &> query_file_retrival.log
```  
  
# Hive Joins  
when these two tables are joined it is important that the larger table comes last in the query. Or, you can also explicitly tell Hive which table it should stream.    
```SQL
SELECT /*+ STREAMTABLE(emp) */ emp.id,name,salary,dept_name FROM small_table JOIN large_table ON (dept.id = emp.id);
```  
Check the below parameters for joins in spark:  
spark.sql.shuffle.partitions(which is by default 200)  
spark.default.parallelism(works for raw RDD)  
df.repartition(numOfPartitions)  
  
## Map-side joins  
Map-side joins can be tricky to configure and use properly. Here are a few pointers.  
### Auto-convert to map-side join whenever possible  
Set the property hive.auto.convert.join to true in your Hive config and Hive will automatically try to convert the join to a map-side join, as long as the table fits below a certain size threshold. You can configure the maximum size with the property hive.smalltable.filesize. This will tell Hive what file size (or below) constitutes a small table. It's written in bytes expressed as a long (for example, 25000000L = 25M).  
```SQL
hive> set hive.auto.convert.join=true;
hive> set hive.auto.convert.join.noconditionaltask=true;
```
Also consider setting hive.hashtable.max.memory.usage, which tells the map task to terminate if it requires more than the configured memory percentage.  
### Map-join behavior 
If you omit ```/*+ MAPJOIN() */``` and rely on auto-convert, it can be difficult to follow what Hive is doing to optimize the join. Following are some tips:  
TableFoo LEFT OUTER JOIN TableBar: Try to convert TableBar to a hash table  
TableFoo RIGHT OUTER JOIN TableBar: Try to convert TableFoo to a  hash table  
TableFoo FULL OUTER JOIN TableBar: Framework cannot map join full outer joins  
    
## Hive Bucket Join  
Buckets add an additional structure to the way the data is organized across HDFS. Entries with identical values in the columns using bucketing are stored in the same file partitions. Tables bucketed based on columns that include the join key can make advantage of Hive Bucket Joins.  
The trick of Bucket Join in Hive is that the join of bucketed files having the same join key can efficiently be implemented as map-side joins. As previously explained do map-side joins impose strict constrains on the way the data needs to be organized. It needs to be sorted and partitioned identically. This is what can be achieved with bucketed Hive tables.  
