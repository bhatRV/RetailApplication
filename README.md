
Environement/Software Requirements to run :
============================================

Java 8.x
Cassandra 3.11.x
Rest client
Spring boot
maven 3.x


Database Pre configurations:
===============================

Cassandra is used as the database;
  Cassandra needs to have the following tables pre created:

create table if not exists target.product_pricing
(id  int PRIMARY KEY,
currency text,
price double
);

Application configurations:
===========================

Application PORT and cassandra details should be provided in application.properties and cassandra.properties respectively

RUNNING THE APPLICATION
========================





Supported Operations:
============================

1. GET single Product by its Id
2. Create a product with price details
3. Update the price.


Assumptions:
=============

Product name is provided by external rest based interface


REQUEST FORMATS
==========================

1. URL for POST request :
 http://localhost:9080/product/<productId>


POST Json body format:

{
"id":"<productId>",
  "name":"RASHMI",
  "priceDetails":{
    	"price":"11",
    	"currency":"USD"
  				  }
 }


2. Get request URL:
 http://localhost:9080/product/<productId>

3. Put will have similar format as POST
