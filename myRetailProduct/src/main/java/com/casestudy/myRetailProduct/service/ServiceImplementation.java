package com.casestudy.myRetailProduct.service;

import com.casestudy.myRetailProduct.db.ProductPrice;
import com.casestudy.myRetailProduct.dto.CurrentPriceDTO;
import com.casestudy.myRetailProduct.dto.FullProductDTO;
import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import com.casestudy.myRetailProduct.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.zip.DataFormatException;

@org.springframework.stereotype.Service
public class ServiceImplementation implements Service {

    @Autowired
    Repository repository;

    //get the price for a given ID
    public FullProductDTO getPrice(String id) {

        FullProductDTO fullProductDTO = new FullProductDTO();
        CurrentPriceDTO currentPriceDTO = new CurrentPriceDTO();

        try {
            ProductPrice productPrice;
            productPrice = repository.getPrice(id);
            fullProductDTO.setId(productPrice.getId());

            currentPriceDTO.setValue(productPrice.getValue());
            currentPriceDTO.setCurrencyCode(productPrice.getCurrencyCode());

            fullProductDTO.setCurrentPriceDTO(currentPriceDTO);
            fullProductDTO.setName(repository.getProductName(id));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.OK, e.getMessage());
        }
        return fullProductDTO;
    }

    //update the price for a given ID
    public String updateProductPrice(String id, CurrentPriceDTO dto) throws DataFormatException {

        //test that a valid currency code is being passed
        if (CurrencyCodeEnum.contains(dto.getCurrencyCode().toString())) {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setId(id);
            productPrice.setValue(dto.getValue());
            productPrice.setCurrencyCode(dto.getCurrencyCode());

            try {
                if (repository.updateProductPrice(productPrice)) {
                    return "Product updated in product price database with ID: " + id;
                } else {
                    throw new RuntimeException("Product failed to update in product price database with ID: " + id);
                }
            } catch (Exception e) {
            }
            throw new ResponseStatusException(HttpStatus.OK, "Product not found in product price database with ID:  " + id);
        } else
            throw new DataFormatException("Currency code must be one of the following: " + CurrencyCodeEnum.values());


    }
}
