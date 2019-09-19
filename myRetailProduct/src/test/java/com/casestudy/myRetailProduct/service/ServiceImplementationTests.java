package com.casestudy.myRetailProduct.service;

import com.casestudy.myRetailProduct.db.ProductPrice;
import com.casestudy.myRetailProduct.dto.CurrentPriceDTO;
import com.casestudy.myRetailProduct.dto.FullProductDTO;
import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import com.casestudy.myRetailProduct.repository.RepositoryImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class ServiceImplementationTests {
    @Mock
    RepositoryImplementation repositoryImplementation;
    @InjectMocks
    ServiceImplementation service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPrice() throws Exception {

        //setup
        ProductPrice mockProductPrice = new ProductPrice();
        mockProductPrice.setId("1");
        mockProductPrice.setValue(BigDecimal.valueOf(1.00));
        mockProductPrice.setCurrencyCode(CurrencyCodeEnum.EUR);
        FullProductDTO mockFullProductDTO = new FullProductDTO("1", null, new CurrentPriceDTO(BigDecimal.valueOf(1.00), CurrencyCodeEnum.EUR));

        when(repositoryImplementation.getPrice(anyString())).thenReturn(mockProductPrice);
        when(repositoryImplementation.getProductName(anyString())).thenReturn(null);

        //execution
        FullProductDTO result = service.getPrice("id");

        //verification
        Assert.assertEquals(mockFullProductDTO, result);
    }

    @Test
    public void testUpdateProductPrice() throws Exception {

        //setup
        String id = "1";

        when(repositoryImplementation.updateProductPrice(any())).thenReturn(true);

        //execution
        String result = service.updateProductPrice(id, new CurrentPriceDTO(BigDecimal.valueOf(1.00), CurrencyCodeEnum.USD));

        //verification
        Assert.assertEquals("Product updated in product price database with ID: " + id, result);
    }

    //verification
    @Test(expected = Exception.class)
    public void testExceptionGetPrice() {
        //setup
        when(repositoryImplementation.getPrice(any())).thenThrow(new Exception());

        //execution
        service.getPrice("1");
    }
}