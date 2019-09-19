package com.casestudy.myRetailProduct.dto;

import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class FullProductDTO {

    public FullProductDTO(){}

    public FullProductDTO(String id, String name, CurrentPriceDTO currentPriceDTO){
        this.id = id;
        this.name = name;
        this.currentPriceDTO = currentPriceDTO;
    }
    @Valid
    @NotNull
    private String id;

    @Valid
    @NotNull
    private String name;

    @Valid
    @NotNull
    CurrentPriceDTO currentPriceDTO;

}
