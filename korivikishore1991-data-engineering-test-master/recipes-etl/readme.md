# Submission:  
## Local:  
spark-submit --master local recipes.py /user/arc/ranga_korivi/recipes.json /user/arc/ranga_korivi/recipes_Modified_parquet /user/arc/ranga_korivi/recipes_Modified_text  
## Yarn:  
spark-submit --master yarn recipes.py /user/arc/ranga_korivi/recipes.json /user/arc/ranga_korivi/recipes_Modified_parquet /user/arc/ranga_korivi/recipes_Modified_text  
  
# Transferring files from Local to HDFS:  
hadoop fs -copyFromLocal ~/ranga_korivi/recipes.json ranga_korivi/  
  
# Checking the files in HDFS and execution mode changing:  
hadoop fs -ls /user/arc/ranga_korivi  
  
chmod 777 recipes.py  
  
# Input file path in HDFS:  
/user/arc/ranga_korivi/recipes.json  
  
# Output file Paths in HDFS:  
## Parquet file:(automatic deletion because of Overwrite mode)  
/user/arc/ranga_korivi/recipes_Modified_parquet  
## text file:(need to Delete this for every rerun operation)  
/user/arc/ranga_korivi/recipes_Modified_text  
   
# testing the text output code:  
hadoop fs -cat /user/arc/ranga_korivi/recipes_Modified_text/part-00000| wc -l  

hadoop fs -cat /user/arc/ranga_korivi/recipes_Modified_text/part-00000| more
