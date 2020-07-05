# combineByKey 
combineByKey can be used when you are combining elements but your return type differs from your input value type. Spark combineByKey is a transformation operation on PairRDD (i.e. RDD with key/value pair). It is a wider operation as it requires shuffle in the last stage.  
  
// Creating PairRDD studentRDD with key value pairs  
```code  
val studentRDD = sc.parallelize(Array(

    ("Joseph", "Maths", 83), ("Joseph", "Physics", 74), ("Joseph", "Chemistry", 91), 

    ("Joseph", "Biology", 82), ("Jimmy", "Maths", 69), ("Jimmy", "Physics", 62), 

    ("Jimmy", "Chemistry", 97), ("Jimmy", "Biology", 80), ("Tina", "Maths", 78), 

    ("Tina", "Physics", 73), ("Tina", "Chemistry", 68), ("Tina", "Biology", 87), 

    ("Thomas", "Maths", 87), ("Thomas", "Physics", 93), ("Thomas", "Chemistry", 91), 

    ("Thomas", "Biology", 74), ("Cory", "Maths", 56), ("Cory", "Physics", 65), 

    ("Cory", "Chemistry", 71), ("Cory", "Biology", 68), ("Jackeline", "Maths", 86), 

    ("Jackeline", "Physics", 62), ("Jackeline", "Chemistry", 75), ("Jackeline", "Biology", 83), 

    ("Juan", "Maths", 63), ("Juan", "Physics", 69), ("Juan", "Chemistry", 64), 

    ("Juan", "Biology", 60)), 3)
```  
//Defining createCombiner, mergeValue and mergeCombiner functions  
 ```code  
 def createCombiner = (tuple: (String, Int)) => (tuple._2.toDouble, 1)
def mergeValue = (accumulator: (Double, Int), element: (String, Int)) => (accumulator._1 + element._2, accumulator._2 + 1)
def mergeCombiner = (accumulator1: (Double, Int), accumulator2: (Double, Int)) => (accumulator1._1 + accumulator2._1, accumulator1._2 + accumulator2._2)
 ```  
// use combineByKey for finding percentage  
```code
val combRDD = studentRDD.map(t => (t._1, (t._2, t._3)))
                        .combineByKey(createCombiner, mergeValue, mergeCombiner)
                        .map(e => (e._1, e._2._1/e._2._2))
```  
//Check the Output  
```code
combRDD.collect foreach println
// Output
// (Tina,76.5)
// (Thomas,86.25)
// (Jackeline,76.5)
// (Joseph,82.5)
// (Juan,64.0)
// (Jimmy,77.0)
// (Cory,65.0)
```   
  
# AggregateByKey  
AggregateByKey is same like combineByKey and there is slight difference in functioning and arguments. The aggregateByKey function is used to aggregate the values for each key and adds the potential to return a different value type.  
The three parameters of aggregateByKey function,     
zeroValue: As we are finding maximum marks out of all subjects we should use Double.MinValue (which is also known as an accumulator)  
seqOp: Sequential operation is an operation of finding maximum marks (operation at each partition level data)  
combOp: Combiner operation is an operation of finding maximum marks from two values (operation on aggregated data of all partitions)  
```code
scala> val babyNamesCSV = sc.parallelize(List(("David", 6), ("Abby", 4), ("David", 5), ("Abby", 5)))
babyNamesCSV: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[0] at parallelize at <console>:12
scala> babyNamesCSV.reduceByKey((n,c) => n + c).collect
res0: Array[(String, Int)] = Array((Abby,9), (David,11))
scala> babyNamesCSV.aggregateByKey(0)((k,v) => v.toInt+k, (v,k) => k+v).collect
res1: Array[(String, Int)] = Array((Abby,9), (David,11))
```  
  
# reduceByKey  
reduceByKey Spark RDD reduceByKey function merges the values for each key using an associative reduce function. Basically reduceByKey function works only for RDDs which contains key and value pairs kind of elements(i.e RDDs having tuple or Map as a data element). It is a transformation operation which means it is lazily evaluated.
  
# groupByKey    
groupByKey is just to group your dataset based on a key. It will result in data shuffling when RDD is not already partitioned. When a groupByKey is called on a RDD pair the data in the partitions are shuffled over the network to form a key and list of values.    
```code
val words = Array("one", "two", "two", "three", "three", "three")
val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
val wordCountsWithReduce = wordPairsRDD
  .reduceByKey(_ + _)
  .collect()
val wordCountsWithGroup = wordPairsRDD
  .groupByKey()
  .map(t => (t._1, t._2.sum))
  .collect()
```  
