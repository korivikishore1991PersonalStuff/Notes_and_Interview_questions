- [Integrating Hue with Navigator](#Integrating Hue with Navigator)
# DataBase and Table creation  
## Creation of New DataBase with technical metadata  
## Creation of New Table with technical metadata  
# DataBase and Table Alteration  
## Alteration of Databases  
## Alteration of tables  
# Cloumn creation and alteration  
## Altering a cloumn   
## Adding a cloumn  
## Removing a cloumn  
# Navigator based searching  
# Hue based searching  
# References  
  
The aim of this data tagging is ease the time required to bowse, gather and categorise related cloumns, tables and databases via predefined attributes like tags, key:value pairs and comments/description. 
<a name="Integrating Hue with Navigator"></a>
# Integrating Hue with Navigator  
Embedded Search & Tagging via metastore manager in Hue and cloudera navigator can be used for Data Search and Tagging via Hue and navigator.  Aplicable for Cloudera Enterprise 5.11 and greater.   
Configure Hue in cloudera manager tp search on Tagging.  
Figure#1  
Refresh the SQL editor to get a new search bar in the Hue. Which enables users to search on tags, tables, columns etc..  
Figure#2  
user tags can also be edited via Hue.  
Figure#3  
  
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
   
# Cloumn creation and alteration  
## Altering a cloumn  
```hql
ALTER TABLE $table_name CHANGE COLUMN $old_col_name $new_col_name INT COMMENT 'The hours, minutes, and seconds part of the timestamp' AFTER $col_name_before_present_col; --detailed way of changing the cloumn name and description
ALTER TABLE test_change CHANGE a1 a1 INT COMMENT 'this is column a1';
```  
## Adding a cloumn  
```hql
ALTER TABLE log_messages ADD COLUMNS ( app_name   STRING COMMENT 'Application name', session_id LONG   COMMENT 'The current session id');
```  
## Removing a cloumn  
```hql
ALTER TABLE log_messages REPLACE COLUMNS ( hours_mins_secs INT    COMMENT 'hour, minute, seconds from timestamp', severity        STRING COMMENT 'The message severity' message STRING COMMENT 'The rest of the message');
```  
# Navigator based searching  
# Hue based searching  
# References  
https://learning.oreilly.com/library/view/programming-hive/9781449326944/ch04.html  
https://blog.cloudera.com/blog/2017/05/new-in-cloudera-enterprise-5-11-hue-data-search-and-tagging/  

