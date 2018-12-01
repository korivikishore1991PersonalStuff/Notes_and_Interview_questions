/*
Assumptions:
1)Every cloumn is a string
2)For deleted column 0 means not deleted and 1 means deleted
3)from_unixtime() function works on your impala version
4)timestamp is key word in Impala. So, timestamps is used as identifier instead
*/

-- Raw table creation
-- CREATE TABLE ingredients (id_ingredient STRING, ingredient_name STRING, price STRING, timestamps STRING, deleted STRING);


--value insertion
-- INSERT INTO ingredients values ('1', 'potatoes', '10.00', '1445899655', '0'), ('2', 'tomatoes', '20.00', '1445836421', '0'), ('1', 'sweet potatoes', '10.00', '1445899132', '0'), ('1', 'sweet potatoes', '15.00', '1445959231', '0'), ('2', 'tomatoes', '30.00', '1445894337', '1'), ('3', 'chicken', '50.00', '1445899655', '0');


-- view creation and aggregation
create view
ingredients_agg
as
select
ingredients_casted.id_ingredient as id_ingredient,
ingredients_casted.ingredient_name as ingredient_name, 
ingredients_casted.price as price, 
from_unixtime(max(ingredients_casted.timestamps), "yyyy-MM-dd HH:mm:ss.SSSS") as Most_Recent_Date, 
ingredients_casted.deleted as is_deleted
from
(select
cast(id_ingredient as BIGINT) id_ingredient,
ingredient_name,
cast(price as float) price,
cast(timestamps as BIGINT) timestamps,
cast(deleted as INT) deleted
from
ingredients
where
deleted = '0') 
as 
ingredients_casted
group by
ingredients_casted.id_ingredient,
ingredients_casted.ingredient_name, 
ingredients_casted.price, 
ingredients_casted.deleted;