Algorithm: A mathematical formula or statistical process used to perform an analysis of data. How is Algorithm is related to Big Data? Even though algorithm is a generic term, Big Data analytics made the term contemporary and more popular. (Bonus: Here’s a pickup line on your date, ‘You show me your algorithms and I’ll show you mine.  ….  Ok, Ok, I’ll stop! No more cheesy jokes)
 
Analytics: Most likely, your credit card company sent you year-end statement with all your transactions for the entire year. What if you dug into it to see what % you spent on food, clothing, entertainment etc? You are doing ‘analytics’. You are drawing insights from your raw data which can help you make decisions regarding spending for the upcoming year. What if you did the same exercise on tweets or facebook posts made by your friends/network or your own your company. Now we are talking Big Data analytics. It is about making inferences and story-telling with large sets of data.There are 3 or 4 different types of analytics depending on who you talk to.
 
Descriptive Analytics: If you just told me that you spent 25% on food, 35% on clothing, 20% on entertainment and the rest on miscellaneous items last year using your credit card, that is descriptive analytics. Of course, you can go into lot more detail as well.

Predictive Analytics: If you analyzed your credit card history for the past 5 years and the split is somewhat consistent, you can safely forecast with high probability that next year will be similar to past years. The fine print here is that this is not about ‘predicting the future’ rather ‘forecasting with probabilities’ of what might happen. In Big data predictive analytics, data scientists may use advanced techniques like data mining, machine learning, and advanced statistical processes (we’ll discuss all these terms later) to forecast weather, economy etc.

Prescriptive Analytics: Still using the credit card transactions example, you may want to find out which spending to target (i.e. food, entertainment, clothing etc.) to make a huge impact on your overall spending. Prescriptive analytics builds on predictive analytics by including ‘actions’ (i.e. reduce food or clothing or entertainment) and analyzing the resulting outcomes to ‘prescribe’ the best category to target to reduce your overall spend. You can extend this to big data and imagine how executives can make data-driven decisions by looking at the impacts of various actions in front of them.

<p align="center">
  <img src="https://www.dropbox.com/s/vyie53g2fchuxmg/analytics_nature.png?raw=1" width="650"/>
</p>  
<p align="center">
  <img src="https://www.dropbox.com/s/md7e6fldnkempp0/analytics_solutions.png?raw=1" width="650"/>
</p> 
 
Batch processing: Even though Batch data processing has been around since mainframe days, Batch processing gained additional significance with Big Data given the large datasets that it deals with. Batch data processing is an efficient way of processing high volumes of data where a group of transactions is collected over a period of time. Hadoop, which I’ll describe later, is focused on batch data processing.
 
Cassandra, a beautiful name, is a popular open source database management system managed by The Apache Software Foundation. Apache can be credited with many big data technologies and Cassandra was designed to handle large volumes of data across distributed servers.

Cloud computing: Well, cloud computing has become ubiquitous so it may not be needed here but I included just for completeness sake. It’s essentially software and/or data hosted and running on remote servers and accessible from anywhere on the internet.
 
Cluster computing: It’s a fancy term for computing using a ‘cluster’ of pooled resources of multiple servers. Getting more technical, we might be talking about nodes, cluster management layer, load balancing, and parallel processing etc.

Dark Data: This, in my opinion, is coined to scare the living daylights out of senior management. Basically, this refers to all the data that is gathered and processed by enterprises not used for any meaningful purposes and hence it is ‘dark and may never be analyzed. It could be social network feeds, call center logs, meeting notes and what have you. There are many estimates that anywhere from 60-90% of all enterprise data may be ‘dark data’ but who really knows.
 
Data lake: When I first heard of this, I really thought someone was pulling an April fool’s joke. But it’s a real term! Data lake is a large repository of enterprise-wide data in raw format.

Data warehouse:It is a repository for enterprise-wide data but in a structured format after cleaning and integrating with other sources. Data warehouses are typically used for conventional data (but not exclusively). Supposedly data lake makes it easy to access enterprise-wide data you really need to know what you are looking for and how to process it and make intelligent use of it.
 
Data mining: Data mining is about finding meaningful patterns and deriving insights in large sets of data using sophisticated pattern recognition techniques. It is closely related the term Analytics that we discussed earlier in that you mine the data to get analytics. To derive meaningful patterns, data miners use statistics(yup, good old math), machine learning algorithms, and artificial intelligence.
 
Data Scientist: Talk about a career that is HOT! It is someone who can make sense of big data by extracting raw data (did u say from data lake?), massage it, and come up with insights. Some of the skills required for data scientists are what a superman/woman would have: analytics, statistics, computer science, creativity, story-telling and understand business context. No wonder they are so highly paid.

Distributed File System: As big data is too large to store on a single system, Distributed File System is a data storage system meant to store large volumes of data across multiple storage devices and will help decrease the cost and complexity of storing large amounts of data.
 
ETL: ETL stands for extract, transform, and load. It refers to the process of ‘extracting’ raw data, ‘transforming’ by cleaning/enriching the data for ‘fit for use’ and ‘loading’ into the appropriate repository for the system's use. Even though it originated with data warehouses, ETL processes are used while ‘ingesting i.e. taking/absorbing data from external sources in big data systems.

Hadoop: When people think of big data, they immediately think about Hadoop. Hadoop (with its cute elephant logo) is an open source software framework that consists of what is called a Hadoop Distributed File System (HDFS) and allows for storage, retrieval, and analysis of very large data sets using distributed hardware. If you really want to impress someone, talk about YARN (Yet Another Resource Scheduler) which, as the name says it, is a resource scheduler. I am really impressed by the folks who come up with these names. Apache foundation, which came up with Hadoop, is also responsible for Pig, Hive, and Spark (yup, they are all names of various software pieces). Aren’t you impressed with these names?

In-memory computing: In general, any computing that can be done without accessing I/O is expected to be faster. In-memory computing is a technique to moving the working datasets entirely within a cluster's collective memory and avoid writing intermediate calculations to disk. Apache Spark is is an in-memory computing system and it has huge advantage in speed over I/O bound systems like Hadoop's MapReduce.

IoT: The latest buzzword is Internet of Things or IOT. IOT is the interconnection of computing devices in embedded objects (sensors, wearables, cars, fridges etc.) via internet and they enable sending / receiving data. IOT generates huge amounts of data presenting many big data analytics opportunities.
 
Machine learning: Machine learning is a method of designing systems that can learn, adjust, and improve based on the data fed to them. Using predictive and statistical algorithms that are fed to these machines, they learn and continually zero in on "correct" behavior and insights and they keep improving as more data flows through the system. Fraud detection, online recommendations based  
  
<p align="center">
  <img src="https://www.dropbox.com/s/7ck2nqyprprkdq0/Machine_Learning.PNG?raw=1" width="650"/>
</p>  
  
MapReduce: MapReduce could be little bit confusing but let me give it a try. MapReduce is a programming model and the best way to understand this is to note that Map and Reduce are two separate items. In this, the programming model first breaks up the big data dataset into pieces (in technical terms into ‘tuples’ but let’s not get too technical here) so it can be distributed across different computers in different locations (i.e. cluster computing described earlier) which is essentially the Map part. Then the model collects the results and ‘reduces’ them into one report. MapReduce’s data processing model goes hand-in-hand with hadoop’s distributed file system.
 
NoSQL: It almost sounds like a protest against ‘SQL (Structured Query Language) which is the bread-and-butter for traditional Relational Database Management Systems (RDBMS) but NOSQL actually stands for Not ONLY SQL :-). NoSQL actually refers to database management systems that are designed to handle large volumes of data that does not have a structure or what’s technically called a ‘schema’ (like relational databases have). NoSQL databases are often well-suited for big data systems because of their flexibility and distributed-first architecture needed for large unstructured databases.

R: Can anyone think of any worse name for a programming language? Yes, ‘R’ is a programming language that works very well with statistical computing. You ain’t a data scientist if you don;’t know ‘R’. (Please don’t send me nasty grams if you don’t know ‘R’). It is just that ‘R’ is one of the most popular languages in data science.

Spark (Apache Spark): Apache Spark is a fast, in-memory data processing engine to efficiently execute streaming, machine learning or SQL workloads that require fast iterative access to datasets. Spark is generally a lot faster than MapReduce that we discussed earlier.
 
Stream processing: Stream processing is designed to act on real-time and streaming data with “continuous” queries. Combined with streaming analytics i.e. the ability to continuously calculate mathematical or statistical analytics on the fly within the stream, stream processing solutions are designed to handle high volume in real time. 

Structured v Unstructured Data: This is one of the ‘V’s of Big Data i.e.Variety. Structured data is basically anything than can be put into relational databases and organized in such a way that it relates to other data via tables. Unstructured data is everything that can’t – email messages, social media posts and recorded human speech etc.
