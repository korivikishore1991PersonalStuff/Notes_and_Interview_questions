# Integrating Hue with Navigator  
can be used for Data Search and Tagging via Hue.  Aplicable for Cloudera Enterprise 5.11 and greater.   
# DataBase and Table creation  
## Creation of New DataBase with technical metadata  
```hql
CREATE DATABASE financials
COMMENT 'Holds all financial tables'
WITH DBPROPERTIES ('creator' = 'Mark Moneybags', 'date' = '2012-01-02', 'ModifiedBy' = 'Ranga');
```
  
## Creation of New Table with technical metadata  
```hql
CREATE TABLE IF NOT EXISTS test_table
(col1 int COMMENT 'Integer Column',
col2 string COMMENT 'String Column'
)
COMMENT 'This is test table'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
TBLPROPERTIES ('Key0'='Hello', 'create_at'='2017-02-09 12:00');
```
  
# DataBase and Table Alteration  
## Alteration of Databases  
```hql
ALTER database table1 SET DBPROPERTIES ('comment' = 'your comments'); --Altering comments
ALTER DATABASE financials SET DBPROPERTIES ('edited-by' = 'Joe Dba'); --Altering key:value properties
```  
 ## Alteration of tables
 ```hql
ALTER TABLE test_table SET TBLPROPERTIES ('comment' = 'Hello Ranga jeee'); --Altering comments
ALTER TABLE table1 SET TBLPROPERTIES ('Key1' = 'Hello World!'); --Altering key:value properties
 ```
  
# References  
