package com.casestudy.myRetailProduct.repository;

import com.casestudy.myRetailProduct.db.ProductPrice;
import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import com.mongodb.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class RepositoryImplementationTest {
    @Mock
    Logger logger;
    @Mock
    RestTemplate restTemplate;
    @Mock
    MongoClient mongoClient;
    @Mock
    DB db;
    @Mock
    DBCollection dbCollection;
    @InjectMocks
    RepositoryImplementation repositoryImplementation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRemoveDocument() throws Exception {
        repositoryImplementation.removeDocument("id");
    }

    @Test
    public void testPopulateDB() throws Exception {
        repositoryImplementation.populateDB();
    }

    @Test
    public void testGetPrice() throws Exception {
        //setup
        ProductPrice mockProductPrice = new ProductPrice();
        mockProductPrice.setId("13860428");
        mockProductPrice.setValue(BigDecimal.valueOf(6.00));
        mockProductPrice.setCurrencyCode(CurrencyCodeEnum.USD);

        DBObject mockDbObject = new BasicDBObject();
        mockDbObject.put("_id", "13860428");
        mockDbObject.put("value", 6.00);
        mockDbObject.put("currencyCode", "USD");

        when(dbCollection.findOne(any())).thenReturn(mockDbObject);

        //execution
        ProductPrice result = repositoryImplementation.getPrice("13860428");

        //verification
        Assert.assertEquals(mockProductPrice, result);
    }

    @Test
    public void testUpdateProductPrice() {

        //setup
        ProductPrice mockProductPrice = new ProductPrice();
        mockProductPrice.setId("13860428");
        mockProductPrice.setValue(BigDecimal.valueOf(6.00));
        mockProductPrice.setCurrencyCode(CurrencyCodeEnum.USD);

        DBObject mockDbObject = new BasicDBObject();
        mockDbObject.put("_id", "13860428");
        mockDbObject.put("value", 6.00);
        mockDbObject.put("currencyCode", "USD");
        WriteResult writeResult = new WriteResult(0, true, null);

        when(dbCollection.update(any(), any())).thenReturn(writeResult);

        //execution
        boolean result = repositoryImplementation.updateProductPrice(mockProductPrice);

        //verification
        Assert.assertEquals(true, result);
    }
}