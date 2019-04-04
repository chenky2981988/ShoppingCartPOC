package com.poc.shoppingpos.ui.viewmodel;

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
    }

    public int getTotalCartQuantity() {
        if (mCart != null) {
            return mCart.getTotalQuantity();
        }
        return 0;
    }

    public double getCartTotalAmount() {
        if (mCart != null) {
            return mCart.getTotalPrice().doubleValue();
        }
        return BigDecimal.ZERO.doubleValue();
    }

    public List<CartItem> getCartItems() {
        if (mCart != null) {
            return mCart.getCartItems();
        }
        return new ArrayList<CartItem>();
    }

    public void removeItemFromCart(int position){
        CartItem mCartItem = mCart.getCartItems().get(position);
        mCart.remove(mCartItem.getProduct());
    }


}
