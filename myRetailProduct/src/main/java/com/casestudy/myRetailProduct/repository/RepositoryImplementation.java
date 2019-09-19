package com.casestudy.myRetailProduct.repository;

import com.casestudy.myRetailProduct.db.ProductPrice;
import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import com.mongodb.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;


@org.springframework.stereotype.Repository
public class RepositoryImplementation implements Repository {

    Logger logger = LoggerFactory.getLogger(RepositoryImplementation.class);

    @Autowired
    RestTemplate restTemplate;

    //external RedSky URL
    @Value("${productURL}")
    @Autowired
    private String productURL;

    //connection to Mongo server and creation of DB/collection
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    DB db = mongoClient.getDB("myRetail");
    DBCollection dbCollection = db.getCollection("products");

    //remove existing documents
    public void removeDocument(String id) {
        DBObject query = new BasicDBObject();
        query.put("_id", id);
        dbCollection.remove(query);
    }

    //populate collection on start up
    @PostConstruct
    public void populateDB() {

        //remove existing documents for case study startup
        removeDocument("13860428");
        removeDocument("15117729");

        //populate case study data in database
        try {
            BasicDBObject documentOne = new BasicDBObject();
            documentOne.put("_id", "13860428");
            documentOne.put("value", 5.00);
            documentOne.put("currencyCode", "USD");
            dbCollection.insert(documentOne);


            BasicDBObject documentTwo = new BasicDBObject();
            documentTwo.put("_id", "15117729");
            documentTwo.put("value", 6.00);
            documentTwo.put("currencyCode", "AUD");
            dbCollection.insert(documentTwo);

        } catch (DuplicateKeyException e) {
            logger.info("Document exists");
        }
    }

    //get price for a given ID
    public ProductPrice getPrice(String id) {

        DBObject query = new BasicDBObject();
        query.put("_id", id);
        DBObject dbObject = dbCollection.findOne(query);

        if (dbObject != null) {
            String _id = (String) dbObject.get("_id");
            Double value = (Double) dbObject.get("value");
            String currencyCode = (String) dbObject.get("currencyCode");

            ProductPrice productPrice = new ProductPrice();
            productPrice.setId(_id);
            productPrice.setValue(BigDecimal.valueOf(value));
            productPrice.setCurrencyCode(Enum.valueOf(CurrencyCodeEnum.class, currencyCode));

            return productPrice;
        } else {
            throw new DataRetrievalFailureException("Product with ID: " + id + " not found in product price database.");
        }
    }

    //get description from redsky for a given ID
    public String getProductName(String id) throws Exception {

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(productURL + id);
        String stringResponse;

        try {
            stringResponse = restTemplate.getForObject(componentsBuilder
                    .build()
                    .encode()
                    .toUri(), String.class);
        } catch (Exception e) {
            throw new Exception("Product not found in external API");
        }
        if (stringResponse != null) {
            JSONObject jsonObject = new JSONObject(stringResponse);
            if (jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description").getString("title") != null) {
                return jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description").getString("title");
            } else {
                throw new Exception("Title not found in external API");
            }
        } else {
            throw new Exception("Product not found in external API");
        }
    }

    //update the price of a given ID
    public boolean updateProductPrice(ProductPrice productPrice) {
        BasicDBObject document = new BasicDBObject();
        //update the value of the ID
        document.append("$set", new BasicDBObject().append("value", productPrice.getValue()));
        BasicDBObject searchQuery = new BasicDBObject().append("_id", productPrice.getId());
        WriteResult result = dbCollection.update(searchQuery, document); //NOT REDUNDANT, the document appends overwrite themselves
        //update the currency code of the ID
        document.append("$set", new BasicDBObject().append("currencyCode", productPrice.getCurrencyCode().toString()));
        result = dbCollection.update(searchQuery, document);

        if (result.isUpdateOfExisting()) {
            return true;
        } else return false;
    }
}
