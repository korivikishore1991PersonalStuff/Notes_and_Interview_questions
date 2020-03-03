## Interview#  
Upsert operations in Hadoop?Using Delta Lake and not using Delta Lake. Upsert is basically a merging operation which involves insert or update an existing record in one call i.e a new record will be inserted, while old records present in the current batch will be updated and old records not present in the current batch will be let unaltered.  
beeline vs hive what is the difference? Basically hive works when hive-client is installed on node in the cluster, where as beeline is used for remotly connecting to a separate HiveServer2 process over Thrif.  
The primary difference between the two involves how the clients connect to Hive. The Hive CLI connects directly to the Hive Driver and requires that Hive be installed on the same machine as the client. However, Beeline connects to HiveServer2 and does not require the installation of Hive libraries on the same machine as the client. Beeline is a thin client that also uses the Hive JDBC driver but instead executes queries through HiveServer2, which allows multiple concurrent client connections and supports authentication. HiveServer2 also offers enchanced security via JDBC polices in Ranger and sentry.  
How do you pass arguments in Hive? Using hivevar in -f or ${} in -e  
Hive map side joins and other optimizations?  
Spark executors and drivers configurations?  
Spark sql-shuffle-join and repartitions?  
Hadoop small file probleams and possible compactions?  
What is star schema?  
Difference between Machine Learning, Data Science, AI, Deep Learning, and Statistic?  
Joins pocedure in Map/Reduce?  
Procedure to convert RDD to DataFrames?  
Difference between a namenode and edgenode in Hadoop?  
What is a LinkedList and an ArrayList in Java?  
Explain Hashing consept in Java?  
What are sets and maps in Java?  
