package com.casestudy.myRetailProduct.service;

import com.casestudy.myRetailProduct.dto.CurrentPriceDTO;
import com.casestudy.myRetailProduct.dto.FullProductDTO;

import java.util.zip.DataFormatException;

public interface Service {

    public FullProductDTO getPrice(String id);

    public String updateProductPrice(String id, CurrentPriceDTO dto) throws DataFormatException;
}
