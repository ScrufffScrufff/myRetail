   1.myRetail RESTful servicemyRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.Build an application that performs the following actions: Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793) Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail) Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statisticsReads pricing information from a NoSQL data store and combinesit with the product id and name from the HTTP request into a single response. BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store

Technology used:
 * Java 11
 * Springboot
 * MongoDB
 * JUNIT
 * Mockito
 * Test me

To run locally (Windows):
    
    1. Download mongodb from https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/
    2. Follow the install instructions, keeping everything default.
    3. Find and run Mongo.
        Note: Follow mongo instructions for alternate OS installation.
    4. Clone this git repo, open in IDE of choice, ex. Intellij.
    5. build and run MyRetailProductApplication
    6. Download and install Postman from https://www.getpostman.com/
    7. Find and run Postman (http://localhost:8088/products/{$id}).
    8. For testing purposes download and install NOSQLBooset for MongoDB and connect to localhost 27017

Sample Get:
        http://localhost:8088/products/13860428  

Sample Post:
        http://localhost:8088/products/13860428  
        {
        "value": 12.00,
         "currencyCode": "AUD"
        }
        
Tests to run: 
Get:
1. Product and price exist                 http://localhost:8088/products/13860428 
    * Expected result: Product/price is returned. 
2. Product does not exist but price does    http://localhost:8088/products/15117729
    * Expected result: Product does not exist in external API.
3. Product exists but price does not       http://localhost:8088/products/13860429
    * Expected result: Product not found in price DB.
4. Neither product nor price exists        http://localhost:8088/products/16696652
    * Expected result: Product does not exist.
    
Post:
1. Product and price exist                 http://localhost:8088/products/13860428 
    * Expected result: Price is updated 
2. Product does not exist but price does    http://localhost:8088/products/15117729
    * Expected result: Price is updated
3. Product exists but price does not       http://localhost:8088/products/13860429
    * Expected result: Product not found in price DB
4. Neither product nor price exists        http://localhost:8088/products/16696652
    * Expected result: Product does not exist



    
