[optimal-file-size](#optimal-file-size)  
- [optimal-file-size](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#optimal-file-size)  
  - [optimal-file-size-for-hdfs](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#optimal-file-size-for-hdfs)  
  - [optimal-file-size-for-s3](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#optimal-file-size-for-s3)
- [compaction-for-small-file-problem-in-spark](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#compaction-for-small-file-problem-in-spark)  
- [compaction-for-small-file-probleam-in-hive](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#compaction-for-small-file-probleam-in-hive)   
  - [non-transactional-traditional-tables](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#non-transactional-traditional-tables)  
  - [transactional-tables](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#transactional-tables)  
- [Reference_links](https://github.com/korivikishore1991PersonalStuff/Notes_and_Interview_questions/new/master#reference_links)  
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
## Non-Transactional traditional tables  
## Transactional tables   
#  
# Reference_links  
https://medium.com/@chrisfinlayson_83750/compaction-merge-of-small-parquet-files-bef60847e60b  
https://medium.com/datakaresolutions/compaction-in-hive-97a1d072400f  
