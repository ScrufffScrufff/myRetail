package com.casestudy.myRetailProduct.controller;

import com.casestudy.myRetailProduct.db.ProductPrice;

import com.casestudy.myRetailProduct.dto.CurrentPriceDTO;
import com.casestudy.myRetailProduct.dto.FullProductDTO;
import com.casestudy.myRetailProduct.service.Service;
import com.casestudy.myRetailProduct.service.ServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/products")
public class Controller {

    @Autowired
    Service service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FullProductDTO> getPrice(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getPrice(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CurrentPriceDTO> updateProductPrice(
            @PathVariable(name = "id") String id, @RequestBody @Valid CurrentPriceDTO currentPriceDTO) throws DataFormatException {

        String dbUpdateResponse = service.updateProductPrice(id, currentPriceDTO);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(dbUpdateResponse, headers, HttpStatus.CREATED);
    }

    @RequestMapping(name = "/health", method = RequestMethod.GET)
    public String health() {
        return "OK";
    }
}
