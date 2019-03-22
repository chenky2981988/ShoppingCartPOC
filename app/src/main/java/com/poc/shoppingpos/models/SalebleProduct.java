package com.poc.shoppingpos.models;

import java.math.BigDecimal;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.
 *
 * Implements this interface for any product which can be added to shopping cart
 */
public interface SalebleProduct {
    double getSellingPrice();

    String getProductName();
}
