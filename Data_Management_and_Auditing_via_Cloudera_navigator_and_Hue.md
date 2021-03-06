# Table of contents  
- [Introduction](#Introduction)  
  * [Auditing_Component](#Auditing_Component)
  * [Metadata_Component](#Metadata_Component)
- [Expolring_Auditing_Component_API](#Expolring_Auditing_Component_API)  
- [Expolring_Metadata_Component_API](#Expolring_Metadata_Component_API)  
  * [Metadata_Component_Operations](#Metadata_Component_Operations)
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
- [Items_needed_to_be_covered](#Items_needed_to_be_covered)
  
# Introduction
The aim of this data tagging is ease the time required to bowse, gather and categorise related cloumns, tables and databases via predefined attributes like tags, key:value pairs and comments/description. Navigator provides a singel front end view for managing, adding meta data, governing access, visualize data lineage and auditing data. Navigator automatically tracks lineage of cluster entities such as:  
- HDFS files and directories  
- Hive tables and columns  
- Mapreduce and YARN jobs  
- Hive queries (**USERS NEED TO BE USING BEELINE OR HUE INORDER FOR DATA CAPTURE VIA NAVIGATOR**)  
- Pig scripts  
- Oozie workflows  
- Spark jobs  
- Sqoop jobs  
  
Navigator is made up of two components:  
- Auditing Component(available @ port 7186)  
- Metadata Component(available @ port 7187)  
  
## Auditing_Component    
  
Figure#8 (for Auditing architecture)  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1FE5bxZgzAbSBSyJTzxptFPNGWucibG5A" width="650"/>
</p>   
  
HDFS operations captured for audit purposes are operations that access or modify the data or metadata of a file or a directory, operations denied due to lack of privileges.  
Hive Operations captured for audit purpose will be any operations sent by HiveServer2, except for those captured by Sentry auditing like GRANT, REVOKE and ACCESS TO METADATA ONLY. naigator will also log operations denied due to lack of privileges. **USERS NEED TO BE USING BEELINE OR HUE INORDER FOR DATA CAPTURE VIA NAVIGATOR**. Action taken against hive via the Hive CLI(HiveServer1) are not audited.  
All Hue operations are captured for audit purpose, except for those captured by Sentry auditing.  
Impala operations were captured for audit purpose and queries denied due to lack of privileges.  
  
## Metadata_Component  
  
Figure#10 (Navigator Metadata Component Architecture)    
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1b0ImlGghTQTGNrWfnvqL9UMua2jeDi_G" width="650"/>
</p>   
  
The Metadata component extracts metadata from cluster services including HDFS, Hive, Impala, YARN, MapReduce, Oozie, Pig and Sqoop 1. Spark support had limitaions like lineage is produced only for data that is read/written and processed using the Dataframe and SparkSQL APIs. Lineage is not available for data that is read/written or processed using Spark's RDD APIs. The spark extractor included prior to CDH 5.11 and enabled by setting the safety valve, ```nav.spark.extraction.enable=true``` is being deprecated, and could be removed completely in a future release. If upgrading from CDH 5.10 or earlier and were using the extractor configured with this safety valve, be sure to remove the setting when you upgrade.  
HDFS metadata is extracted when the service starts up and also soon after each HDFS checkpoint, if HDFS high availability is configured, which is typical in production cluster, then the metadata is extracted from the journal nodes. So, metadata should be available pretty much instantaneously.  
For Hive and Sqoop, the metadata is extracted from Hive's metastore server. So, that's the database which stores Hive's metadata.  
For YARN jobs metadata is extracted from the Job History Server, map reduce is extracted from Job Tracker, Oozie workflows are extracted from the oozie Server and Pig script runs are extracted from the jobTracker(MapReduce 1) or Job History Server(YARN).  
    
# Expolring_Auditing_Component_API  
audit data can be accessed through the web interface or via the REST API.  
As an example of the REST API, issue this curl CMD to obtain all audit events where the 'Command' field is equal to 'GlobalHostInstall' ``` curl -u admin:admin 'http://cmhost:7180/api/v6/audits?query=command==GlobalHostInstall' ```  
As example of Audit via port 7187 on Navigator Server.  
Figure#9a and Figure#9b (Audit checking via Navigator)  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=14ALCT-Axm9EeScb_55bnIwhL8Y0awOKK" width="650"/>
</p>  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1K9x6Mzmatn-3q7AnaSVFA9jifATW1QHx" width="650"/>
</p>  
    
# Expolring_Metadata_Component_API  
Navigator provides a web interface for Searching all meta data, Setting business metadata and Viewing relationships between entities in the form of Lineage Diagrams. Navigator's Metadata Component collects two different kinds of metadata from entities(iles, tables, directories etc..) in the cluster:  
1. technical metadata:  
technical metadata is the metadata which is automatically generated as the entity is created and throughout its life cycle. For instance, the date that entity was created, entity name, the date that entity was moved, the date that entity was destroyed, who owns the entity, when entity was last modified etc...  Technical metadata cann't be modified by an end user.  
2. business metadata:  
Business metadata is assigned to entities, such as tables or table columns, to add business meaning and context. Business metadata is added by end users via Navigator. Business metadata provides increased business value because users can:  
  i) Assign customized and meaningful annotations to make data exploration more intuitive for business users.  
  ii) Tag cluster entities to aid with data lifecycle management.  
  
The Navigator Metadata compoenent extracts metadata at startup and maintains a fresh view through periodic extractions. The extracted metadata is indexed and used by the embedded Solr search engine. Solr provides support for the metadata discovery and exploration features that you access from the Metadata web interface.  
  
## Metadata_Component_Operations  
Extracted metadata is indexed in order to facilitate fast searches. The Solr schema indexes two types of metadata, Entity properties and relationships between entities.  
Both technical and business metadata can be directly searched using the cloudera navigator metadata web interface.  
  
Figure#11(Metadata Querying via Navigator)  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1_JUwtAyCsCtepGaWwyOaHt7GgaunOR5G" width="650"/>
</p>  
  
# Integrating_Hue_with_Navigator  
Embedded Search & Tagging via metastore manager in Hue and cloudera navigator can be used for Data Search and Tagging via Hue and navigator.  Aplicable for Cloudera Enterprise 5.11 and greater.   
Configure Hue in cloudera manager tp search on Tagging. 
  
Figure#1  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1CltIc8lCXWGVB5CZDwxyfJ8Cz5Nkslfy" width="650"/>
</p>  
Refresh the SQL editor to get a new search bar in the Hue. Which enables users to search on tags, tables, columns etc..  
  
Figure#2  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1o96ZpdRTHtb4PJ-zaFGkvVDsEG-yV8XF" width="650"/>
</p>  
  
user tags can also be edited via Hue. 
  
Figure#3  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1qaUgAn-CNwOmmEeiZ6wJEVJBpLOTtY_k" width="650"/>
</p>  
  
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
Union type searching ```(*ers_stage_tls*) +(sourceType:hdfs sourceType:hive) +(type:table)```, to search for source types of HDFS and Hive or for table type or with string ```ers_stage_tls``` in the path.  
Intersection type searching ```+((+*ers_stage_tls* +type:table))```, to search for table type with string ```ers_stage_tls``` in the path.  
boolean type searching ```+((+*ers_stage_tls* +type:table)) +(-deleted:true)```, to search for table type with string ```ers_stage_tls``` in the path, which are not deleted.  
  
## Defineing_and_searching_Managed_Metadata  
Navigator provides capabilities to adding and Editing Metadata(tags, cloumns, description at all column, tables and database level) Using the Navigator UI.  
Example:  
Tags and description can be searched at main filtering level. key:value can be searched using "User defined values".  
  
Figure#4    
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1jde3nPiJbbA4z3P_BaWZ9_EN6HFYyNaq" width="650"/>
</p>  
  
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
to search for this property, we must specify ```tp_key1:value1```  
This technical metadata is extended attributes, which are added by Hive clients.  
 
A sample after clear definition of techinical and managed metadata is given below, the below Hive table has custom metadata consisting of tags tag1 and tag2, a custom key-value pair customkey-value, and an extended Hive attribute key-value pair key1-value1. The Details page also displays the table schema.  
  
Figure#5  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1g0lp2dCJKBbe0I4Y2L3BYMY3iV_tFYoE" width="650"/>
</p>  
  
# Hue_based_searching  
As stated earlier once hue is integrated we can edit, define and modified the technical metadata of Hive entities.  
By default, only tables and views are returned. To search for columns, partitions, databases use the ‘type:’ filter.  
Example of searches:  
table:customer → Find the customer table  
table:tax* tags:finance → List all the tables starting with tax and tagged with ‘finance’  
owner:admin type:field usage → List all the fields created by the admin user that matches the usage string  
  
Figure#6  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1isUizOaP-nJfrErjhmjza0cFE37B14J9" width="650"/>
</p>  
  
Adding tags in Hue.  
  
Figure#7  
<p align="center">
  <img src="https://drive.google.com/uc?export=view&id=1O0US1aKBllKNuiTi7nAjHbzodrRZKprh" width="650"/>
</p>  
  
# References  
https://learning.oreilly.com/library/view/programming-hive/9781449326944/ch04.html  
https://blog.cloudera.com/blog/2017/05/new-in-cloudera-enterprise-5-11-hue-data-search-and-tagging/  
https://www.cloudera.com/documentation/enterprise/5-13-x/PDF/cloudera-datamgmt.pdf  
  
# Items_needed_to_be_covered  
tagging at database, table and cloumn level via Hive.  
key:value pairs at cloumn level via Hive.  
