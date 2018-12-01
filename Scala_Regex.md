# Scala  
  
## Libraries  
import scala.util.matching.Regex
  
## Matching  
```scala
"ab12".matches("[a-zA-Z0-9]{4}")  
```
  
## Splitting  
```scala
"hello world, this is Al".split("\\s+")  
```  
## Replacement  
```scala
"milk, tea, muck" replaceAll ("m[^ ]+k", "coffee")  
"milk, tea, muck" replaceFirst ("m[^ ]+k", "coffee")  
```  
## Grouping & Extracting  
```scala  
  val url = "http://api.openweathermap.org/data/2.5/forecast?mode=xml&lat=55&lon=0"  
  val l: List[String] = List(  
    """<?xml version="1.0" encoding="utf-8"?>""",  
    """<weatherdata>""", "  <location>",  
    "    <name>Whitby</name>",  
    "    <type/>",  
    "    <country>GB</country>",  
    "    <timezone/>",  
    "    <name>Whitby1</name>",  
    "    <name>Whitby2</name>",  
    """    <location altitude="0" latitude="54.48774" longitude="-0.61498" geobase="geonames" geobaseid="0"/>""",  
    "  </location>", "  <credit/>")  
  def getChild(tag:String, pattern: Regex)={  
    val match_string = (l.map(line => line.trim)  
      .filter(line => line.contains("<%s>".format(tag)))).mkString("")  
    println(match_string)  
    var regex_list: List[String] = List();  
    pattern.findAllIn(match_string).matchData.foreach{  
      m => {  
        println(m.group(1))  
        regex_list = regex_list:::m.subgroups  
      }  
    }  
    regex_list  
  }  
  val cityName = getChild("name", ".*>(\\w+)?</.*>(\\w+)?</.*>(\\w+)?</.*".r)  
  println(cityName.mkString(" "))  
  val countryName = getChild("country", ".*>(\\w+)?</.*".r)  
  println(countryName.mkString(" "))  
```
(ALternatives)  
```scala
val numPattern = "[0-9]+".r  
val address = "123 Main Street Suite 101"  
val match1 = numPattern.findFirstIn(address)  
val matches = numPattern.findAllIn(address)  
matches.foreach(println)  
val matches = numPattern.findAllIn(address).toArray  
val result = numPattern.findFirstIn(address).getOrElse("no match")  
```
(OR)  
```scala
scala> val s = "Frank,123 Main,925-555-1943,95122"  
s: String = Frank,123 Main,925-555-1943,95122  
  
scala> val p = """.*,(\d{3})-(\d{3})-(\d{4}),.*""".r  
p: scala.util.matching.Regex = .*,(\d{3})-(\d{3})-(\d{4}),.*  
  
scala> val p(p1,p2,p3) = s  
p1: String = 925  
p2: String = 555  
p3: String = 1943    
```
(OR)  
```scala
val string = "one493two483three"
val pattern = """two(\d+)three""".r
pattern.findAllIn(string).matchData foreach {
   m => println(m.group(1))
}
```

## Group Extraction  
```scala
scala> val pattern = "([0-9]+) ([A-Za-z]+)".r
pattern: scala.util.matching.Regex = ([0-9]+) ([A-Za-z]+)

scala> val pattern(count, fruit) = "100 Bananas"
count: String = 100
fruit: String = Bananas
```
  
## Group Extraction error handling  
```scala
// match "movies 80301"
val MoviesZipRE = "movies (\\d{5})".r

// match "movies near boulder, co"
val MoviesNearCityStateRE = "movies near ([a-z]+), ([a-z]{2})".r

"movies near boulder, co" match {
    case MoviesZipRE(zip) => getSearchResults(zip)
    case MoviesNearCityStateRE(city, state) => getSearchResults(city, state)
    case _ => println("did not match a regex")
}
(OR)
"movies near boulder, co" match {
  case MoviesZipRE(zip) => println(zip)
  case MoviesNearCityStateRE(city, state) => println(city)
  case _=>println("No match bro")
}
```
