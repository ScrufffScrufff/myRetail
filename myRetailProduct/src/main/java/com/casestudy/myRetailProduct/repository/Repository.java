package com.casestudy.myRetailProduct.repository;

import com.casestudy.myRetailProduct.db.ProductPrice;

@org.springframework.stereotype.Repository
public interface Repository {

    public ProductPrice getPrice(String id);

    public String getProductName(String id) throws Exception;

    public boolean updateProductPrice(ProductPrice productPrice);
}
