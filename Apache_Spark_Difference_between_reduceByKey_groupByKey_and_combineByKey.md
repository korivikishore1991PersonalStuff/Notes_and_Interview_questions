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
Now let us try to use aggregate functions one at a time.

```Des
groupByKey - When called on a dataset of (K, V) pairs, returns a dataset of (K, Iterable) pairs.
By default, the level of parallelism in the output depends on the number of partitions of the parent RDD.
```
A groupByKey on myPair gives a key and iterate-able Value (list) as expected.
```Scala
myPair.groupByKey().foreach(println)
              Messi,CompactBuffer(45, 54, 48))
              (Ronaldo,CompactBuffer(52, 51, 42))
```
Let us find out what is the total goals scored by players. To achieve this we need to apply a function on the iterable list obtained from groupByKey().Function used here is reduce. reduce aggregate the elements of the dataset using a user provided function. The function should be commutative and associative so that it can be computed correctly in parallel.
```code
myPair.groupByKey().mapValues { x => x.reduce((a,b)=>a + b ) }.foreach(println)
```
```output
(Messi,147)
(Ronaldo,185)
```
Same result can be achieved using
```code
myPair.groupByKey().map { x => (x._1, x._2.sum) }.foreach(println)
```
When a groupByKey is called on a RDD pair the data in the partitions are shuffled over the network to form a key and list of values. This is a costly operation particularly when working on large data set. This might also cause trouble when the combined value list is huge to occupy in one partition. In this case a disk spill will occur.  
```Figue_1```   
reduceByKey is a better bet in this case.

```des
reduceByKey(function) - When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function. The function should be able to take arguments of some type and it returns same result data type. Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.
```
Our same use case can be achieved using reduceByKey and a function which adds up the values.

```code
myPair.reduceByKey { case (a, b) => a + b }.foreach { println }
```
```Data
(Messi,147)
(Ronaldo,145)
```  
```Figure_2```  
Unlike groupByKey , reduceByKey does not shuffle data at the beginning. As it knows the reduce operation can be applied in same partition first , only result of reduce function is shuffled over network. This cause significant reduction in traffic over network. Only catch is that the values for each key has to be of same datatype. If it is different datatypes it has to explicitly converted. This drawback can be addressed using combineByKey.
```des
combineByKey is most general per-key aggregation function. In fact other aggregate functions described above are derived from combineByKey. This makes combineByKey little bit tricky to comprehend.
```
Let us see how combineByKey works in our use case. When combineByKey navigates through each element i.e for partition 1 - (Messi,45) it has a key which it has not seen before and when it moves to next (Messi,48) it gets a key which it has seen before. When it first time see a element , combineByKey() use function called createCombiner to create an initial value for the accumulator on that key. i.e. it use Messi as the key and 45 as value. So current value of the accumulator of that key (Messi) becomes 45. Now next time combineByKey() sees same key on same partition it does not use createCombiner instead it will make use of second function mergeValue with current value of accumulator (45) and new value 48.


Since all this happens parallel in different partition, there is chance that same key exist on other partition with other set of accumulators. So when results from different partition has to be merged it use mergeCombiners function.


All three functions in combineByKey -createCombiner, mergeValue, mergeCombiners is user supplied, which give user flexibility to aggregate the result they need.To achieve same result, let us look at the code

```code
myPair.combineByKey((comb:Int)=>(comb), (a:Int, comb:Int)=>(a + comb), (a:Int,b:Int)=>(a+b) ).foreach(println)
```
This does not look good, so let us take one function at time. To further dissect and cross examine each functions, we will add a print statements in each sub functions to check when it is called.
```code
myPair.combineByKey(   
       (comb:Int)=>{
         println(s"""createCombiner is going to create first combiner for ${comb}""")
         (comb)}, (a:Int, comb:Int)=>{
            println(s"""mergeValue is going to merge ${a}+${comb} values in a single partition""")
           (a + comb)}, (a:Int,b:Int)=>{
             println(s"""mergeCombiner is going to merge ${a}+${b} combiners across partition""")
            (a+b)} ).foreach(println)
```
Result from console
```Data
createCombiner is going to create first combiner for 45
createCombiner is going to create first combiner for 52
mergeValue is going to merge 45+54 values in a single partition
mergeValue is going to merge 52+51 values in a single partition
createCombiner is going to create first combiner for 48
createCombiner is going to create first combiner for 42
mergeCombiner is going to merge 103+42 combiners across partition
mergeCombiner is going to merge 99+48 combiners across partition
(Messi,147)
(Ronaldo,145)
```
Let us further get into details of each steps.


createCombiner is going to create first combiner for 45 - because this is the first time combineByKey saw a new key i.e Messi. So it created a combiner with accumulator as 45 for that Key.

createCombiner is going to create first combiner for 52 - When it moved (Ronaldo,52) it saw a new key Ronaldo so again createCombiner was invoked with accoumlator for key as 52.

mergeValue is going to merge 45+54 values in a single partition - When it moved to (Messi,54) which is on same partition it invokes mergeValue function as the keys are already recognized.

mergeValue is going to merge 52+51 values in a single partition -Same as above. mergeValue function i.e adding up the values are invoked as Ronaldo is already a recognized key.

Now in a different partition createCombiner is again invoked for a different set of accumulators.
createCombiner is going to create first combiner for 48

createCombiner is going to create first combiner for 42

At the end the merge across the combiner happens using mergeCombiner function, this is when a shuffle happens for first time across network.
mergeCombiner is going to merge 103+42 combiners across partition
mergeCombiner is going to merge 99+48 combiners across partition

To summarize what each sub function inside combineByKey do
1) createCombiner, which turns a value into a combiner (e.g., creates a one-element list)
2) mergeValue, to merge a value into a accumulated values (e.g., adds it to the end of a list)
3) mergeCombiners, combine two or more combiners into a single one.


If groupByKey is derived from combineByKey why is it different in the way shuffle operation is done. The answer is purpose of groupByKey is to get a list of values before actual aggregation to achieve this map side aggregation is disabled for groupByKey since adding to a list does not save any space.
Check the final code. You can remove sparkcontext if the code is run from REPL
```code
def aggregateFunctions(): Unit = {
    val c SparkContext("local[8]", "Learn Aggregate Functions")
    val mydata = conf.textFile("src/main/resources/soccer.txt")
    //Converting data to a tuple, by splitting at delimiter. Score converted to a number explicitly
    val myPair = mydata.map { k => (k.split(" ")(0), k.split(" ")(1).toInt) }
    // Now let us try groupByKey to get sum of the goals in last 4 years for players
    myPair.groupByKey().foreach(println)
    myPair.groupByKey().mapValues { x => x.reduce((a, b) => a + b) }.foreach(println)
    println("Another method to do same thing.")
    myPair.groupByKey().map { x => (x._1, x._2.sum) }.foreach(println)
    println("reduceByKey")
    myPair.reduceByKey { case (a, b) => a + b }.foreach { println }
    println("combineByKey")
    myPair.combineByKey(
      (comb: Int) => {
        println(s"""createCombiner is going to create first combiner for ${comb}""")
        (comb)
      }, (a: Int, comb: Int) => {
        println(s"""mergeValue is going to merge ${a}+${comb} values in a single partition""")
        (a + comb)
      }, (a: Int, b: Int) => {
        println(s"""mergeCombiner is going to merge ${a}+${b} combiners across partition""")
        (a + b)
      }).foreach(println)
  }
```
WHY IS REDUCEBYKEY EFFICIENT THAT GROUPBYKEY?
```Des
i) reduceByKey works on pair as a per key function. That is rather than reducing entire RDD to a in-memory value, reduceByKey reduces the data per key and get back an RDD with reduced values corresponding to that key. This means the data getting moved over network or from one parition to another is minimal.
ii) groupByKey output is key and iterate-able value list. So this list of values might have obtained from different partition in different nodes. This cause a lot of shuffle and data exchange over network.To determine which machine to shuffle a pair to, Spark calls a partitioning function on the key of the pair. Spark spills data to disk when there is more data shuffled onto a single executor machine than can fit in memory. However, it flushes out the data to disk one key at a time - so if a single key has more key-value pairs than can fit in memory, an out of memory exception occurs. When Spark needs to spill to disk, performance is severely impacted.
iii) Often groupByKey function has to be accompanied with a reduce or fold to get the real result. Now this becomes a two step operation. First apply groupByKey and then reduce. Where as same logic can be achieved with a single reduceByKey call with the reduction function applied. As seen in our use case above.
```
groupBy is still useful when we need to apply certain functions to determine the key. i.e not just grouping by on key, we need to do some transformation to determine key. In our above example , assume we standardized names to upper case, a function say toUpper can be applied to transform the keys.

Hope this helps. Feel free to add more use cases where the aggregate functions can applied differently.
