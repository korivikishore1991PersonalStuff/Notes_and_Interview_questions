Spark comes with a lot of easy to use aggregate functions out of the box. For the same reason spark becomes a powerful technology for ETL on BigData.

Grouping the data is a very common use case in the world of ETL. Just like aggregate transformation in ETL tools like Ab-initio or Informatica, where the results can be grouped and aggregate functions can be applied.

e.g. Group all customer order based on customer key, find the best sales year , find the worst player in baseball based on strike rate etc. etc.

Unlike standard ETL tools sparks comes with three transformations to achieve the same result but in different ways.

reduceByKey
groupByKey
combineByKey
These are three transformation available in spark which can be used interchangeably.
