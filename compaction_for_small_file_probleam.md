- [optimal-file-size](#optimal-file-size)  
  - [optimal-file-size-for-hdfs](#optimal-file-size-for-hdfs)  
  - [optimal-file-size-for-s3](#optimal-file-size-for-s3)
- [compaction-for-small-file-problem-in-spark](#compaction-for-small-file-problem-in-spark)  
- [compaction-for-small-file-probleam-in-hive](#compaction-for-small-file-probleam-in-hive)   
  - [non-transactional-traditional-tables](#non-transactional-traditional-tables)  
  - [transactional-tables](#transactional-tables)  
- [Reference_links](#reference_links)  
#  
# Optimal file size  
Avoid file sizes that are smaller than the configured block size. An average size below the recommended size adds more burden to the NameNode, cause heap/GC issues in addition to cause storage and processing to be inefficient.  
Larger files than the blocksize are potentially wasteful. e.g. Creating files of 130MB would mean that file extend over 2 blocks, which carries additional I/O time. File sizes should always be a multiple of blocksize.  
For every file, directory and block in HDFS we need to spend around 150 bytes of namenode’s memory. So, If there are millions of files, then the amount of RAM that needs to be reserved by the Namenode becomes large.  
## Optimal file size for HDFS  
In the case of HDFS, the ideal file size is that which is as close to the configured blocksize value as possible (dfs.blocksize), often set as default to 128MB.  
## Optimal file size for S3  
For S3, there is a configuration parameter we can refer to — fs.s3a.block.size — however this is not the full story. File listing performance from S3 is slow, therefore an opinion exists to optimise for a larger file size. 1GB is a widely used default, although you can feasibly go up to the 4GB file maximum before splitting.  
#       
# Compaction for small file Problem in Spark  
calculate the ideal number of files using (Total file size / Configured blocksize ) = rounded(Ideal number of files), and use this in repartition.  
```python
def get_repartition_factor(dir_size):
    block_size = sc._jsc.hadoopConfiguration().get(“dfs.blocksize”)
    return math.ceil(dir_size/block_size) # returns 2
df=spark.read.parquet(“/path/to/source”)
df.repartition(get_repartition_factor(217894092))
.write
.parquet("/path/to/output")
```   
#       
# Compaction for small file Probleam in Hive  
Compaction in hive can be set by tuning the reducer output.  
## Non-Transactional traditional tables  
Approach to enable compaction on Non-Transactional tables is to find out the list of all partitions which holds more than 5 files, this can be done by using the hive virtual column ‘input__file__name’. Set the reducer size to define approximate file size. Execute insert overwrite on the partitions which exceeded file threshold count, in our case which is 5.  
```sql 
set hive.support.quoted.identifiers=none;
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.exec.reducers.bytes.per.reducer=268435456; --256MB reducer size.
with partition_list as
(
select order_date, count(distinct input__file__name) cnt from orders
group by order_date  having cnt > 5
)
insert overwrite table orders partition (order_date)
select * from orders
where order_date  in (select order_date from partition_list)
```  
## Transactional tables   
Given the need to apply frequent updates on the ACID enabled table, the hive can generate a large number of small files. Unlike a regular Hive table, ACID table handles compaction automatically. All it needs is some table properties to enable auto compaction.  
“compactor.mapreduce.map.memory.mb” : specify compaction map job properties  
“compactorthreshold.hive.compactor.delta.num.threshold: Trigger minor compaction threshold  
“compactorthreshold.hive.compactor.delta.pct.threshold”: Threshold when to trigger major compaction  
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
CLUSTERED BY (id) INTO 10 BUCKETS STORED AS ORC
LOCATION '/user/dks/datalake/orders_acid';
TBLPROPERTIES ("transactional"="true",
"compactor.mapreduce.map.memory.mb"="3072",     -- specify compaction map job properties
"compactorthreshold.hive.compactor.delta.num.threshold"="20",  -- trigger minor compaction if there are more than 20 delta directories
"compactorthreshold.hive.compactor.delta.pct.threshold"="0.5" -- trigger major compaction if the ratio of size of delta files to          -- size of base files is greater than 50%
);
```
#  
# Reference_links  
https://medium.com/@chrisfinlayson_83750/compaction-merge-of-small-parquet-files-bef60847e60b  
https://medium.com/datakaresolutions/compaction-in-hive-97a1d072400f  
