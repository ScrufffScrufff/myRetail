package com.casestudy.myRetailProduct.dto;

import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CurrentPriceDTO {

    public CurrentPriceDTO(BigDecimal value, CurrencyCodeEnum currencyCodeEnum){
        this.value = value;
        this.currencyCode = currencyCodeEnum;
    }

    public CurrentPriceDTO(){
    }

    @Valid
    @NotNull
    private BigDecimal value;

    @Valid
    @NotNull
    private CurrencyCodeEnum currencyCode;
}
