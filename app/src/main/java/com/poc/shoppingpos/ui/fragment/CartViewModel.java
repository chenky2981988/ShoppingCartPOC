package com.poc.shoppingpos.ui.fragment;

import android.app.Application;

import com.poc.shoppingpos.models.Cart;
import com.poc.shoppingpos.models.CartItem;
import com.poc.shoppingpos.utils.CartHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class CartViewModel extends AndroidViewModel {

    private Cart mCart;

    public CartViewModel(@NonNull Application application) {
        super(application);
        mCart = CartHelper.getCartInstance();
        // Initialize a Google Pay API client for an environment suitable for testing.
        // It's recommended to create the PaymentsClient object inside of the onCreate method.

    }

    public int getTotalCartQuantity() {
        if (mCart != null) {
            return mCart.getTotalQuantity();
        }
        return 0;
    }

    public BigDecimal getCartTotalAmount() {
        if (mCart != null) {
            return mCart.getTotalPrice();
        }
        return BigDecimal.ZERO;
    }

    public List<CartItem> getCartItems() {
        if (mCart != null) {
            return mCart.getCartItems();
        }
        return new ArrayList<CartItem>();
    }


}
