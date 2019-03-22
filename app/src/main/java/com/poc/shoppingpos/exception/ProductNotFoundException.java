package com.poc.shoppingpos.exception;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.com
 *
 * Throw this exception to indicate invalid operation on product which does not belong to a shopping cart
 */
public class ProductNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Product is not found in the shopping cart.";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}