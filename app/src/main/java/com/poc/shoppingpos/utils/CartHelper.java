package com.poc.shoppingpos.utils;

import com.poc.shoppingpos.models.Cart;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.com
 *
 * A helper class to retrieve the static shopping cart.
 * Call {@code getCart()} to retrieve the shopping cart before you perform any operation on the shopping cart.
 */
public class CartHelper {
    private static Cart cart = new Cart();

    /**
     * Retrieve the shopping cart. Call this before perform any manipulation on the shopping cart.
     *
     * @return the shopping cart
     */
    public static Cart getCartInstance() {
        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }
}
