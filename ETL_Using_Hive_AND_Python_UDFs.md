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
  
## To display using column name  
```SQL
hive> set hive.cli.print.header=true;
hive> select * from table_name;
```  
  
## Deleting all tables in a database:  
```SQL
hive -e 'use sample_db;show tables' | xargs -I '{}' hive -e 'use sample_db;drop table {}'
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

or
beeline -u $beeline_connect --showHeader=false --outputformat=dsv --delimiterForDSV='\n' -e "use $database; show tables;" \
        | xargs -P 10 -I {} bash -c 'impala-shell -k -i $impala_connection_string --ssl --ca_cert=$impala_cert_path -q "COMPUTE STATS $database.{}"'
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
It is another Hive join optimization technique where all the tables need to be bucketed and sorted. In this case joins are very efficient because they require a simple merge of the presorted tables.  
Let us create bucketed tables from our existing tables i.e.; emp and dept. Before creating bucketed table, you need to set below properties.  
```SQL
hive> set hive.enforce.bucketing=true;
hive> set hive.enforce.sorting=true;

create table buck_emp(
    id int,
    name string,
    salary int)
CLUSTERED BY (id)
SORTED BY (id)
INTO 4 BUCKETS;
We need to use regular INSERT statement to insert into bucketed table.
INSERT OVERWRITE TABLE buck_emp
SELECT * FROM emp;
```  
Similarly, create another bucketed table from ‘dept’ table and inserting into it.  
```SQL
create table buck_dept(
id int,
dept_name string)
CLUSTERED BY (id)
SORTED BY (id)
INTO 4 BUCKETS;
 INSERT OVERWRITE TABLE buck_dept
SELECT * FROM dept;
```  
Now the stage is set to perform SMB Map Join to optimize Hive joining. Again, make some changes in properties to perform SMB Map join.  
```SQL
hive>set hive.enforce.sortmergebucketmapjoin=false;
hive>set hive.auto.convert.sortmerge.join=true;
hive>set hive.optimize.bucketmapjoin = true;
hive>set hive.optimize.bucketmapjoin.sortedmerge = true;
hive>set hive.auto.convert.join=false;  // if we do not do this, automatically Map-Side Join will happen
SELECT u.name,u.salary FROM buck_dept d  INNER JOIN buck_emp u ON d.id = u.id;
```  
  
## ACID in Hive  
```SQL
set hive.support.concurrency=true;
set hive.enforce.bucketing=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.txn.manager=org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;

create table test_delete ( id int, name string )
CLUSTERED BY (id) INTO 2 BUCKETS STORED AS ORC
TBLPROPERTIES ("transactional"="true",
"compactor.mapreduce.map.memory.mb"="3072",     -- specify compaction map job properties
"compactorthreshold.hive.compactor.delta.num.threshold"="20",  -- trigger minor compaction if there are more than 20 delta directories
"compactorthreshold.hive.compactor.delta.pct.threshold"="0.5" -- trigger major compaction if the ratio of size of delta files to size of base files is greater than 50%);

INSERT INTO TABLE test_delete VALUES (31, 'aaa31');
INSERT INTO TABLE test_delete VALUES (32, 'aaa32');
INSERT INTO TABLE test_delete VALUES (33, 'aaa33');
INSERT INTO TABLE test_delete VALUES (34, 'aaa34');
INSERT INTO TABLE test_delete VALUES (35, 'aaa35');

-- or can use
--INSERT INTO TABLE test_delete VALUES (31, 'aaa31'), (32, 'aaa32'), (33, 'aaa33'), (34, 'aaa34'), (35, 'aaa35'), (35, 'aaa35');

hive> select * from test_delete;
OK
31 aaa31
32 aaa32
33 aaa33
34 aaa34
35 aaa35

delete from test_delete where name = 'aaa33';
--deleting keys in Transactional table via data from non transactional table  
--DELETE FROM transactional_table WHERE transactional_table.key IN (SELECT key FROM non_transactional_table);  
--UPDATE test_delete SET id = 333 where name = "aaa33";

--combining Update and Insert using Merge  
--merge into customer_partitioned
-- using all_updates on customer_partitioned.id = all_updates.id
-- when matched then update set
--   email=all_updates.email,
--   state=all_updates.state
-- when not matched then insert
--   values(all_updates.id, all_updates.name, all_updates.email,
--   all_updates.state, all_updates.signup);

hive> select * from test_delete;
OK
32 aaa32
34 aaa34
31 aaa31
35 aaa35
```
make to set the "hive.in.test" in hive-site.xml to "true".  
```xml
 <property>
  <name>hive.in.test</name>
  <value>true</value>
 </property>
```    
and optionally  
```xml
 <property>
  <name>hive.support.concurrency</name>
  <value>true</value>
 </property>
 <property>
  <name>hive.enforce.bucketing</name>
  <value>true</value>
 </property>
 <property>
  <name>hive.exec.dynamic.partition.mode</name>
  <value>nonstrict</value>
 </property>
 <property>
  <name>hive.txn.manager</name>
  <value>org.apache.hadoop.hive.ql.lockmgr.DbTxnManager</value>
 </property>
 <property>
  <name>hive.compactor.initiator.on</name>
  <value>true</value>
 </property>
 <property>
  <name>hive.compactor.worker.threads</name>
  <value>2</value>
 </property>
```
  
## Serdes in Hive
JSON: org.apache.hive.hcatalog.data.JsonSerDe  
TextFile: STORED AS TEXTFILE;  
Parquet: ROW FORMAT SERDE 'parquet.hive.serde.ParquetHiveSerDe'  STORED AS INPUTFORMAT 'parquet.hive.DeprecatedParquetInputFormat' OUTPUTFORMAT 'parquet.hive.DeprecatedParquetOutputFormat';  [OR] STORED AS PARQUET;  
HBase: STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,details:carrier_desc") TBLPROPERTIES ("hbase.table.name" = "carriers")  
Transactional Table: TBLPROPERTIES ("transactional"="true")  
  
# Data Ingestions   
For all Data Ingestions a Temporary or staging tables(holds current data)[orders_stg] and Final tables(holds all data)[orders] is neccessary.  
Optionally can use internall hive columns like input__file__name, current_timestamp for better tracking purpose.  
## Complete Load:   
In this method, entire table/partition is truncated and then added with new full set of data.  
```sql
with new_data as(select * from orders_stg)
insert overwrite table orders partition(order_date)
select `(order_date)?+.+`, input__file__name, current_timestamp as ingestion_ts, cast(order_date as date) as part from new_data
```  
## Append Load:  
New data is appended to existing data for each batch of data refresh.  
```sql
with new_data as(select * from orders_stg)
insert overwrite table orders partition(order_date)
(select * from orders where order_date in
(select distinct order_date from new_data)  --existing data
Union all
(select `(order_date)?+.+`, input__file__name, current_timestamp as ingestion_ts, cast(order_date as date) as part from new_data )

[OR]

with new_data as(select * from orders_stg)
insert into table orders partition(order_date)
select `(order_date)?+.+`, input__file__name, current_timestamp as ingestion_ts, cast(order_date as date) as part from new_data 
```  
## Insert or Update Ingestion: 
In this method, data with new key is inserted to the table, whereas if the relevant key already exists in partition/table then record is updated with latest info.  
```sql
```  
Also, refer to ## ACID in Hive  
