
Environement/Software and dependency Requirements to run :
============================================

Java 8.x
Cassandra 3.11.x
Rest client
Spring boot
maven 3.x

dependency: swagger,springboot 1.5.x


Database Pre configurations:
===============================

CREATE  KEYSPACE IF NOT EXISTS  target
   WITH REPLICATION = {
      'class' : 'SimpleStrategy', 'replication_factor' : N };



create table if not exists target.product_pricing
(id  int PRIMARY KEY,
currency text,
price double
);

Application configurations:
===========================

Application PORT and cassandra details should be provided in application.properties and cassandra.properties respectively


Build using maven
===============

mvn clean install -DskipTests=true


RUNNING THE APPLICATION
========================
java -jar <jarpath/jarname>

eg. java -jar  /home/rvretailservice-0.0.1-SNAPSHOT.jar

Swagger URL
=================

http://localhost:9080/swagger-ui.html#/retail-rest-controller


Supported Operations:
============================
swagger url to know more details:
http://localhost:9080/swagger-ui.html#/retail-rest-controller

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

3. Put will have similar format as POST.

VALIDATIONS
============
Basic validation are provided for the CRUD operations mentioned above
