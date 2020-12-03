# Prmary Table creation:  
## Important Hive NoteMarks  
```sql
comments: using --
Parameter tuning: using SET
handeling special charecters in Coloumn_name: using `` like `Col$1/_2`
Tuning Table Properties: using ALTER command
```  
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
drop database sample_db cascade;
[OR]
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
  
# ACID in Hive  
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
multiple characters as a separator: ```sql 'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe' WITH SERDEPROPERTIES ("field.delim"="##")```  
RegexSerDeMethod implementation: ```sql row format serde 'org.apache.hadoop.hive.serde2.RegexSerDe' with serdeproperties('input.regex'='(.*)\\|\\|(.*)','output.format.string'='%1$s %2$s')```  
TextFile: STORED AS TEXTFILE;  
Parquet: ROW FORMAT SERDE 'parquet.hive.serde.ParquetHiveSerDe'  STORED AS INPUTFORMAT 'parquet.hive.DeprecatedParquetInputFormat' OUTPUTFORMAT 'parquet.hive.DeprecatedParquetOutputFormat';  [OR] STORED AS PARQUET;  
HBase: STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,details:carrier_desc") TBLPROPERTIES ("hbase.table.name" = "carriers")  
Transactional Table: TBLPROPERTIES ("transactional"="true")  
### Serdes Property modification  
A CSV sample data processing  
```sql
CREATE EXTERNAL TABLE test_csv_opencsvserde (
  id STRING,
  name STRING,
  location STRING,
  create_date STRING,
  create_timestamp STRING,
  longitude STRING,
  latitude STRING
) 
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
with serdeproperties(
"separatorChar"=",",
"quoteChar"="\"",
"escapeChar"="\\"
)
STORED AS TEXTFILE LOCATION 'oss://test-bucket-julian-1/test_csv_serde_1'
```  
  
# Data Ingestions   
For all Data Ingestions a Temporary or staging tables(holds current data)[orders_stg] and Final tables(holds all data)[orders] is neccessary.  
Optionally can use internall hive columns like input__file__name, current_timestamp for better tracking purpose.  
At Source Orders table is in JSON format and contains below fields.  
1)   ID  
2)   Order_ID  
3)   Product_ID  
4)   Product_Name  
5)   Quanity  
6)   Product_Price  
7)   Customer_Id  
8)   Customer_Name  
9)   Order_Date  
Stage table:  
```sql 
CREATE EXTERNAL TABLE IF NOT EXISTS orders_stg (
id string,
customer_id string,
customer_name string,
product_id string,
product_name string,
product_price string,
quantity string,
order_date string,
src_update_ts string
)
ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'LOCATION '/user/dks/datalake/orders_stg'; //source data location  
```  
Final Table:  
```sql
CREATE TABLE IF NOT EXISTS orders (
id bigint,
customer_id string,
customer_name string,
product_id int,
product_name string,
product_price decimal(12,2),
quantity int,
src_update_ts timestamp,
src_file string,
ingestion_ts timestamp
)
PARTITIONED BY (order_date date)
ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
LOCATION '/user/dks/datalake/orders';
```  
## Complete Load or Overwrites:   
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
### Using Over(), rank_no and row_number() OR Using inner_join and union
In this method, data with new key is inserted to the table, whereas if the relevant key already exists in partition/table then record is updated with latest info.  
New data is added or updated to the existing partition/table based on a row key. Data is appended to the table if the record contains new “key” otherwise existing record is updated with latest information. A row level timestamp is must for both current increamental data and full table data for this traditional solution to work.     
This option is only possible when a row in the table can be identified uniquely using one or more columns. In orders table, “ID” field is row key. src_update_ts field indicates record time stamp.  
Method 1: use over() function to find out latest record that needs to be updated/inserted.  
```sql
with new_data as(select * from orders_stg)
insert overwrite table orders partition(order_date)
select `(rank_no)?+.+` from (
(
select *, row_number() over(partition by order_date,ID order by src_update_ts desc) rank_no from(
select * from orders where order_date in (select distinct order_date from new_data)
Union
select `(order_date)?+.+`, input__file__name, current_timestamp as ingestion_ts, cast(order_date as date) as part from new_data
)  update_data
where rank_no=1 distribute by order_date order by ID
--rank_no=1 indicates recent record
--`(order_date)?+.+` :  This selects all the columns except order_date
```   
Method 2: In this method, a temporary table is created by merging data from both original and incremental tables. Next data in temporary table is filtered to select only the records with latest timestamp. Last step is original table is dropped and temporary table is renamed with original table name. This approach is more suitable non-partition tables. For example if orders table is not partitioned.  
```sql
--Step1=> RECONCILE VIEW: This view combines record set from both original table and incremental data.
With new_data as (select * from orders_stg)
create view recon_orders as (
   select final_data.* from
   (select * from orders where order_date Union all
select `(order_date)?+.+`, input__file__name, current_timestamp as ingestion_ts, cast(order_date as date) as part from new_data
)  final_data
inner join (select order_date, ID, max(src_update_ts) as max_src_upd_ts  from   (select order_date, id, src_update_ts from orders   union all
select order_date,id, src_update_ts from new_data) all_data )  max_data on final_data.id=max_data.id and final_data.src_update_ts=max_data.max_src_upd_ts
)
--Step2 =>Insert into temp table
drop table temp_orders;
create table temp_orders as
select * from recon_orders  ---this view is created in step1
--Step3=> Purge
drop table orders;
ALTER TABLE recon_orders RENAME TO orders;
```
Refer to https://medium.com/datakaresolutions/hive-design-patterns-783d6104d852 and also, refer to "ACID in Hive" above.  
  
### Update Hive Partition   
```sql
ALTER TABLE <db_name>.<table_name> PARTITION(year = 2012) --table name can also be altered using ALTER TABLE recon_orders RENAME TO orders;
SET LOCATION 'hdfs://user/user1/some_table/2012';
--Update MetaStore
Msck repair table <db_name>.<table_name>
```  
## Data Deletion:    
### Drop/Deleting Hive Partition  
```sql
ALTER TABLE some_table DROP IF EXISTS PARTITION(year = 2012);
--This command will remove the data and metadata for this partition. The drop partition will actually move data to the .Trash/Current directory if Trash is configured, unless PURGE is specified, but the metadata is completely lost.
--Update MetaStore
Msck repair table <db_name>.<table_name>
```  
  
### Droping Data in main_table based on Keys from staging_table  
```sql
insert overwrite table main_table partition (c,d)
select t2.a, t2.b, t2.c,t2.d  from staging_table t2 left outer join main_table t1 on t1.a=t2.a;
```  
### Droping Data in main_table based on individual Keys 
```sql
--Create temp table same as target table:
Create table main_table_temp like main_table;
--Insert the subtracted data from main_table to temp table
INSERT INTO main_table_temp
select * FROM main_table 
WHERE  row_key NOT IN (SELECT row_key 
              FROM   main_table 
              WHERE  row_key = 'user_specified_value');
--Insert the subtracted data from temp table into the main table
INSERT OVERWRITE TABLE main_table select * from main_table_temp;   
--Delete the temp table
Drop table main_table_temp;
```  
  
# Handeling Unstructured Data JSON or XML  
## XML  
XML raw data with tags can be parsed using xpath. Like xpath(main_column_name, 'tag1/tag1.2../dataType()').  
```sql
create table xmlsample(str string); --table creation with all the line as Single String
load data local inpath '/path/to/xmlFile.xml' into xmlsample; --loading data into the table
select xpath(str, '/tag1/tag1.1/text()'), xpath(str, '/tag1/tag1.2/text()') from xmlsample;
```  
## JSON  
ETL on JSON can be done using 2 methods.  
1) loading entrie JSON as String and parsing the string JSON using get_json_object() --For COmplicated JSON's  
2) Using Serdes @ org.apache.hive.hcatalog.data.JsonSerDe or org.apache.hive.serde2.JsonSerDe by creating tables Using STRUCT and ARRAY --For Structured and Semi StructuredJSON's  
3) flattening Complex Dataobjects using a combiation of explode or Posexpolde with LATERAL VIEW. --For Analysis  
### 1)loading entrie JSON as String and parsing the string JSON using get_json_object()  
Usage is get_json_object(jsonString, '$.key') --Where, jsonString is a valid json string. $.key is a key of a value that you are trying to extract.  
```sql
>select get_json_object(jvalue, '$.name.pin\[1]') 
>from  (select '{"name":{"pin":["123456","654321"]}}' as jvalue) as q;
OK
+---------+--+
|   _c0   |
+---------+--+
| 654321  |
+---------+--+
1 row selected (0.087 seconds)
```  
### 2)Using Serdes @ org.apache.hive.hcatalog.data.JsonSerDe or org.apache.hive.serde2.JsonSerDe by creating tables Using STRUCT and ARRAY  
JsonSerDe is based on the text SerDe and each newline is considered as a new record. Only Valid JSON formats will be parsed.  
#### Flat JSON((Key: value) 
```sql
>cat Data.json
{"world_rank": 1,"country": "China","population": 1388232694,"World": 0.185},
{"world_rank": 2,"country": "India","population": 1342512706,"World": 0.179},
>CREATE external TABLE if not exists world_population (
>  world_rank INT,
>  country STRING,
>  population BIGINT,
>  world DOUBLE
> )
>ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
>Location `/tmp/Data.json`;
```  
#### JSON with Special Charecters in Keys(Key: value and struct)  
```sql
>hadoop fs -cat /tmp/record/*
{"Organisation": "Wells Fargo","_id":{"$oid":"5f82f"},"age":{"$numberLong":"35"}}  
>create external table if not exists mongoTestDCT(Organisation STRING,
>`_id` struct<`$oid`: string>,
>age struct<`$numberLong`: string>)
>ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
>LOCATION '/tmp/record';
>select * from mongoTestDCT;
mongoTestDCT.Organisation mongoTestDCT._id mongoTestDCT.age
Wells Fargo               {"$oid":"5f82f"}  {"$numberLong":"35"}
>select `_id`.`$oid` from mongoTestDCT
$oid
5f82f
```  
#### SemiStructured JSON(Key: value, struct and Array)
```sql
>hadoop fs -cat /tmp/record/*
{"county":"Albany","category":"Animal","groups":{"taxonomic_group":"Amphibians","taxonomic_subgroup":"Salamanders"},"position":{"xaxis": 256, "yaxis": 266, "labels": ["p1", "p2", "p3"]}}
>create external table if not exists mongoTestDCT(
>country string,
>category string,
>groups struct<taxonomic_group: string,taxonomic_subgroup: string>,
>position struct<xaxis: INT, yaxis: INT, labels: ARRAY<string>>)
>ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
>LOCATION '/tmp/record';
>select * from mongoTestDCT;
mongoTestDCT.country mongoTestDCT.category mongoTestDCT.groups                                                 mongoTestDCT.position
Albany               Animal                {"taxonomic_group":"Amphibians","taxonomic_subgroup":"Salamanders"} {"xaxis": 256, "yaxis": 266, "labels": ["p1", "p2", "p3"]}}
```  
Reference: http://forkedblog.com/load-json-data-to-hive-database-from-file/  
### 3) flattening Complex Dataobjects using a combiation of explode or Posexpolde with LATERAL VIEW.  
#### Lateral view using Explode  
```sql
>CREATE TABLE Products
>(id INT, ProductName STRING, ProductColorOptions ARRAY<STRING>);
>select * from products;
1 Watches [“Red”,”Green”]
2 Clothes [“Blue”,”Green”]
3 Books [“Blue”,”Green”,”Red”]
>select explode(ProductColorOptions) from products;
Red
Green
>SELECT p.id,p.productname,colors.colorselection FROM default.products P
>LATERAL VIEW EXPLODE(p.productcoloroptions) colors as colorselection;
1 Watches Red
1 Watches Green
2 Clothes Blue
2 Clothes Green
3 Books Blue
3 Books Green
3 Books Red
```  
#### Lateral View using POSExplode
posexplode means positional_explode. posexplode gives you an index along with value when you expand any error, and then you can use this indexes to map values with each other as mentioned below.
```sql
>select * from test_laterla_view_posexplode;
name	phone_numbers	cities
AAA	[“365-889-1234”, “365-887-2232”]	[“Hamilton”][“Burlington”]
BBB	[“232-998-3232”, “878-998-2232”]	[“Toronto”, “Stoney Creek”]
>select name, phone_number, city 
>from temp.test_laterla_view_posexplode
>lateral view posexplode(phone_numbers) pn as pos_phone, phone_number
>lateral view posexplode(cities) pn as pos_city, city 
>where pos_phone == pos_city;
name	phone_number	city
AAA	365-889-1234	Hamilton
AAA	365-887-2232	Burlington
BBB	232-998-3232	Toronto
BBB	878-998-2232	Stoney Creek
```  
### 4) Processing MultiLine JSON  
```sql
>cat multiFile.json
{
    "DocId": "Alibaba", 
    "User_1": {
        "Id": 1234, 
        "Username": "bob1234", 
        "Name": "Bob", 
        "ShippingAddress": {
            "Address1": "969 Wenyi West St.", 
            "Address2": null, 
            "City": "Hangzhou", 
            "Province": "Zhejiang"
        }, 
        "Orders": [
            {
                "ItemId": 6789, 
                "OrderDate": "11/11/2017"
            }, 
            {
                "ItemId": 4352, 
                "OrderDate": "12/12/2017"
            }
        ]
    }
}
>CREATE EXTERNAL TABLE json_table_1 (
    docid string,
    user_1 struct<
            id:INT,
            username:string,
            name:string,
            shippingaddress:struct<
                            address1:string,
                            address2:string,
                            city:string,
                            province:string
                            >,
            orders:array<
                    struct<
                        itemid:INT,
                        orderdate:string
                    >
            >
    >
)
ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
LOCATION 'oss://xxx/test/json/hcatalog_serde/table_1/';
```  
### Other modification
```sql
CREATE TABLE mytable (
    my_field string,
    other struct<with_dots:string> )
    ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES ("dots.in.keys" = "true", 'case.insensitive'='false', 'ignore.malformed.json' = 'true' )
```  
Reference: http://allabouthadoop.net/hive-lateral-view-explode-vs-posexplode/ and https://alibaba-cloud.medium.com/processing-oss-data-files-in-different-formats-using-data-lake-analytics-476d1c49c541  

