# Table of contents  
- [Integrating_Hue_with_Navigator](#Integrating_Hue_with_Navigator)  
- [DataBase_and_Table_creation](#DataBase_and_Table_creation)    
  * [Creation_of_New_DataBase_with_technical_metadata](#Creation_of_New_DataBase_with_technical_metadata)    
  * [Creation_of_New_Table_with_technical_metadata](#Creation_of_New_Table_with_technical_metadata)    
- [DataBase_and_Table_Alteration](#DataBase_and_Table_Alteration)    
  * [Alteration_of_Databases](#Alteration_of_Databases)    
  * [Alteration_of_tables](#Alteration_of_tables)    
- [Cloumn_creation_and_alteration](#Cloumn_creation_and_alteration)    
  * [Altering_a_cloumn](#Altering_a_cloumn)    
  * [Adding_a_cloumn](#Adding_a_cloumn)    
  * [Removing_a_cloumn](#Removing_a_cloumn)    
- [Navigator_based_searching](#Navigator_based_searching)  
   * [Searching_in_navigator](#Searching_in_navigator)
   * [Defineing_and_searching_Managed_Metadata](#Defineing_and_searching_Managed_Metadata)  
   * [Defining_and_searching_Technical_Metadata](#Defining_and_searching_Technical_Metadata)
- [Hue_based_searching](#Hue_based_searching)    
- [References](#References)    
  
The aim of this data tagging is ease the time required to bowse, gather and categorise related cloumns, tables and databases via predefined attributes like tags, key:value pairs and comments/description. 
# Integrating_Hue_with_Navigator  
Embedded Search & Tagging via metastore manager in Hue and cloudera navigator can be used for Data Search and Tagging via Hue and navigator.  Aplicable for Cloudera Enterprise 5.11 and greater.   
Configure Hue in cloudera manager tp search on Tagging.  
Figure#1  
Refresh the SQL editor to get a new search bar in the Hue. Which enables users to search on tags, tables, columns etc..  
Figure#2  
user tags can also be edited via Hue.  
Figure#3  
  
# DataBase_and_Table_creation
## Creation_of_New_DataBase_with_technical_metadata
```hql
CREATE DATABASE financials
COMMENT 'Holds all financial tables'
WITH DBPROPERTIES ('creator' = 'Mark Moneybags', 'date' = '2012-01-02', 'ModifiedBy' = 'Ranga');
```
  
## Creation_of_New_Table_with_technical_metadata
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
  
# DataBase_and_Table_Alteration
## Alteration_of_Databases
```hql
ALTER database table1 SET DBPROPERTIES ('comment' = 'your comments'); --Altering comments
ALTER DATABASE financials SET DBPROPERTIES ('edited-by' = 'Joe Dba'); --Altering key:value properties
```  
 ## Alteration_of_tables
 ```hql
ALTER TABLE test_table SET TBLPROPERTIES ('comment' = 'Hello Ranga jeee'); --Altering comments
ALTER TABLE table1 SET TBLPROPERTIES ('Key1' = 'Hello World!'); --Altering key:value properties
 ```  
   
# Cloumn_creation_and_alteration
## Adding_a_cloumn
```hql
ALTER TABLE $table_name CHANGE COLUMN $old_col_name $new_col_name INT COMMENT 'The hours, minutes, and seconds part of the timestamp' AFTER $col_name_before_present_col; --detailed way of changing the cloumn name and description
ALTER TABLE test_change CHANGE a1 a1 INT COMMENT 'this is column a1';
```  
## Removing_a_cloumn
```hql
ALTER TABLE log_messages ADD COLUMNS ( app_name   STRING COMMENT 'Application name', session_id LONG   COMMENT 'The current session id');
```  
## Removing a cloumn  
```hql
ALTER TABLE log_messages REPLACE COLUMNS ( hours_mins_secs INT    COMMENT 'hour, minute, seconds from timestamp', severity        STRING COMMENT 'The message severity' message STRING COMMENT 'The rest of the message');
```  
  
# Navigator_based_searching  
Navigator is available on Port 7187 via navigator metadata server, the navigator can be used to audit, track and visulize data flow on the meta data using filtering via solar and linege diagram.  
## Searching_in_navigator  
In Cloudera Navigator, metadata search is implemented by an embedded Solr engine that supports the syntax given by LuceneQParserPlugin. Navigator provides searching capabilities by components, storage, type(table, feilds), tags and size etc..  
Example:  
Union type searching ```lucene  (*ers_stage_tls*) +(sourceType:hdfs sourceType:hive) +(type:table)```, to search for source types of HDFS and Hive or for table type or with string ```ers_stage_tls``` in the path.  
Intersection type searching ```lucene +((+*ers_stage_tls* +type:table))```, to search for table type with string ```ers_stage_tls``` in the path.  
boolean type searching ```lucene +((+*ers_stage_tls* +type:table)) +(-deleted:true)```, to search for table type with string ```ers_stage_tls``` in the path, which are not deleted.  
  
## Defineing_and_searching_Managed_Metadata  
Navigator provides capabilities to adding and Editing Metadata(tags, cloumns, description at all column, tables and database level) Using the Navigator UI.  
Example:  
Tags and description can be searched at main filtering level. key:value can be searched using "User defined values".  
Figure#4  
1. Run a search in the Navigator UI.  
2. Click an entity link returned in the search. The Details tab displays.  
3. To the left of the Details tab, click Actions > Edit Metadata.... The Edit Metadata dialog box drops down.  
4. Add metadata fields:
  * In the Name field, type a new display name.  
  * In the Description field, type a description.  
  * In the Tags field, type a tag and press Enter or Tab to create new tag entries.  
  * Managed Metadata  
    * Click the  and select a property.  
    * Click the value field after the : to display type-specific selection controls such as integer spinners and date selection controls. Either type the value or use the controls to select a value.  
    Click + to add another managed property key-value pair or another value for a given key.  
   * Key-Value Pairs  
     * Click + to add a key-value pair.
     * Type a key and a value. You can specify special characters (for example, ".", " ") in the name, but it makes searching for the entity more difficult because some characters collide with special characters in the search syntax.    
5. Click Save. The new metadata appears in the Managed Metadata or Custom Metadata pane.        
   
## Defining_and_searching_Technical_Metadata  
technical metadata in hive can be specified using table propetrties.  
```hql
ALTER TABLE table_name SET TBLPROPERTIES ('key1'='value1');
```    
to search for this property, we must specify ```hql tp_key1:value1```  
This technical metadata is extended attributes, which are added by Hive clients.  
 
A sample after clear definition of techinical and managed metadata is given below, the below Hive table has custom metadata consisting of tags tag1 and tag2, a custom key-value pair customkey-value, and an extended Hive attribute key-value pair key1-value1. The Details page also displays the table schema.  
Figure#5  
  
# Hue_based_searching  
As stated earlier once hue is integrated we can edit, define and modified the technical metadata of Hive entities.  
By default, only tables and views are returned. To search for columns, partitions, databases use the ‘type:’ filter.  
Example of searches:  
table:customer → Find the customer table  
table:tax* tags:finance → List all the tables starting with tax and tagged with ‘finance’  
owner:admin type:field usage → List all the fields created by the admin user that matches the usage string  
Figure#6  
Adding tags in Hue.  
Figure#7  
  
# References  
https://learning.oreilly.com/library/view/programming-hive/9781449326944/ch04.html  
https://blog.cloudera.com/blog/2017/05/new-in-cloudera-enterprise-5-11-hue-data-search-and-tagging/  
https://www.cloudera.com/documentation/enterprise/5-13-x/PDF/cloudera-datamgmt.pdf  
