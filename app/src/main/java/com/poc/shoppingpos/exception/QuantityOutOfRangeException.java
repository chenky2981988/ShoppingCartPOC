package com.poc.shoppingpos.exception;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.com
 *  Throw this exception to indicate invalid quantity to be used on a shopping cart product.
 */
public class QuantityOutOfRangeException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Quantity is out of range";

    public QuantityOutOfRangeException() {
        super(DEFAULT_MESSAGE);
    }

    public QuantityOutOfRangeException(String message) {
        super(message);
    }
}
