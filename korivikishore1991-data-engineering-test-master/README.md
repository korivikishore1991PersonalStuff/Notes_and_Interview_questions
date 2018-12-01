# HelloFresh Data Engineering Test

Thank you for your interest in joining HelloFresh! As part of our selection process, all of our candidates must take the following test.
The test is designed to assess key competencies required in your role as a data engineer in our Business Intelligence Team.

Please submit your answers in a different branch and create a pull request. Please do not merge your own pull request.

# 1. SQL

[Cloudera Impala](http://impala.io/), one of the tools of the Hadoop Ecosystem that allow us to execute SQL queries on Hadoop, provides a range of powerful analytical SQL functions that come very handy when doing analysis on datasets. You can find more information on the analytical SQL functions offered by Impala [here](http://www.cloudera.com/content/www/en-us/documentation/archive/impala/2-x/2-1-x/topics/impala_analytic_functions.html)

Given the following sample table `ingredients` that records how individual records change over time: 
```
------------------------------------------------------------------
| id_ingredient | ingredient_name | price |  timestamp | deleted |
------------------------------------------------------------------
|        1      | potatoes        | 10.00 | 1445899655 |    0    |
|        2      | tomatoes        | 20.00 | 1445836421 |    0    |
|        1      | sweet potatoes  | 10.00 | 1445899132 |    0    |
|        1      | sweet potatoes  | 15.00 | 1445959231 |    0    |
|        2      | tomatoes        | 30.00 | 1445894337 |    1    |
|        3      | chicken         | 50.00 | 1445899655 |    0    |
							....
							....
|       999     | garlic          | 17.00 | 1445897351 |    0    |
------------------------------------------------------------------

```
Write an Impala SQL query that creates a view of that `ingredients` table, that shows only **the most recent state** of each ingredient that **has not been yet deleted**.

Save your answer to a `.sql` file in the root directory of this repository.

# 2. Python

### Requirements
1. Write well structured, documented, maintainable code.
2. Write unit tests to test the different components

## Ingredients Shopping
Your task is to create a simple shopping cart to buy ingredients for our delicious recipes!

Ingredients are stored in a `IngredientsStore` object. This can either be created from a list of ingredient tuples or initialized from a CSV file.

```python
from decimal import Decimal
from ingredient import IngredientsStore

ingredients = [
    ('tomatoes', Decimal('0.15')),
    ('chicken', Decimal('3.49')),
    ('onions', Decimal('2.00')),
    ('rice', Decimal('0.70')),
]
ingredient_store = IngredientsStore(ingredients)

# or

import os

csv_filepath = os.path.abspath('ingredients.csv')
ingredient_store = IngredientsStore.init_from_filepath(csv_filepath)
```

The price for an ingredient can be retrieved with `get_ingredient_price()`.

```python
price = ingredient_store.get_ingredient_price('chicken')
```

## Cart

Carts should be created with an IngredientsStore instance.

```python
from cart import Cart

shopping_cart = Cart(ingredient_store)
```

Ingredients can be added to a cart by name, with an optional quantity parameter.

```python
shopping_cart.add('tomatoes')
shopping_cart.add('onions', 3)
```

The total for the cart can be calculated with `get_total()`. This method optionally takes a list of [Discount](#discount) objects that are applied to items in the cart when calculating the total.

```python
total = shopping_cart.get_total()

# with discount
total_after_discount = shopping_cart.get_total(discounts=[discount_one, discount_two])
```

## Discount

Discount classes inherit from `AbstractDiscount` and must implement the `calculate_line_total()` method.

Two discount classes are provided; `NoDiscount`, `BulkDiscount`.

### NoDiscount

No discount is applied and the price remains unaffected.

```python
tomatoes_nodiscount = NoDiscount('tomatoes')
```

### BulkDiscount

A discount that applies when you buy a specific quantity. For example, buy one get one free, or, buy two get a third free.

```python
# buy one get one free on tomatoes
buy_one_get_one_free_tomatoes = BulkDiscount('tomatoes', 1, 1)

# buy two get third free on onions
buy_two_get_third_free_onions = BulkDiscount('onions', 2, 1)
```

# 3. Apache Spark

1. Download the following dataset of [Open Recipes](https://s3-eu-west-1.amazonaws.com/dwh-test-resources/recipes.json)
2. Write an Apache Spark application in **Python** that reads the recipes json, extracts every recipe that has **"beef"** as one of the ingredients.
3. Add an extra field to each of the extracted recipes with the name `difficulty`. The `difficulty` field would have a value of "Hard" if the total of `prepTime` and `cookTime` is greater than 1 hour, "Medium" if the total is between 30 minutes and 1 hour, "Easy" if the total is less than 30 minutes, and "Unknown" otherwise.
4. The resulting dataset should be saved as parquet file.
5. Your Spark application could run in Stand-alone mode or it could run on YARN.
6. Place your answer in a directory called "recipes-etl" in the root of this repository, with a README.md file that outlines the instructions to run your Spark Application.

### Requirements
1. Use Apache Spark version 1.6.1
2. Write well structured, documented, maintainable code.
3. Write unit tests to test the different components

Open Recipes archive courtesy of [Ed Finkler](https://github.com/fictivekin/openrecipes)

# 4. Data Modelling

ADIS lifts have a system to simulate different lift control mechanisms in their buildings. Let us assume a building has M lifts and N floors.

You are in charge of measuring the performance of different lift control mechanisms and so will need to design the data model to capture observed data and the measurements you would calculate on that data model. Assume a typical simulation run proceeds over a 24 hour period and you are allowed to observe as much as you like (when/where each lift is, how many passengers, where the passengers are, when/where they arrive/depart, etc – if in doubt assume you can observe it). From your detailed data model you will then need (with very simple calculations) to determine various performance measures, for example:

Average waiting time per passenger 

Average journey time per passenger 
… etc 

1. List the different stakeholders who would be interested in lift performance (a “stakeholder” is any person or group who have an interest in or may be affected by the lift performance).**


2. List other performance measures that it would be useful or important to measure – make sure these cover all of the stakeholders. (Hint: there are lots of these).**


3. What would a suitable data representation look like? Please design a series of tables (as would be suitable to put in a database or spreadsheet). Make sure that the data representation (with very simple arithmetic calculations) is adequate to calculate the above measures, and any other measures that you deem important (and that those calculations are fairly easy and unambiguous).**


4. For “Average waiting time per passenger” and at least 2 other performance measures, describe how they can be easily calculated from your data model. Preferably write the SQL code you would use to calculate the waiting and journey times.**


5. Describe a simple but sensible algorithm or set of rules which could run a lift control mechanism. In what ways would this simple lift control mechanism work well, and in what ways might it not work well in the real world? What other complications might be important to turn this into a real-world, operational lift controller?**


Please answer these questions either by editing this file, or by adding an additional document to this repository that contains the answers to these questions.
