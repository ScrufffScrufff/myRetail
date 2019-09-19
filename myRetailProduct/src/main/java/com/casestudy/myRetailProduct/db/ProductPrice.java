package com.casestudy.myRetailProduct.db;

import com.casestudy.myRetailProduct.enums.CurrencyCodeEnum;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ProductPrice")
public class ProductPrice {
    public ProductPrice(){}

    public ProductPrice(String id, BigDecimal value, CurrencyCodeEnum currencyCodeEnum){
        this.id = id;
        this.value = value;
        this.currencyCode = currencyCodeEnum;
    }

    @Id
    @Valid
    @NotNull
    private String id;

    @Valid
    @NotNull
    private BigDecimal value;

    @Valid
    @NotNull
    private CurrencyCodeEnum currencyCode;
}
