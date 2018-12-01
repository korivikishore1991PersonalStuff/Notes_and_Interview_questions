
# there is an error in the raw data when applying show() function on the DataFrame. It is as mentioned below:
# when non-UnicodeEncodeError: 'charmap' codec can't encode character u'\u2019' in position 3272: character maps to <undefined>
# So, only way to test will be saving it as textfile and checking the 47 rows of results

# Assumptions:
# None

import re
import sys

from pyspark.conf import SparkConf
from pyspark.context import SparkContext
from pyspark.rdd import RDD
from pyspark.sql import SQLContext, Row
from pyspark.sql import HiveContext
from pyspark.sql.functions import unix_timestamp
from pyspark.sql.types import *


# test with master as local and yarn
conf = SparkConf().setAppName("MFG_Reports_project")
sc = SparkContext(conf=conf)
hiveCtx = HiveContext(sc)
sqlContext = SQLContext(sc)

def difficulty_set(line):
    difficulty_count = -1
    difficulty_variables = [line[0], line[6]]
    for variable in difficulty_variables:
        if re.search('^\D{1,2}\d{1,2}H$', variable):
            try:
                value1 = re.findall('^\D+([0-9]+)H$', variable)[0]
            except Exception, error:
                value1 = 0
            difficulty_count = difficulty_count+(int(value1)*60)
        elif re.search('^\D{1,2}\d{1,2}M$', variable):
            try:
                value2 = re.findall('^\D+([0-9]+)M$', variable)[0]
            except Exception, error:
                value2 = 0
            difficulty_count = difficulty_count + int(value2)
        elif re.search('^\D{1,2}\d{1,2}H\d{1,2}M$', variable):
            try:
                value3 = re.findall('^\D{1,2}([0-9]+)H\d{1,2}M$')[0]
                value4 = re.findall('^\D{1,2}\d{1,2}H([0-9]+)M$')[0]
            except Exception, error:
                value3 = 0
                value4 = 0
            difficulty_count = difficulty_count+((int(value3)*60)+int(value4))
        elif re.search('^\D{1,2}\d{1,2}M\d{1,2}H$', variable):
            try:
                value5 = re.findall('^\D{1,2}([0-9]+)M\d{1,2}M$')[0]
                value6 = re.findall('^\D{1,2}\d{1,2}M([0-9]+)H$')[0]
            except Exception, error:
                value5 = 0
                value6 = 0
            difficulty_count = difficulty_count+(int(value5)+(int(value6)*60))
    if difficulty_count > 60:
        line[9] = "Hard"
    elif (difficulty_count <= 60 and difficulty_count >= 30):
        line[9] = "Medium"
    elif difficulty_count < 30:
        line[9] = "easy"
    else:
        line[9] = "unknown"
    return line


# test it with file path specified as argument
# recipesDF = sqlContext.read.json("C:\\Users\\korivi\\Desktop\\HelloFresh_assig\\recipes.json")
recipesDF = sqlContext.read.json(sys.argv[1])

filteredDF = recipesDF.filter("ingredients LIKE '%beef%' OR ingredients LIKE '%Beef%'")

filteredDFrdd = filteredDF.rdd.map(lambda row:[row.cookTime,row.datePublished, row.description,
                                     row.image, row.ingredients, row.name, row.prepTime,
                                     row.recipeYield, row.url, "unknown"])

filteredDFrdd_Modified = filteredDFrdd.map(lambda line: difficulty_set(line))

recipesDF_Modified = sqlContext.createDataFrame(filteredDFrdd_Modified
                                                .map(lambda list:
                                                     Row(cookTime=list[0],
                                                         datePublished=list[1],
                                                         description=list[2],
                                                         image=list[3],
                                                         ingredients=list[4],
                                                         name=list[5],
                                                         prepTime=list[6],
                                                         recipeYield=list[7],
                                                         url=list[8],
                                                         difficulty=list[9])))

# testing of the result

# recipesDF_Modified.printSchema()
# recipesDF_Modified.take(2)
# recipesDF_Modified.show(recipesDF_Modified.count()) # won't work because of u'\u2019' in position 3272 in raw file

# test it with file path specified as argument
# save as parquet file
recipesDF_Modified.write.mode('Overwrite').parquet(sys.argv[2])
# save as textfile
recipesDF_Modified.rdd.map(lambda row:[row.cookTime,row.datePublished, row.description,
                                       row.difficulty, row.image, row.ingredients,
                                       row.name, row.prepTime, row.recipeYield,
                                       row.url]).coalesce(1).saveAsTextFile(sys.argv[3])
