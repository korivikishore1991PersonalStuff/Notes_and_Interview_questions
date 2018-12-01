## Fast Summary Statistics for DataFrame:  
val summary = DataFrame.describe()  {Results in additional column called Summary that involves count, mean, stddev, min and max}  
  
## Pivoting and Reshaping DataFrame:  
### Reshaping of the DataFrame using FlatMap:  
``` scala
def longForm(desc: DataFrame): DataFrame = {  
    import desc.sparkSession.implicits._ // For toDF RDD -> DataFrame conversion  
    val schema = desc.schema  //getting the schema of the DataFrame  
    desc.flatMap(row => {  
      val metric = row.getString(0)  
      (1 until row.size).map(i => (metric, schema(i).name, row.getString(i).toDouble))  
    })  
    .toDF("metric", "field", "value")  //One to many Mapping  
  }  
```
### Pivoting of a table:  
used to transform row structure to columnar structure.  
Ex:  
Raw Data:  
```Scala
+------+---+-------+---------------+  
|userid|age|country|number_of_calls|  
+------+---+-------+---------------+  
|   x01| 41|     us|              3|  
|   x01| 41|     uk|              1|  
|   x02| 72|     us|              4|  
|   x02| 72|     uk|              6|  
+------+---+-------+---------------+  
```
Final Data:  
```Scala
+------+---+---+---+  
|userid|age| us| uk|  
+------+---+---+---+  
|   x01| 41|  3|  1|  
|   x02| 72|  4|  6|  
+------+---+---+---+  
```
#### Pivoting of a table using RDD:  
``` scala
import org.apache.spark.{SparkConf, SparkContext}  
object pivot extends App{

  val sc = new SparkContext(new SparkConf().setAppName("pivot").setMaster("local[*]"))

  val data = List(("x01", 41, "us", 3), ("x01", 41, "uk", 1),
    ("x02", 72, "us", 4), ("x02", 72, "uk", 6))

  val datardd = sc.parallelize(data)

  val countries = List("uk", "us")

  val brc = sc.broadcast(countries)

  val datarddF = datardd.filter(line => brc.value.contains(line._3))


  def reshape(line: (String, Int, String, Int)):((String, Int),
    (Int, Int))={
    val list = List.newBuilder[Any]  
  
    list.+=(line._1)  
  
    list.+=(line._2)  
  
    for (v <- brc.value){  
      if (line._3 == v){  
        list.+=(line._4)  
      }  
      else{  
        list.+=(0)  
      }  
    }  
  
    val listF = list.result()  
    return ((listF(0).toString,listF(1).toString.toInt),  
      (listF(2).toString.toInt,listF(3).toString.toInt))  
  }  
  
  val datarddFM = datarddF.map(reshape(_))  
  
  def addtup(t1:(Int, Int), t2: (Int, Int))={  
    (t1._1+t2._1, t1._2+t2._2)  
  }  
  
  val datarddFMR = datarddFM.reduceByKey(addtup)  
  
  datarddFMR.foreach(println)  
  
}  
```
#### Pivoting of a table using DataFrame:  
```Scala
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.first
import org.apache.spark.{SparkConf, SparkContext}

case class df_tabel(userid:String, age:Int, country:String, number_of_calls:Int)

object pivot_DF extends App{

  val sc = new SparkContext(new SparkConf().setAppName("pivot").setMaster("local[*]"))

  val sqlcontext = new SQLContext(sc)

  val data = List(("x01", 41, "us", 3), ("x01", 41, "uk", 1),
    ("x02", 72, "us", 4), ("x02", 72, "uk", 6))

  val datardd = sc.parallelize(data)

  import sqlcontext.implicits._

  val dataDF = datardd.map(line => df_tabel(line._1, line._2, line._3, line._4)).toDF()

  dataDF.show()
  dataDF.groupBy("userid", "age").pivot("country", Seq("us", "uk"))
    .agg(first("number_of_calls")).show()

}
```
#### Syntax:  
```Scala
val resultDF = rawDF.groupBy("unchanging cloumns").  
               pivot("from_cloumn", Seq("to_cloumns","_in_Strings")).  
               agg(first("column to be aggregated"))  
```
