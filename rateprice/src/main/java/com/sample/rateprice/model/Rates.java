package com.sample.rateprice.model;

import org.springframework.stereotype.Component;

/**
 * Class Rates which contains an array of Rates
 */
@Component
public class Rates {

    Rate[] rates;

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }
}
