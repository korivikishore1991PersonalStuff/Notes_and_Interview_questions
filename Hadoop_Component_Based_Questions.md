# General  
  
### Brief the resume?  
I’m a Hadoop Developer with hands on experience in performing Data Analytics using Hadoop components like Spark, SparkSQL, Spark-Streaming, Hive and Impala with in-depth understanding of Hadoop Map-reduce and distributed file systems architecture. I have mainly worked on Parsing, filtering and Manipulating different kinds of Semi-Structured, Unstructured and Structured data.  
  
My most recent project is at Sabre in Southlake TX, which is the largest Global Distribution Systems provider for air bookings in North America. Sabre processes more than 1.5 billion daily API requests. The systems should quickly cull through billions of possibilities to return the most appropriate itineraries to travel shoppers. Data from these data points coming through travel agencies could be structured and unstructured in few cases. All this data was collected, aggregated and analyzed in the Hadoop clusters to find customer usage patterns which helps make cross sell, up sell business decisions and device targeted marketing strategies.  
  
### Few tasks you have done at your most recent project?  
•	Worked on parsing and filtering Semi-Structure data like json using Dataframe/SparkSQL, case classes and also programmatically specifying the schema explicitly in-addition to working on loading more than 22 features into case classes in Spark.  
•	Worked on modeling data using Avro schema into Parquet format using SparkSQL.  
•	Worked on querying the unstructured text data using Vector Space Models and Apache Lucene.  
•	Worked on real-time data analytics in SparkStreaming for streaming text and kafka topic data.  
•	Worked on loading data to and from RDBMS to HDFS using Spark and JDBC connectors.  
•	Worked on data preparation methods in spark dataframes using set operations, regular expressions, sorting, parsing arbitrary date/time inputs and converting json arrays values into lists.  
•	Worked on installing and pointing the Zeppelin to the Spark master in-addition to creation of custom defined functions.  
•	Exploring of Spark improving the performance and optimization of the existing algorithms in Hadoop using Spark context, Spark-SQL, Data Frame, pair RDD's.  
•	Designed Hive table schemas using partitioning and bucketing to store tables as both external and internal table.  
•	Worked on developing Hive UDF’s in Python to define custom analytical functions.  
•	Worked on performance tuning, debugging and optimization of hive queries by changing the default YARN values.  
  
### Describe the Arcitecture at your Organisation?  
<p align="center">
  <img src="https://www.dropbox.com/s/t2uqmk3iirgpzkr/Movie_Plex_Arcitecture.png?raw=1" width="650"/>
  <img src="https://www.dropbox.com/s/3heoh2vsif84mgv/Oracle_Big_Data_Sloution.png?raw=1" width="650"/>
</p>
  
From the website they are two outgoing data streams one is into Cassandra, where the few required data attributes are processed and loaded back to the Website in real time to enable operational functions of an enterprice. Other, is through the Data-pipeline of Apache Kafka and Spark Streaming, where all the WebLogs of site activity was collected in near real time streaming and stored in HDFS to enable Analytical functions of the enterprice.  
  
The Data collected in HDFS is Processed using Hive, Impala and Spark to Load Recomendations into Cassandra (or) to push Data into Backend DataBases and Relational DataBases like R and Vertica for analytical purposes (or) to push the data into Legacy Databases like MySQL.  
  
### Descibe why is it neccessary for Enterprices to have both NoSQL and Hadoop components in it for Data processing?  
The ability to extract information from operational data in real time is critical for a modern, agile enterprise. The faster you can harness insights from data, the greater your advantage in driving revenue, reducing costs, and increasing efficiency. Modern architecture for real time big data combines Hadoop and NoSQL. Hadoop is engineered for big data analytics, but it’s not real time. NoSQL is engineered for real-time big data, but it’s operational rather than analytical. NoSQL together with Hadoop is the key to real time big data.  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1KGyTWPm4LnZmaIkOlslaW4PARhVd5TZ9" width="650"/>
  <img src="https://drive.google.com/uc?export=view&id=1kTuKtQ_rSa1mQ5AoaWvhxU6CG2kM19uR" width="650"/>
</p>
    
### What are operational and analytical operations?  
Operational Data  
A company's operations are supported by applications that automate key business processes. These include areas such as sales, service, order management, manufacturing, purchasing, billing, accounts receivable and accounts payable. These applications require significant amounts of data to function correctly.  This includes data about the objects that are involved in transactions, as well as the transaction data itself.  For example, when a customer buys a product, the transaction is managed by a sales application.  The objects of the transaction are the Customer and the Product.  The transactional data is the time, place, price, discount, payment methods, etc. used at the point of sale. The transactional data is stored in On-Line Transaction Processing (OLTP) tables that are designed to support high volume low latency access and update.  
  
Master Data Management(MDM) solutions that focus on managing operational master data supporting transactional applications are called Operational MDM. They rely heavily on integration technologies. They bring real value to the enterprise, but lack the ability to influence reporting and analytics.  
  
Operational Big Data systems provide operational features to run real-time, interactive workloads that ingest and store data. Cassandra is a top technology for operational Big Data applications with over 10 million downloads of its open source software.  
  
Analytical Data  
Analytical data is used to support a company's decision making.  Customer buying patterns are analyzed to identify churn, profitability, and marketing segmentation. Suppliers are categorized, based on performance characteristics over time, for better supply chain decisions. Product behavior is scrutinized over long periods to identify failure patterns.  This data is stored in large Data Warehouses and possibly smaller data marts with table structures designed to support heavy aggregation, ad hoc queries, and data mining. Typically the data is stored in large fact tables surrounded by key dimensions such as customer, product, supplier, account, and location.  
  
Master Data Management(MDM) solutions that focus on managing analytical master data are called Analytical MDM. They focus on providing high quality dimensions with their multiple simultaneous hierarchies to data warehousing and BI technologies. They also bring real value to the enterprise, but lack the ability to influence operational systems. Any data cleansing done inside an Analytical MDM solution is invisible to the transactional applications and transactional application knowledge is not available to the cleansing process. Because Analytical MDM systems can do nothing to improve the quality of the data under the heterogeneous application landscape, poor quality inconsistent domain data finds its way into the BI systems and drives less than optimum results for reporting and decision making.  
  
Analytical Big Data technologies, on the other hand, are useful for retrospective, sophisticated analytics of your data. Hadoop is the most popular example of an Analytical Big Data technology.  
  
### How WebLog and Transaction information to be processed by Big Data is Generated(Data Sources People(Social Media), Machines(WebLogs), Organisation(Transactions))?  
### Yarn Resources and peformane tuning?  
Turning hive for performance and optimization involves changing default yarn parameters. Like  
For over coming Java HeapDumpOnOutOfMemoryError:  
set mapreduce.map.memory.mb=12288;  
set mapreduce.map.java.opts= -Xmx8192M -verbose:gc -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70;  
To Optimise the a multiple Map-Reduce jobs:  
SET hive.exec.compress.output=true;  
SET io.seqfile.compression.type=BLOCK;  
To enable bucketing in hive:(Not needed form Hive 2.X)  
set hive.enforce.bucketing = true;  
To create avro file:  
set hive.exec.compress.output=true;  
set avro.output.codec=snappy;  
### Tablue data vizulalization?  
### Difference between object, method, functions and classes in Java?(Objects are instances of classes, whose class Functions can be acssed as methods)  
### Difference between public, private and default value decrelation in Java?  
### is val and var public, private and default decrelation in Scala?  
### Use of Some() KeyWord in Scala? 
```Scala
def toInt(in: String): Option[Int] = {  
try {  
Some(Integer.parseInt(in.trim))  
} catch {  
case e: NumberFormatException => None  
}  
}  
toInt(someString) match {  
case Some(i) => println(i)  
case None => println("That didn't work.")  
}  
```
Used to better handle the exception errors. Can be used to return some other value besides null, in this case, perhaps zero or some other meaningless number.  
  
### Opening URL's and getting the data in Scala?  
io.source.fromURL("URL_String")  
to get data use io.source.fromURL("URL_String").getLines.toList  
  
### File input and output operations and redirecting console output and handling Exceptions in Scala?  
```Scala
object Scala_practise extends App{  
  val file = new File("text.txt")  
  var i = 0  
  //Redirecting the Console Output and handling Exceptions  
  try {  
    val Outputfile = new File("output.txt")  
    val ps = new PrintStream(new FileOutputStream(Outputfile, true))  
    System.setOut(ps)  
  }  
  catch {  
    case ex: Exception =>{  
      ex.printStackTrace()  
      System.exit(1)  
    }  
  }  
  //Writing to a text file using PrintWriter and FileOutputStream in append mode  
  while (!file.exists()|| i<11){  
    val printWriter = new PrintWriter(new FileOutputStream(file, true))  
    printWriter.println("Korivi, Ranga Nanda Kishore")  
    printWriter.println("This is %d writing".format(i))  
    printWriter.close()  
    i += 1  
  }  
  //reading the contents of a text file using Scanner  
  if(file.exists()){  
    val scanner = new Scanner(new FileInputStream(file))  
    while (scanner.hasNext()){  
      println(scanner.nextLine())  
    }  
    scanner.close()  
  }  
  //reading the contents of a text file using Source Class in Scala  
  for(line <- Source.fromFile(file).getLines()){  
    println(line)  
  }  
  //closing the console output PrintStream object  
  ps.close()  
}  
```
### File I/O and word count in Python?  
```Python
import string
fhand = open('romeo.txt')
counts = dict()
for line in fhand:
    line = line.translate(None, string.punctuation)
    line = line.lower()
    words = line.split()
    for word in words:
        if word not in counts:
            counts[word] = 1
        else:
            counts[word] += 1

# Sort the dictionary by value
lst = list()
for key, val in counts.items():
    lst.append( (val, key) )

lst.sort(reverse=True)

fout = open('output.txt', 'w')

for key, val in lst:
    fout.write(str(key)+" "+val+"\n")
fout.close()
```
### deceralation of public, private and default types in python?  
### Changing file permissions in Shell Scripting?  
### What is DML and DDL?  
DML is abbreviation of Data Manipulation Language. It is used to retrieve, store, modify, delete, insert and update data in database.
Examples: SELECT, UPDATE, INSERT statements  
  
DDL is abbreviation of Data Definition Language. It is used to create and modify the structure of database objects in database.
Examples: CREATE, ALTER, DROP statements  
### Primary key vs unique key vs logical key vs foregn key in SQL?  
Primary Key:  
Can be only one in a table  
In some DBMS it cannot be NULL - e.g. MySQL adds NOT NULL  
Primary Key is a unique key identifier of the record  
Unique Key:  
Can be more than one unique key in one table  
Unique key can have null values  
It can be a candidate key  
Unique key can be null and may not be unique  
Logical Key:  
A logical Key is a key that the "real world" might use to look up a row. Often we add "UNIQUE" constraint to a logical key.  
Foreign Key:  
A foreign key is usually a number that points to the primary key of an associated row in a different table.  
### What is the advantage of a Parquet file?  
Parquet file is a columnar format file that helps –  
Limit I/O operations  
Consumes less space  
Fetches only required columns.  
### What is MVC architecture(model-view-controller)?  
As with other software architectures, MVC expresses the "core of the solution" to a problem while allowing it to be adapted for each system. Particular MVC architectures can vary significantly from the traditional description here.  
Components:  
The model is the central component of the pattern. It expresses the application's behavior in terms of the problem domain, independent of the user interface. It directly manages the data, logic and rules of the application.  
A view can be any output representation of information, such as a chart or a diagram. Multiple views of the same information are possible, such as a bar chart for management and a tabular view for accountants.  
The third part, the controller, accepts input and converts it to commands for the model or view.  
<p align="center">
  <img src="https://www.dropbox.com/s/fl82wi3d88mymg4/MVC-basic.svg.png?raw=1" width="650"/>
</p>  
Interactions:    
In addition to dividing the application into three kinds of components, the model–view–controller design defines the interactions between them.  
A model stores data that is retrieved according to commands from the controller and displayed in the view.  
A view generates new output to the user based on changes in the model.  
A controller can send commands to the model to update the model's state (e.g., editing a document). It can also send commands to its associated view to change the view's presentation of the model (e.g., scrolling through a document).  
### What is a RestFull webservice API?  
RESTful web services are built to work best on the Web. Representational State Transfer (REST) is an architectural style that specifies constraints, such as the uniform interface, that if applied to a web service induce desirable properties, such as performance, scalability, and modifiability, that enable services to work best on the Web. In the REST architectural style, data and functionality are considered resources and are accessed using Uniform Resource Identifiers (URIs), typically links on the Web. The resources are acted upon by using a set of simple, well-defined operations. The REST architectural style constrains an architecture to a client/server architecture and is designed to use a stateless communication protocol, typically HTTP. In the REST architecture style, clients and servers exchange representations of resources by using a standardized interface and protocol.  
The following principles encourage RESTful applications to be simple, lightweight, and fast:  
Resource identification through URI: A RESTful web service exposes a set of resources that identify the targets of the interaction with its clients. Resources are identified by URIs, which provide a global addressing space for resource and service discovery.  
Uniform interface: Resources are manipulated using a fixed set of four create, read, update, delete operations: PUT, GET, POST, and DELETE. PUT creates a new resource, which can be then deleted by using DELETE. GET retrieves the current state of a resource in some representation. POST transfers a new state onto a resource.  
Self-descriptive messages: Resources are decoupled from their representation so that their content can be accessed in a variety of formats, such as HTML, XML, plain text, PDF, JPEG, JSON, and others. Metadata about the resource is available and used, for example, to control caching, detect transmission errors, negotiate the appropriate representation format, and perform authentication or access control.  
Stateful interactions through hyperlinks: Every interaction with a resource is stateless; that is, request messages are self-contained. Stateful interactions are based on the concept of explicit state transfer. Several techniques exist to exchange state, such as URI rewriting, cookies, and hidden form fields. State can be embedded in response messages to point to valid future states of the interaction.  <br />
  
### Difference between a Data Engineer, Data Analyst and Data Scientist?   
  
### Type of Analytics Done on Big Data?  
Descriptive Analytics: If you just told me that you spent 25% on food, 35% on clothing, 20% on entertainment and the rest on miscellaneous items last year using your credit card, that is descriptive analytics. Of course, you can go into lot more detail as well.  
  
Predictive Analytics: If you analyzed your credit card history for the past 5 years and the split is somewhat consistent, you can safely forecast with high probability that next year will be similar to past years. The fine print here is that this is not about ‘predicting the future’ rather ‘forecasting with probabilities’ of what might happen. In Big data predictive analytics, data scientists may use advanced techniques like data mining, machine learning, and advanced statistical processes (we’ll discuss all these terms later) to forecast weather, economy etc.  
  
Prescriptive Analytics: Still using the credit card transactions example, you may want to find out which spending to target (i.e. food, entertainment, clothing etc.) to make a huge impact on your overall spending. Prescriptive analytics builds on predictive analytics by including ‘actions’ (i.e. reduce food or clothing or entertainment) and analyzing the resulting outcomes to ‘prescribe’ the best category to target to reduce your overall spend. You can extend this to big data and imagine how executives can make data-driven decisions by looking at the impacts of various actions in front of them.  
  
  
# MapReduce  
  
### Map/reduce if 1st file had empId and empName & 2nd file empId and empSalary. How to find the max salary empId? How many mappers are launched?(Partially Unawnsered)  
A block is read in the form of a key-value pair in TextInputFormat, where the key is the byte offset of a line and the value is content of the line itself. Each block is considered as an InputSplit and a single Mapper is lauched for each inputSplit.  
###Explaine Map/Reduce process?  
  
  
# Spark  
  
## SparkCore  
### Difference between Map-reduce and spark? Advantages and disadvantages and execution methods?  
Conceptually DAG model is a strict generalization of MapReduce model. DAG-based systems like Spark and Tez that are aware of the whole DAG of operations can do better global optimizations than systems like Hadoop MapReduce which are unaware of the DAG to be executed.  
  
<p align="center">
  <img src="https://www.dropbox.com/s/99ctmul7unk6wvk/Map-Reduce.png?raw=1" width="650"/>
  <img src="https://www.dropbox.com/s/8uw8f0ieu4uuqgi/Spark.jpg?raw=1" width="650"/>
</p>
  
Conceptually speaking, the MapReduce model simply states that distributed computation on a large dataset can be boiled down to two kinds of computation steps - a map step and a reduce step. One pair of map and reduce does one level of aggregation over the data. Complex computations typically require multiple such steps. When you have multiple such steps, it essentially forms a DAG of operations. So a DAG execution model is essentially a generalization of the MapReduce model.  
  
While this is the theory, different systems implement this theory in different ways, and that is where the "advantages" and "disadvantages" come from. Computations expressed in Hadoop MapReduce boil down to multiple iterations of (i) read data from HDFS, (ii) apply map and reduce, (iii) write back to HDFS. Each map-reduce round is completely independent of each other, and Hadoop does not have any global knowledge of what MR steps are going to come after each MR. For many iterative algorithms this is inefficient as the data between each map-reduce pair gets written and read from filesystem. Newer systems like Spark and Tez improves performance over Hadoop by considering the whole DAG of map-reduce steps and optimizing it globally (e.g., pipelining consecutive map steps into one, not write intermediate data to HDFS). This prevents writing data back and forth after every reduce.  
  
Storm, being a streaming system, is slightly different from the batch processing systems referred earlier. It also sets up a DAG of nodes lets the records stream between the nodes. Its best to compare Storm with Spark Streaming (streaming system built over Spark) than Hadoop MapReduce. Both accepts a DAG of operations representing the streaming computation, but then process the DAG in slightly different ways. Storm sets up a DAG of node and allocates each operation in the DAG of ops to different nodes. Spark Streaming does not pre-allocate, rather uses the underlying Spark's mechanisms to dynamically allocate tasks to available resources. This gives different kinds of performance characteristics.  
### What are the disadvantages of using Apache Spark over Hadoop MapReduce?  
Apache spark does not scale well for compute intensive jobs and consumes large number of system resources. Apache Spark’s in-memory capability at times comes a major roadblock for cost efficient processing of big data. Also, Spark does have its own file management system and hence needs to be integrated with other cloud based data platforms or apache hadoop.  
### Define a worker node?  
A node that can run the Spark application code in a cluster can be called as a worker node. A worker node can have more than one worker which is configured by setting the SPARK_ WORKER_INSTANCES property in the spark-env.sh file. Only one worker is started if the SPARK_ WORKER_INSTANCES property is not defined.  
### What is a lineage Map in Spark?  
### Explain about the core components of a distributed Spark application?  
Driver- The process that runs the main () method of the program to create RDDs and perform transformations and actions on them.  
Executor –The worker processes that run the individual tasks of a Spark job.  
Cluster Manager-A pluggable component in Spark, to launch Executors and Drivers. The cluster manager allows Spark to run on top of other external managers like Apache Mesos or YARN.  
### What do you understand by Executor Memory in a Spark application?  
Every spark application has same fixed heap size and fixed number of cores for a spark executor. The heap size is what referred to as the Spark executor memory which is controlled with the spark.executor.memory property of the –executor-memory flag. Every spark application will have one executor on each worker node. The executor memory is basically a measure on how much memory of the worker node will the application utilize.  
### How to enable speculative execution in spark?  
spark-submit \  
--conf "spark.speculation=true" \  
--conf "spark.speculation.multiplier=5" \  
--conf "spark.speculation.quantile=0.90" \  
--class "org.asyncified.myClass" "path/to/uberjar.jar"  
### How can you minimize data transfers when working with Spark?  
Minimizing data transfers and avoiding shuffling helps write spark programs that run in a fast and reliable manner. The various ways in which data transfers can be minimized when working with Apache Spark are:  
Using Broadcast Variable- Broadcast variable enhances the efficiency of joins between small and large RDDs.  
Using Accumulators – Accumulators help update the values of variables in parallel while executing.  
The most common way is to avoid operations ByKey, repartition or any other operations which trigger shuffles.  
### Memory manegment for Spark job execution via Tunning memory parameters for spark job execution?  
#### Inputs:  
From Resource maneger on Port 8088:  
number of VCores = 84 cores= VC  
From NameNode on port 50070:  
number of nodes = 6 nodes = n  
Rule of thumb to minimize I/O to HDFS: number of executors-cores = 3 to 5 = e  
#### Intermidiates:  
number of executor-cores for each executor = 3 to 5 = e  
number of executors per machine(worker or slave node) ={(VC/n)-1}/e  
number of threads trying to read and write from HDFS per machine = e*{((VC/n)-1)/e}  
Total number of cores per spark Application = n*{((VC/n)-1)/e}*e  
Total memory per spark Application = {(M*e)/(n*((VC/n)-1))}*0.9*{n*(((VC/n)-1)/e)}  
#### Output:  
--executor-cores = e  
--num-executors = ({(VC/n)-1}/e)*n  
--executor-memory = {(M*e)/(n*((VC/n)-1))}*0.9  
```example
Auxilary Outputs:
number of executors per machine(worker or slave node) ={(84/6)-1}/3=4  
number of threads trying to read and write from HDFS per machine = 3 cores*4 executor = 12 threads 
Total number of cores per spark Application = 24 executors * 3 cores/executors = 72 cores 
Total memory per spark Application = 12 GB * 24 executors = 288 GB  
real Outputs:
--executor-cores = 3 
--num-executors = ({(84/6)-1}/3)*6 = 24  
--executor-memory = {(336*3)/(6*((84/6)-1))}*0.9 = 12 GB  
```
### Hadoop uses replication to achieve fault tolerance. How is this achieved in Apache Spark?  
Data storage model in Apache Spark is based on RDDs. RDDs help achieve fault tolerance through lineage. RDD always has the information on how to build from other datasets. If any partition of a RDD is lost due to failure, lineage helps build only that particular lost partition.  
### Why is Spark conf and spark context related to the program?  
### What is an RDD in spark?(Partially anwsred look in Chapter 3 Spark Data Analytics cook book)  
RDDs (Resilient Distributed Datasets) are basic abstraction in Apache Spark that represent the data coming into the system in object format. RDDs are used for in-memory computations on large clusters, in a fault tolerant manner. RDDs are read-only portioned, collection of records, that are –  
Immutable – RDDs cannot be altered.  
Resilient – If a node holding the partition fails the other node takes the data.  
### Why the Data needs to serialised in Spark?  
### Difference between action and transformation?  
### How to use wholeTextFiles() and textFile(), using union() in sparkContext()?  
```Scala
val text_file1 = sc.wholeTextFiles("""C:\\Users\\korivi\\Downloads\\numbertextdirectory""")  
val text_file2 = sc.wholeTextFiles("""C:\\Users\\korivi\\Downloads\\tabletextdirectory""")  
sc.union(Seq(text_file1,text_file2)).foreach(line => println(line._1))   
```
  
```Scala   
val text_file1 = sc.textFile("""C:\\Users\\korivi\\Downloads\\numbertextdirectory\\*.txt""")  
val text_file2 = sc.textFile("""C:\\Users\\korivi\\Downloads\\tabletextdirectory\\*.txt""")  
sc.union(Seq(text_file1,text_file2)).foreach(line => println(line))  
```
  
### Use the union operations in RDD to merge RDD's in iterative mode?  
```Scala
  val conf = new SparkConf()  
    .setAppName("WordCount")  
    .setMaster("local")  
  val sc = new SparkContext(conf)  
  var totalunionRDD: RDD[String] = sc.emptyRDD  
  //Read some example file to a test RDD  
  val list: List[String] = List("tabletextdirectory", "numbertextdirectory")  
  for(element <- list){  
    val present_csvfile = sc.textFile("C:\\Users\\korivi\\Downloads\\%s\\*".format(element))  
    totalunionRDD = totalunionRDD.union(present_csvfile)  
  }  
  //Displaying the RDD  
  totalunionRDD.foreach(line => println(line))  
  ```

### When to use map and flat map in spark?  
map for each Tuple or row level operations. Does one to one mapping operations.  
flat map for whole document level operations. Does one to none or one to many mapping operations.  
### Difference between GroupByKey, ReduceByKey, CombineByKey and AggregateByKey?  
#### GroupByKey:  
When called on an RDD of (k, v) pairs returns an RDD of (k, Iterable(V)) pairs.  
RDD's in nodes: [(k1, v1)(k1, v2)] [(k2, v1)(k1, v3)] [(k2, v4)(k1, v3)]  
RDD's in Driver:  [(k1, v1)(k1, v2)(k1, v3)(k1, v3)]=> (k1,(v1,v2,v3)) [(k2, v1)(k2, v4)]=>(k2, (v1,v4))  
Ex myPair.groupByKey()  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=12LLiL4aGF-G0bTj62CQxNHDfUZ3PinAC" width="650"/>
</p>  
  
#### ReduceByKey:  
Shuffling in nodes and aggregation in drivers  
When called on an RDD of (k, v) pairs, returns an RDD of (k,v) pairs where the values for each key are aggregated using the given  
reduce function. The function should be able to take arguments of same type and it returns same data type as a result.  
Ex: myPair.reduceByKey{case(a, b)=> a+b}  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=12EU_-_KVpw7CABcBJfC8mFA00NbN0wXB" width="650"/>
</p>  
  
#### CombineByKey:  
RDD's in nodes:  
Partition1=> [(Messi,45)(Messi,48)]  
Partition2=> [(Ronaldo,42)(Messi,54)]  
Partition3=> [(Ronaldo,52)(Ronaldo,51)]  
Syntax: myPair.combineByKey(createCombiner, which turns a value into a combiner (e.g., creates a one-element list), /*Shuffel in nodes*/    
                             mergeValue, to merge a value into a accumulated values (e.g., adds it to the end of a list),  /*Shuffel in nodes*/   
                             mergeCombiners, combine two or more combiners into a single one./*Driver side operation*/)  
Ex: myPair.combineByKey((comb:Int)=>(comb), (a:Int, comb:Int)=>(a + comb), (a:Int,b:Int)=>(a+b) )  
When combineByKey navigates through each element i.e for partition1=>(Messi, 45) it has a key which it has not seen before and when it   moves to next (Messi, 48) it gets a key which it has seen before. When "combineByKey()" sees an element for first time it uses a  
function  called "createCombiner" to create an initial value for the "accumulator" on the key i.e it use Messi as the key and 45 as   value. So current value of the "accumulator" of that "Key(Messi)" becomes 45.  
Now, next time combineByKey() sees the same key on the same partition, it does not use "createCombiner" instead it will make use of second function "merge Value" with current value of accumulator (45) and new value (48).  
Since, all this happens in parallel in different partitions. There is a chance that same key exist on other partition with other set of   "accumulators". So, when results from different partition has to be merged it uses "mergeCombiners" fuction.  
#### AggregateByKey:  
For reduceByKey the output needs to be same type has the Input argument.  
For combineByKey an initial value cannot be provided, but the output type can change from the input arguments type.  
AggregateByKey overcomes these both disadvantages but doesnot allow type changes to output from input in driver phase(merge combiner).  
Ex:- dataRDD.aggregateByKey(intial value)((merge values)/*shuffel in nodes*/,  
                                          (merge combiners)/*Shuffel in Driver*/)  
Average Computation: babyNamesCSV.aggregateByKey((0, 0))(  
        (acc, value) => (acc._1 + value, acc._2 + 1),  
        (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))  
    .mapValues(sumCount => 1.0 * sumCount._1 / sumCount._2)  
    .collect  
Changing Inputtypes:babyNamesCSV.aggregateByKey(0)  
                                 ((acc:Int,v:String) => acc+1, (acc1:Int,acc:Int) => acc1+acc2).foreach(println)
The aggregateByKey gets an "initial value" for each key given by the user and merges the values for the specified key on the same   "partitions" in "merge values". Then shuffles the data to merge the combiners in "merge combiners".  
### what is coalesce and repartition spark?  
### What is a Sparse Vector?  
A sparse vector has two parallel arrays –one for indices and the other for values. These vectors are used for storing non-zero entries to save space.  
### UDFs in Spark?  
### What do you understand by Pair RDD?  
Special operations can be performed on RDDs in Spark using key/value pairs and such RDDs are referred to as Pair RDDs. Pair RDDs allow users to access each key in parallel. They have a reduceByKey () method that collects data based on each key and a join () method that combines different RDDs together, based on the elements having the same key.  
### Differences in Spark Deployment mode(local[*] and yarn-cluster and yarn-client mode)?  
#### local[*]:  
Used to test the code by running the Driver and executors on the submitted machine.  
This cannot the advantages of distributed environment.  
[*] is the number of cpu cores to be allocated to perform the local operation and means that the driver can use all the available cores.  
```Scala
new SparkConf() .setMaster("local[*]")
```
#### Client:  
In client mode, the driver runs on the host where the job is submitted. The ApplicationMaster is merely present to request executor containers from YARN. The client communicates with those containers to schedule work after they start.  
<p align="center">
   <img src="https://www.dropbox.com/s/35k8pgvy5zyodam/Yarn_Client_mode.png?raw=1" width="650"/>
</p>  
The Program can interact with the client shell, where the program is submitted.  
In client mode, YARN automatically kills all executors if your driver is killed.  
  
```Scala
--master yarn --deploy-mode client
```  
  
#### Cluster:  
In cluster mode, the driver runs in the ApplicationMaster on a cluster host chosen by YARN. This means that the same process, which runs in a YARN container, is responsible for both driving the application and requesting resources from YARN. The client that launches the application doesn't need to continue running for the entire lifetime of the application.  
<p align="center">
   <img src="https://www.dropbox.com/s/szenqx2ry9k69cq/Yarn_Cluster_mode.png?raw=1" width="650"/>
</p>   
The cluster mode is not well suited to using spark interactively, it can only provide output to HDFS.  
In cluster mode, YARN restarts the driver without killing the executors. So, this depolyment mode is preferred for spark jobs running in Production.  
  
```Scala
--master yarn --deploy-mode cluster
```  
  
### Adding other jars(thrid party) in spark when execution in spark-submit?  
### What are broadcast and Accumulator variables in Spark?  
variables defined in program are generally only available to Drivers but the job is performed at the executors. So, the variables need to converted into the Broadcast variables using sc.broadcast technique, as seen below.  
val countries = List("us", "uk")  
val brc = sc.broadcast(countries)  
  
## SparkSQL  
### Detail about the SparkSession object in Spark 2.0? How does it differs from SQLCONTEXT and HIVECONTEXT?    
SparkSession provides a single point of entry to interact with underlying Spark functionality and allows programming Spark with DataFrame and DataSet APIs.  
Spark Session encapsulates SparkConf, SparkContext (or) SQLContext and HiveContext.  
```Scala
object Spark_practise {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("sparksessionexample").master("local")
      .enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext
    //set new runtime options
    spark.conf.set("spark.sql.shuffle.partitions", 6)
    spark.conf.set("spark.executor.memory", "2g")
    //get all settings
    val configMap:Map[String, String] = spark.conf.getAll
    for((k,v)<-configMap){
      println(k+":-"+v)
    }


    //create a Dataset using spark.range starting from 5 to 100, with increments of 5
    val numDS = spark.range(5, 100, 5)
    // reverse the order and display first 5 items
    numDS.orderBy(desc("id")).show(5)
    //compute descriptive stats and display them
    numDS.describe().show()
    // create a DataFrame using spark.createDataFrame from a List or Seq
    val langPercentDF = spark.createDataFrame(List(("Scala", 35), ("Python", 30), ("R", 15), ("Java", 20)))
    //rename the columns
    val lpDF = langPercentDF.withColumnRenamed("_1", "language").withColumnRenamed("_2", "percent")
    //order the DataFrame in descending order of percentage
    lpDF.orderBy(desc("percent")).show()

    // read the json file and create the dataframe
    val zipsDF = spark.read.json("zips.json")
    //filter all cities whose population > 40K
    zipsDF.filter(zipsDF.col("pop") > 40000).show(10)
    // Now create an SQL table and issue SQL queries against it without
    // using the sqlContext but through the SparkSession object.
    // Creates a temporary view of the DataFrame
    zipsDF.createOrReplaceTempView("zips_table")
    zipsDF.cache()
    val resultsDF = spark.sql("SELECT * FROM zips_table limit 10")
    resultsDF.show(10)
    //drop the table if exists to get around existing table error
    spark.sql("DROP TABLE IF EXISTS zips_hive_table")
    //save as a hive table
    spark.table("zips_table").write.saveAsTable("zips_hive_table")
    //make a similar query against the hive table
    val resultsHiveDF = spark.sql("SELECT city, pop, state, zip FROM zips_hive_table WHERE pop > 40000")
    resultsHiveDF.show(10)


    //fetch metadata data from the catalog
    spark.catalog.listDatabases.show(false)
    spark.catalog.listTables.show(false)

  }
}
```
### Illustrate creation and conversions and File i/o operations between RDD's, DataFrame and DataSet?  
```Scala
import org.apache.spark.sql._  
  
case class Trans(accNo: String, tranAmount: Double)  
  
object spark_git extends App{  
  
  //initialising sparksession object  
  val spark = SparkSession.builder.appName("DataFrame_Programming").master("local").enableHiveSupport().getOrCreate()  
  
  //an array of data  
  val acTransList = Array("SB10001,1000", "SB10002,1200", "SB10003,8000", "SB10004,400", "SB10005,300", "SB10006,10000",  "SB10007,500", "SB10008,56", "SB10009,30","SB10010,7000", "CR10001,7000","SB10002,-10")  
  
  //creation of RDD from a collection  
  val acTransRDD = spark.sparkContext.parallelize(acTransList)  
  //creation of RDD from a textfile  
  val textFile = spark.sparkContext.textFile("hdfs://<location in HDFS>")  
  
  //creation of DataFrame from a DataSource  
  val acTransDFfromParquet = spark.read.parquet("scala.trans.parquet")  
  //creation of DataFrame from a RDD  
  import spark.implicits._  
  val acTransDF = acTransRDD.map(_.split(","))  
    .map(line => Trans(line(0), line(1).trim.toDouble)).toDF()  
  //or  
  //val acTransDF = spark.createDataFrame(acTransRDD.map(_.split(","))  
  // .map(line => Trans(line(0), line(1).trim.toDouble)))  
  
  
  //creation of DataSet from a DataSource  
  val acTransDS1= spark.read.parquet("scala.trans.parquet").as[Trans]  
  //creation of DataSet from an RDD  
  import spark.implicits._  
  val acTransDS = acTransRDD.map(_.split(","))  
    .map(line => Trans(line(0), line(1).trim.toDouble)).toDS()  
  //Manipulation of Dataset  
  acTransDS.filter(row => row.tranAmount>0&&row.accNo.startsWith("SB"))  
    .show(acTransDS.count.toInt)  
  //creation of Dataset from a DataFrame  
  //This type of conversion is really required when reading data from  
  //external sources such as JSON, Avro or Parquet files.  
  //simiral to spark.read.json("Path/tofile.json").as[Trans]  
  val acTransDF_DS = acTransDF.as[Trans]  
    
    
  //Conversions  
  //Conversion of DataFrame to rdd  
  val DF_to_rdd = acTransDF.rdd.map(line=>line.getDouble(1)).reduce(_+_)  
  //Conversion of DataSet to rdd  
  acTransDS.rdd.map(row=> List(row.accNo, row.tranAmount)).foreach(println)  
  //Conversion of DataSet to DataFrame  
  val acTransDS_DF = acTransDS.toDF()  
  
  //writing to files  
  acTransRDD.saveAsTextFile("Path_to_textFile_Directory")  
  acTransDF.write.parquet("DF")  
  acTransDS.write.parquet("DS")  
}  
```
### Difference between toDF() and createDataFrame() methods for creating DataFrames?  
When case class is provide it would be useful to import sqlcontext.implicits._ and use toDF() method to convert rdds to dataframes.  
BUT  
When you declared schema using StructType and StructField its usefule to create dataframes using createDataFrame  
Using toDF():  
```Scala
case class Data(ID:String, infect:String, induct:String, adult:String)  
import sqlContext.implicits._  
val CSV_files_DF = CSV_files.map(  
      line => line.split(",")  
    ).map(line => Data(line(0),  
    line(1), line(2),  
    line(3))).toDF()  
```
Using createDataFrame():  
Using case class:  
```Scala
case class Trans(accNo: String, tranAmount: Double)  
val acTransRDD =  
      sc.parallelize(acTransList).map(_.split(",")).map(  
        line => Trans(line(0), line(1).trim.toDouble)  
      )  
val acTransDF = sqlContext.createDataFrame(acTransRDD)  
```
Using Programatic schema:  
```Scala
val empFrameWithRenamedColumns = sc.textFile("Path_to_CSVFile")  
val SchemaString = "Emp_ID,Emp_name"  
import org.apache.spark.sql.Row  
import org.apache.spark.sql.types.{StringType, StructField, StructType}  
val schema = StructType(SchemaString.split(",").map(fieldName =>  
                        StructField(  
                        fieldName, StringType, true  
                        )))  
val rowRDD = empFrameWithRenamedColumns.map(_.split(",")).map(p => Row(p(0).toInt, p(1)))  
val empDataFrame = sqlContext.createDataFrame(rowRDD, schema)  
```
### Difference between an RDD and DataFrame and DataSet?  
Data Representation:  
RDD: RDD is a distributed collection of data elements spread across many machines in the cluster. RDDs are a set of Java or Scala   objects representing data.  
DataFrame: A DataFrame is a distributed collection of data organized into named columns. It is conceptually equal to a table in a   relational database.  
Dataset: It is an extension of DataFrame API that provides the functionality of – type-safe, object-oriented programming interface of   the RDD API and performance benefits of the Catalyst query optimizer and off heap storage mechanism of a DataFrame API.  
Compile-time type safety:  
RDD: RDD provides a familiar object-oriented programming style with compile-time type safety.  
DataFrame: If you are trying to access the column which does not exist in the table in such case Dataframe APIs does not support   compile-time error. It detects attribute error only at runtime.  
Dataset: It provides compile-time type safety.  
Optimization:  
RDD: No inbuilt optimization engine is available in RDD. When working with structured data, RDDs cannot take advantages of sparks     advance optimizers. For example, catalyst optimizer and Tungsten execution engine. Developers optimize each RDD on the basis of its   attributes.  
DataFrame: Optimization takes place using catalyst optimizer. Dataframes use catalyst tree transformation framework in four phases.  
Analyzing a logical plan to resolve references.  
Logical plan optimization.  
Physical planning.  
Code generation to compile parts of the query to Java bytecode.  
Dataset: It includes the concept of Dataframe Catalyst optimizer for optimizing query plan.  
Garbage Collection:  
RDD: There is overhead for garbage collection that results from creating and destroying individual objects.  
DataFrame: Avoids the garbage collection costs in constructing individual objects for each row in the dataset.  
Dataset: There is also no need for the garbage collector to destroy object because serialization  
takes place through Tungsten. That uses off heap data serialization.  
Usage:  
RDD:Can be used whenever there is a need to process any kind to data with very high flexibility in terms of the data processing     requirements and having the lowest API level control such as library development, the RDD-based programming model is ideal.  
DataFrame&DataSet: Whenever there is a need to process structured data with flexibility for accessing and processing data with optimized  performance across all the supported programming languages, the DataFrame-based SparkSQL programming model is ideal.  
### Illustrate application of map and reduce(Action and Transformations) on DataFrames and DataSets?  
On DataFrames:  
```Scala
val variable = DataFrame.map(row =>row.getAs[Col_Type]("Column_name")).reduce(_ + _)  
```
On DataSet:  
```Scala
val sumAmount = goodTransRecords.map(row =>row.Column_name).reduce(_ + _)
```
### What do you understand by SchemaRDD?  
An RDD that consists of row objects (wrappers around basic string or integer arrays) with schema information about the type of data in each column.  
### Case class in SparkSQL?  
### Data preparation methods in SparkSQL?(Chapter 3 Spark Data Analytics cook book)  
### How can you remove the elements with a key present in any other RDD?  
Use the subtractByKey () function  
  
## SparkStreaming  
### Explain the Process of Spark Streaming?  
### What is a DStream?  
Discretized Stream is a sequence of Resilient Distributed Databases that represent a stream of data. DStreams can be created from various sources like Apache Kafka, HDFS, and Apache Flume. DStreams have two operations –  
Transformations that produce a new DStream.  
Output operations that write data to an external system.  
### Explain about Batch Interval, Windows size and Sliding Interval? How are these related to DStream processing?  
Batch Interval - The interval for which a DStream is created. This is provided while creating the Streaming Context.  
Window Duration/Size - The window duration over which a certain fold operation needs to be performed. Should be a multiple of batch interval  
Sliding Interval - The interval over which the window should be slided. Should be a multiple of batch interval  
The number of records in one batch is determined by the batch interval. A window will keep the number of batches as fit within the size of a window, that's why the window size must be a multiple of the batch interval. Your operations will then run on multiple batches, and with each new batch the window will move forward, discarding older batches.  
The point is that in streaming, data that belongs together doesn't necessarily arrive at the same time, especially at low batch intervals. With windows you are essentially looking back in time.  
But note that your job still runs at the specified batch interval, so it will produce output at the same pace as before but look at more data at once. You will also look at the same data multiple times!  
### Explain about the different types of transformations on DStreams?  
Stateless Transformations- Processing of the batch does not depend on the output of the previous batch. Examples – map (), reduceByKey (), filter ().  
Stateful Transformations- Processing of the batch depends on the intermediary results of the previous batch. Examples –Transformations that depend on sliding windows.  
### What is the difference between persist() and cache()?  
persist () allows the user to specify the storage level whereas cache () uses the default storage level.  
### What are the various levels of persistence in Apache Spark?  
Apache Spark automatically persists the intermediary data from various shuffle operations, however it is often suggested that users call persist () method on the RDD in case they plan to reuse it. Spark has various persistence levels to store the RDDs on disk or in memory or as a combination of both with different replication levels.  
The various storage/persistence levels in Spark are -  
MEMORY_ONLY  
MEMORY_ONLY_SER  
MEMORY_AND_DISK  
MEMORY_AND_DISK_SER, DISK_ONLY  
OFF_HEAP  
### Explain Specific Streaming process and saving files to HDFS in SparkStreaming with Kafka?  
### How to create Streams for Basic Input SOurces like file and Socket & Advanced Sources like Kafka, Twitter and Flume?  
  
  
# Hive and Impala  
  
### Difference between Impala and Hive?  
### Disadvantages of Impala?(speculative execution)  
### What are the kind of tables used in Hive?Why?  
Hive has a relational database on the master node it uses to keep track of state. For instance, when you CREATE TABLE FOO(foo string) LOCATION 'hdfs://tmp/';, this table schema is stored in the database.  
External table- When you want for Data retrival after droping the table. when external table is declared, default table path is changed to specified location in hive. When you drop an external table, it only drops the meta data but not the Data. 
Internal table- to add data to the already exsiting table. When you drop an internal table, it drops the data, and it also drops the metadata.  
### What is Partioning and bucketing in hive & Partionting and Bucketing difference?  
Partioning of Hive tables can result in unknown number of Directories in Hive default Table storge path, depending on the Cloumns used.Partitioning data is often used for distributing load horizontally, this has performance benefit, and helps in organizing data in a logical fashion.  
  
Bucketing is another technique for decomposing data sets into more manageable parts in each Hive_Table Partition. Records with the same Column_value will always be stored in the same bucket. Hive can create a logically correct sampling. Bucketing also aids in doing efficient map-side joins and will be stored in a given number of Buckets specified in the Query.  
  
Partitioning is not bounded during schema decleration. While bucketting is bounded to fixed number, during schema decleration.
  
set hive.exec.dynamic.partition=true;  
set hive.enforce.bucketing = true;  
create table IF NOT EXISTS hotelhodsrrequest_PCCparsing_scala_Partitioned_and_Bucketed (PCC String, PropertyCode String, Duplication_Counts int)  
partitioned by (Year String, Month String)  
CLUSTERED BY (PCC) INTO 20 BUCKETS;  
  
INSERT OVERWRITE TABLE hotelhodsrrequest_PCCparsing_scala_Partitioned_and_Bucketed partition (Year, Month)  
SELECT hotelhodsrrequest_pccparsing_scala.pcc as PCC,  
hotelhodsrrequest_pccparsing_scala.propertycode as PropertyCode,  
hotelhodsrrequest_pccparsing_scala.duplication_counts as Duplication_Counts,  
hotelhodsrrequest_pccparsing_scala.year as Year,  
hotelhodsrrequest_pccparsing_scala.month as Month  
FROM hotelhodsrrequest_PCCparsing_scala;  
### Partioning External Table in Hive?  
Has creating a external table involves changing of the Default Table Data path, we need add each partion manullay using Alter Table command. ALTER TABLE user ADD PARTITION(date='2010-02-22') to update the metadata in Hive metastore.  
### Creation, Partitioning and loading data into internal and external table?  
#### Internal Table:  
```SQL 
Creation:  
CREATE TABLE weather (wban INT, date STRING, precip INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ‘,’
LOCATION ‘ /hive/data/weather’;
```
```SQL
Load the Data in Table:  
LOAD DATA INPATH ‘hdfs:/data/2012.txt’ INTO TABLE weather;  
```
#### External Tables:  
```SQL
Creation:
CREATE EXTERNAL TABLE weatherext ( wban INT, date STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ‘,’
LOCATION ‘ /hive/data/weatherext’;
```
```SQL
Loading data:
LOAD DATA INPATH ‘hdfs:/data/2012.txt’ INTO TABLE weatherext;
```
#### Partitioning:  
##### Internal Table:  
```SQL
Creation:  
CREATE TABLE user (
  userId BIGINT,
  type INT,
  level TINYINT,
)
COMMENT 'User Infomation'
PARTITIONED BY (date String)
```
```SQL
Loading the Data:
LOAD INPATH '/user/chris/data/testdata' OVERWRITE INTO TABLE user PARTITION (date='2012-02-22');
```
##### External Table:
```SQL
Creation:  
CREATE EXTERNAL TABLE user (
  userId BIGINT,
  type INT,
  level TINYINT,
  date String
)
COMMENT 'User Infomation'
PARTITIONED BY (date String)
LOCATION '/user/chris/datastore/user/';
```
```SQL
Loading the Data:
LOAD DATA INPATH ‘hdfs:/data/2012.txt’ INTO TABLE weatherext PARTITION (year=2012, month=’01’);
LOAD DATA INPATH ‘hdfs:/data/2012.txt’ INTO TABLE weatherext PARTITION (year=2012, month=’02’);
```
```SQL
UpDate MetaStore for partitions individually:
ALTER TABLE user ADD PARTITION(date='2010-02-22');
```
### DIfference between where and having clauses?  
HAVING is used to check conditions after the aggregation takes place.  
WHERE is used before the aggregation takes place.  
The HAVING clause was added to SQL because the WHERE keyword could not be used with aggregate functions.  
### How many mapper will "select * from Data" launch?  
No Mapper & No Reducer  
### Does "where = """ launch Mapper or reducers?  
Mappers only  
### Does "Group by = """ launch Mapper or reducers?  
Reducer only
### UDF's in Hive and Hwo do you Implement it and How to load UDF's in Hive?  
### Invalidate metaData command in Impala?  
### How to change default Heap Dump memory parameters in Hive using set command?  
### Difference between Map side and reduce side joins?  
### Difference between GroupBy, OrderBy, sortBy, DISTRIBUTE BY and CLUSTER BY x?  
GroupBy=> Aggregates the Data into groups like using SUM, Avg etc.. on a cloumn. The GROUP BY clause’s purpose is summarize unique combinations of columns values. Often group by is followed by order by or cluster by cluases.  
ORDER BY guarantees global ordering, but does this by pushing all data through just one reducer. This is basically unacceptable for large datasets. You end up one sorted file as output.  
SORT BY x: orders data at each of N reducers, but each reducer can receive overlapping ranges of data. You end up with N or more sorted files with overlapping ranges.  
DISTRIBUTE BY x: ensures each of N reducers gets non-overlapping ranges of x, but doesn't sort the output of each reducer. You end up with N or unsorted files with non-overlapping ranges.  
CLUSTER BY x: ensures each of N reducers gets non-overlapping ranges, then sorts by those ranges at the reducers. This gives you global ordering, and is the same as doing (DISTRIBUTE BY x and SORT BY x). You end up with N or more sorted files with non-overlapping ranges.
So CLUSTER BY is basically the more scalable version of ORDER BY.  
### Regular expressions in hive?  
### How to Query Avro struct, Array and key:value pairs in a Hive table?  
### Different join methods in Hive(using distributed Chace) and turning Hive queries for Small table, Big table using Distributed cache?   
  
  
# Sqoop  
### How to Import and export Data using Sqoop?  
#### Sqoop Import
Creation of External table : External tables in hive are kind of permanent tables and stays there even if hive is stopped or server goes down. "EXTERNAL" keyword is used to specify table type.  
  
CREATE EXTERNAL TABLE IF NOT EXISTS HIVEDB.HIVE_TABLE1 (DATE_COL DATE,   
BIG_INT_COL BIGINT, INT_COL INT, VARCHAR_COL VARCHAR(221), FLOAT_COL FLOAT);  
Import the data using Sqoop : Specify the created table name while importing the data, instead of using "--hive-create" option.  
  
sqoop import --connect jdbc:mysql://mysqlhost/mysqldb --username user --password   
passwd --query "SELECT table1.date_col, table1.big_int_col, table1.int_col,   
table1.varchar_col, table1.float_col FROM MYSQL_TABLE1 AS table1 WHERE   
\$CONDITIONS" --split-by table1.date_col --hive-import   
--hive-table hivedb.hive_table1 --target-dir hive_table1_data`  
  
### Does sqoop launches only mapper?  
Yes, Sqoop lauches only Mappers to import and Export Data.  
  
  
# HDFS  
### What is splittablity and block compression and How does it effects different file formats like CSV, JSON, XML, AVRO and Parquet?  
### Falut Tolerence and high availability in HDFS?(Replication and Speculative execution)  
  
  
# Cassandra   
### Explain Cassandra Arcitecture and timeStamps methods in it?  

