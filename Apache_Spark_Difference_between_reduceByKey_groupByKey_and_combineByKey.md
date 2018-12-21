Spark comes with a lot of easy to use aggregate functions out of the box. For the same reason spark becomes a powerful technology for ETL on BigData.

Grouping the data is a very common use case in the world of ETL. Just like aggregate transformation in ETL tools like Ab-initio or Informatica, where the results can be grouped and aggregate functions can be applied.

e.g. Group all customer order based on customer key, find the best sales year , find the worst player in baseball based on strike rate etc. etc.

Unlike standard ETL tools sparks comes with three transformations to achieve the same result but in different ways.

1) reduceByKey 
2) groupByKey  
3) combineByKey  

These are three transformation available in spark which can be used interchangeably.

Before getting to further details, it is important to understand all this transformations are applied on tuples or pairs.A tuple is a key-value pair just like hash in Ruby or hashmap in Java , JSON etc.

simple e.g. (one,1)(two,2) where keys are one and two , values are 1 and 2

For further dissection let us take a sports use case. Two best soccer players of our times Lionel Messi and Cristiano Ronaldo. Below are the goal scored by these players in last 4 years. We need to find the total goal scored by player, average score etc.


Data is in this format available in a text file (soccer.txt).
```Data
Messi 45
Ronaldo 52
Messi 54
Ronaldo 51
Messi 48
Ronaldo 42
```
As mentioned earlier Spark's aggregate functions work on pairs or tuples, so we need to covert this data to a key value pair. Key in this case will be player name and Value will be goals scored each year. So our initial target is to get the data in below format.
```rdd
(Messi,45)
(Messi,48)
(Ronaldo,52)
(Ronaldo,42)
(Messi,54)
(Ronaldo,51)
```
In Scala this can be done in a single step
```Scala
 //set up spark context for local run
    val c SparkContext("local[8]","Learn Aggregate Functions")
    val mydata=conf.textFile("soccer.txt")
    //Converting data to a tuple, by splitting at delimiter. Score converted to a number explicitly
    val myPair=mydata.map{k => (k.split(" ")(0),k.split(" ")(1).toInt)}
```
Output of myPair(RDD) is now key value pair,
```Scala
(Messi,45)
(Messi,48)
(Ronaldo,52)(Ronaldo,42)(Messi,54)(Ronaldo,51)
```
