package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;

import com.poc.shoppingpos.models.Cart;
import com.poc.shoppingpos.utils.CartHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class OrderConfirmationViewModel extends AndroidViewModel {
    private Cart mCart;
    public OrderConfirmationViewModel(@NonNull Application application) {
        super(application);
        mCart = CartHelper.getCartInstance();
    }

    public String getOrderId() {
        return mCart.getOrderID();
    }

    public int getTotalItems() {
        return mCart.getTotalQuantity();
    }

    public double getCartTotal() {
        return mCart.getTotalPrice().doubleValue();
    }


}
