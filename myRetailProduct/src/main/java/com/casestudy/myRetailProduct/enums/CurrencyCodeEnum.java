package com.casestudy.myRetailProduct.enums;

//valid currency codes
public enum CurrencyCodeEnum  {
    USD,
    EUR,
    AUD,
    CAD,
    GBP;

    public static boolean contains(String test) {

        for (CurrencyCodeEnum c : values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
