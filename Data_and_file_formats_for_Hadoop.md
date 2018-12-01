# Structured, Semi Structured and Unstructured Data:
Structured Data follows predefined and static Data Model or Schema like CSV.  
Semi Structured data follows a schema that can be changed or fexible like Json, XML.  
Unstructured data (or unstructured information) refers to information that either does not have a pre-defined data model or is not organized in a pre-defined manner.  
Unstructured information is typically text-heavy, but may contain data such as dates, numbers, and facts as well.  
Techniques such as data mining, natural language processing (NLP), and text analytics provide different methods to find patterns in, or otherwise interpret, this information.  
Examples of "unstructured data" may include books, journals, documents, metadata, health records, audio, video, analog data, images, files, and unstructured text such as the body of an e-mail message, Web page, or word-processor document.  


# When evaluating file formats, what must be considered:

## What processing and query tools will you be using?

This is the most obvious but overlooked question. If you want to use Avro, does the data processing language you’ve chosen include Avro readers and writers?     Likewise, suppose you’ve picked the Cloudera distribution because you like Impala. You should probably know that Impala currently does not support ORC format. Conversely, if you’ve chosen Hortonworks and Hive-Stinger, you probably want to avoid Parquet. Yes, it is expected that most of the tools will end up supporting most of the popular formats, but doublecheck before you make any final decisions.  

## Will your data structure change over time?

Do you want to add and delete fields from a file and still be able to read old files with the same code?   Certainly, if you have a large enough cluster you can rewrite all of your historical data to add a field, but this is often not ideal. Being able to add a field and still read historical data may be preferred. If so, know which file formats enable flexible and evolving schema.  

## How important is file format “splittability”?

Since Hadoop stores and processes data in blocks you must be able to begin reading data at any point within a file in order to take fullest advantage of Hadoop’s distributed processing. For example, CSV files are splittable since you can start reading at any line in the file and the data will still make sense; however, an XML file is not splittable since XML has an opening tag at the beginning and a closing tag at the end. You cannot start processing at any point in the middle of those tags.  

## Does block compression matter?

Since Hadoop stores large files by splitting them into blocks, its best if the blocks can be independently compressed. Snappy and LZO are commonly used compression technologies that enable efficient block storage and processing. If a file format does not support block compression then, if compressed, the file is rendered non-splittable. When processed, the decompressor must begin reading the file at its beginning in order to obtain any block within the file. For a large file with many blocks, this could generate a substantial performance penalty.  

## How big are your files?

If your files are smaller than the size of an HDFS block, then splittability and block compression don’t matter. You may be able to store the data uncompressed or with a simple file compression algorithm. Of course, small files are the exception in Hadoop and processing too many small files can cause performance issues. Hadoop wants large, splittable files so that its massively distributed engine can leverage data locality and parallel processing.  
  
Large files in Hadoop consume a lot of disk -- especially when considering standard 3x replication. So, there is an economic incentive to compress data. i.e. store more data per byte of disk. There is also a performance incentive as disk IO is expensive. If you can reduce the disk footprint through compression, you can relieve IO bottlenecks. As an example, I converted an uncompressed, 1.8GB CSV file into the following formats, achieving much smaller disk footprints.  

Uncompressed CSV 1.8 GB  

Avro 1.5 GB  

Avro w/ Snappy Compression 750 MB  

Parquet w/ Snappy Compression 300 MB  

I then ran Impala and Hive queries against each of the file formats. As the files became smaller, the query performance improved. The queries against Parquet were a couple orders of magnitude faster than uncompressed CSV.  

## Are you more concerned about processing or query performance?  

There are three types of performance to consider:    
  
Write performance -- how fast can the data be written.  
Partial read performance -- how fast can you read individual columns within a file.  
Full read performance -- how fast can you read every data element in a file.  

A columnar, compressed file format like Parquet or ORC may optimize partial and full read performance, but they do so at the expense of write performance. Conversely, uncompressed CSV files are fast to write but due to the lack of compression and column-orientation are slow for reads. You may end up with multiple copies of your data each formatted for a different performance profile.  

# When evaluating file formats, what must be considered  
  
## What processing and query tools will you be using?  
  
This is the most obvious but overlooked question. If you want to use Avro, does the data processing language you’ve chosen include Avro readers and writers?     Likewise, suppose you’ve picked the Cloudera distribution because you like Impala. You should probably know that Impala currently does not support ORC format. Conversely, if you’ve chosen Hortonworks and Hive-Stinger, you probably want to avoid Parquet. Yes, it is expected that most of the tools will end up supporting most of the popular formats, but doublecheck before you make any final decisions.  
  
## Will your data structure change over time?  
  
Do you want to add and delete fields from a file and still be able to read old files with the same code?   Certainly, if you have a large enough cluster you can rewrite all of your historical data to add a field, but this is often not ideal. Being able to add a field and still read historical data may be preferred. If so, know which file formats enable flexible and evolving schema.  
  
## How important is file format “splittability”?  
  
Since Hadoop stores and processes data in blocks you must be able to begin reading data at any point within a file in order to take fullest advantage of Hadoop’s distributed processing. For example, CSV files are splittable since you can start reading at any line in the file and the data will still make sense; however, an XML file is not splittable since XML has an opening tag at the beginning and a closing tag at the end. You cannot start processing at any point in the middle of those tags.  
  
## Does block compression matter?  
  
Since Hadoop stores large files by splitting them into blocks, its best if the blocks can be independently compressed. Snappy and LZO are commonly used compression technologies that enable efficient block storage and processing. If a file format does not support block compression then, if compressed, the file is rendered non-splittable. When processed, the decompressor must begin reading the file at its beginning in order to obtain any block within the file. For a large file with many blocks, this could generate a substantial performance penalty.  
  
## How big are your files?  
  
If your files are smaller than the size of an HDFS block, then splittability and block compression don’t matter. You may be able to store the data uncompressed or with a simple file compression algorithm. Of course, small files are the exception in Hadoop and processing too many small files can cause performance issues. Hadoop wants large, splittable files so that its massively distributed engine can leverage data locality and parallel processing.  
  
Large files in Hadoop consume a lot of disk -- especially when considering standard 3x replication. So, there is an economic incentive to compress data. i.e. store more data per byte of disk. There is also a performance incentive as disk IO is expensive. If you can reduce the disk footprint through compression, you can relieve IO bottlenecks. As an example, I converted an uncompressed, 1.8GB CSV file into the following formats, achieving much smaller disk footprints.  
  
Uncompressed CSV 1.8 GB  
  
Avro 1.5 GB  
  
Avro w/ Snappy Compression 750 MB  
  
Parquet w/ Snappy Compression 300 MB  
  
I then ran Impala and Hive queries against each of the file formats. As the files became smaller, the query performance improved. The queries against Parquet were a couple orders of magnitude faster than uncompressed CSV.  
  
## Are you more concerned about processing or query performance?  
  
There are three types of performance to consider:    
  
Write performance -- how fast can the data be written.  
Partial read performance -- how fast can you read individual columns within a file.  
Full read performance -- how fast can you read every data element in a file.  
  
A columnar, compressed file format like Parquet or ORC may optimize partial and full read performance, but they do so at the expense of write performance. Conversely, uncompressed CSV files are fast to write but due to the lack of compression and column-orientation are slow for reads. You may end up with multiple copies of your data each formatted for a different performance profile.  
  
How do the most common Hadoop file formats stack up?  
  
## Formats to Avoid  
  
Any format that is not splittable should generally be avoided. The use of XML File and JSON File formats is a common mistake. Each of these formats contain a single document per file with an opening tag at the beginning and a closing tag at the end. Note that files containing JSON records are ok and will be discussed below.  
  
## Text/CSV Files  
  
CSV files are still quite common and often used for exchanging data between Hadoop and external systems. They are readable and ubiquitously parsable. They come in handy when doing a dump from a database or bulk loading data from Hadoop into an analytic database. However, CSV files do not support block compression, thus compressing a CSV file in Hadoop often comes at a significant read performance cost.  
  
When working with Text/CSV files in Hadoop, never include header or footer lines. Each line of the file should contain a record. This, of course, means that there is no metadata stored with the CSV file. You must know how the file was written in order to make use of it. Also, since the file structure is dependent on field order, new fields can only be appended at the end of records while existing fields can never be deleted. As such, CSV files have limited support for schema evolution.  
  
## JSON Records  
  
JSON records are different from JSON Files in that each line is its own JSON datum -- making the files splittable. Unlike CSV files, JSON stores metadata with the data, fully enabling schema evolution. However, like CSV files, JSON files do not support block compression. Additionally, JSON support was a relative late comer to the Hadoop toolset and many of the native serdes contain significant bugs. Fortunately, third party serdes are frequently available and often solve these challenges. You may have to do a little experimentation and research for your use cases.   
  
## Avro Files
  
Avro files are quickly becoming the best multi-purpose storage format within Hadoop. Avro files store metadata with the data but also allow specification of an independent schema for reading the file. This makes Avro the epitome of schema evolution support since you can rename, add, delete and change the data types of fields by defining new independent schema. Additionally, Avro files are splittable, support block compression and enjoy broad, relatively mature, tool support within the Hadoop ecosystem.  
  
## Sequence Files  
  
Sequence files store data in a binary format with a similar structure to CSV. Like CSV, sequence files do not store metadata with the data so the only schema evolution option is appending new fields. However, unlike CSV, sequence files do support block compression. Due to the complexity of reading sequence files, they are often only used for “in flight” data such as intermediate data storage used within a sequence of MapReduce jobs.  
  
## RC Files  
  
RC Files or Record Columnar Files were the first columnar file format adopted in Hadoop. Like columnar databases, the RC file enjoys significant compression and query performance benefits. However, the current serdes for RC files in Hive and other tools do not support schema evolution. In order to add a column to your data you must rewrite every pre-existing RC file. Also, although RC files are good for query, writing an RC file requires more memory and computation than non-columnar file formats. They are generally slower to write.  
  
## ORC Files  
  
ORC Files or Optimized RC Files were invented to optimize performance in Hive and are primarily backed by HortonWorks. ORC files enjoy the same benefits and limitations as RC files just done better for Hadoop. This means ORC files compress better than RC files, enabling faster queries. However, they still don’t support schema evolution. Some benchmarks indicate that ORC files compress to be the smallest of all file formats in Hadoop. It is worthwhile to note that, at the time of this writing, Cloudera Impala does not support ORC files.  
  
## Parquet Files  
  
Parquet Files are yet another columnar file format that originated from Hadoop creator Doug Cutting’s Trevni project. Like RC and ORC, Parquet enjoys compression and query performance benefits, and is generally slower to write than non-columnar file formats. However, unlike RC and ORC files Parquet serdes support limited schema evolution. In Parquet, new columns can be added at the end of the structure. At present, Hive and Impala are able to query newly added columns, but other tools in the ecosystem such as Hadoop Pig may face challenges. Parquet is supported by Cloudera and optimized for Cloudera Impala. Native Parquet support is rapidly being added for the rest of the Hadoop ecosystem.  
  
One note on Parquet file support with Hive... It is very important that Parquet column names are lowercase. If your Parquet file contains mixed case column names, Hive will not be able to read the column and will return queries on the column with null values and not log any errors. Unlike Hive, Impala handles mixed case column names. A truly perplexing problem when you encounter it!  

## How to choose a file format?  
  
As discussed, each file format is optimized by purpose. Your choice of format is driven by your use case and environment. Here are the key factors to consider:  
  
Hadoop Distribution- Cloudera and Hortonworks support/favor different formats  
Schema Evolution- Will the structure of your data evolve?  In what way?  
Processing Requirements - Will you be crunching the data and with what tools?  
Read/Query Requirements- Will you be using SQL on Hadoop?  Which engine?  
Extract Requirements- Will you be extracting the data from Hadoop for import into an external database engine or other platform?  
Storage Requirements- Is data volume a significant factor?  Will you get significantly more bang for your storage buck through compression?  
  
So, with all the options and considerations are there any obvious choices?  If you are storing intermediate data between MapReduce jobs, then Sequence files are preferred. If query performance against the data is most important, ORC (HortonWorks/Hive) or Parquet (Cloudera/Impala) are optimal --- but these files will take longer to write. (We’ve also seen order of magnitude query performance improvements when using Parquet with Spark SQL.) Avro is great if your schema is going to change over time, but query performance will be slower than ORC or Parquet. CSV files are excellent if you are going to extract data from Hadoop to bulk load into a database.  
  
Because you will likely have multiple use cases for your Hadoop data, you will use multiple file formats. It is quite common to store the same (or very similar) data in multiple file formats to support varied processing, query and extract requirements. Embrace diversity!  
