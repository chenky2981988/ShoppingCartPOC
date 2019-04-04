package com.poc.shoppingpos.models;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.poc.shoppingpos.exception.ProductNotFoundException;
import com.poc.shoppingpos.exception.QuantityOutOfRangeException;
import com.poc.shoppingpos.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.internal.Utils;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.com
 *
 * A representation of shopping cart.
 *  A shopping cart has a map of {@link SalebleProduct} products to their corresponding quantities.
 */
public class Cart implements Parcelable {
    private Map<SalebleProduct, Integer> cartItemMap = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;
    private String orderID;

    /**
     * Add a quantity of a certain {@link SalebleProduct} product to this shopping cart
     *
     * @param saleableProduct the product will be added to this shopping cart
     * @param quantity the amount to be added
     */
    public void add(final SalebleProduct saleableProduct, int quantity) {
        if(TextUtils.isEmpty(this.getOrderID())){
            this.setOrderID(CommonUtils.getRandomOrderID());
        }
        if (cartItemMap.containsKey(saleableProduct)) {
            cartItemMap.put(saleableProduct, cartItemMap.get(saleableProduct) + quantity);
        } else {
            cartItemMap.put(saleableProduct, quantity);
        }

        totalPrice = totalPrice.add(BigDecimal.valueOf(saleableProduct.getSellingPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity += quantity;
    }

    /**
     * Set new quantity for a {@link SalebleProduct} product in this shopping cart
     *
     * @param selableProduct the product which quantity will be updated
     * @param quantity the new quantity will be assigned for the product
     * @throws ProductNotFoundException    if the product is not found in this shopping cart.
     * @throws QuantityOutOfRangeException if the quantity is negative
     */
    public void update(final SalebleProduct selableProduct, int quantity) throws ProductNotFoundException, QuantityOutOfRangeException {
        if (!cartItemMap.containsKey(selableProduct)) throw new ProductNotFoundException();
        if (quantity < 0)
            throw new QuantityOutOfRangeException(quantity + " is not a valid quantity. It must be non-negative.");

        int productQuantity = cartItemMap.get(selableProduct);
        BigDecimal productPrice = BigDecimal.valueOf(selableProduct.getSellingPrice()).multiply(BigDecimal.valueOf(productQuantity));

        cartItemMap.put(selableProduct, quantity);

        totalQuantity = totalQuantity - productQuantity + quantity;
        totalPrice = totalPrice.subtract(productPrice).add(BigDecimal.valueOf(selableProduct.getSellingPrice()).multiply(BigDecimal.valueOf(quantity)));

    }

    /**
     * Remove a certain quantity of a {@link SalebleProduct} product from this shopping cart
     *
     * @param sellable the product which will be removed
     * @param quantity the quantity of product which will be removed
     * @throws ProductNotFoundException    if the product is not found in this shopping cart
     * @throws QuantityOutOfRangeException if the quantity is negative or more than the existing quantity of the product in this shopping cart
     */
    public void remove(final SalebleProduct sellable, int quantity) throws ProductNotFoundException, QuantityOutOfRangeException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();

        int productQuantity = cartItemMap.get(sellable);

        if (quantity < 0 || quantity > productQuantity)
            throw new QuantityOutOfRangeException(quantity + " is not a valid quantity. It must be non-negative and less than the current quantity of the product in the shopping cart.");

        if (productQuantity == quantity) {
            cartItemMap.remove(sellable);
        } else {
            cartItemMap.put(sellable, productQuantity - quantity);
        }

        totalPrice = totalPrice.subtract(BigDecimal.valueOf(sellable.getSellingPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    /**
     * Remove a {@link SalebleProduct} product from this shopping cart totally
     *
     * @param sellable the product to be removed
     * @throws ProductNotFoundException if the product is not found in this shopping cart
     */
    public void remove(final SalebleProduct sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();

        int quantity = cartItemMap.get(sellable);
        cartItemMap.remove(sellable);
        totalPrice = totalPrice.subtract(BigDecimal.valueOf(sellable.getSellingPrice()).multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    /**
     * Remove all products from this shopping cart
     */
    public void clear() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
        orderID = "";
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * Get quantity of a {@link SalebleProduct} product in this shopping cart
     *
     * @param sellable the product of interest which this method will return the quantity
     * @return The product quantity in this shopping cart
     * @throws ProductNotFoundException if the product is not found in this shopping cart
     */
    public int getQuantity(final SalebleProduct sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
        return cartItemMap.get(sellable);
    }

    /**
     * Get total cost of a {@link SalebleProduct} product in this shopping cart
     *
     * @param sellable the product of interest which this method will return the total cost
     * @return Total cost of the product
     * @throws ProductNotFoundException if the product is not found in this shopping cart
     */
    public BigDecimal getCost(final SalebleProduct sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
        return BigDecimal.valueOf(sellable.getSellingPrice()).multiply(BigDecimal.valueOf(cartItemMap.get(sellable)));
    }

    /**
     * Get total price of all products in this shopping cart
     *
     * @return Total price of all products in this shopping cart
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Get total quantity of all products in this shopping cart
     *
     * @return Total quantity of all products in this shopping cart
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }



    /**
     * Get a map of products to their quantities in the shopping cart
     *
     * @return A map from product to its quantity in this shopping cart
     */
    public HashMap<SalebleProduct, Integer> getItemWithQuantity() {
        HashMap<SalebleProduct, Integer> cartItemMap = new HashMap<SalebleProduct, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<SalebleProduct, Integer> entry : cartItemMap.entrySet()) {
            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getProductName(), entry.getKey().getSellingPrice(), entry.getValue()));
        }
        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));

        return strBuilder.toString();
    }

    protected Cart(Parcel in) {
        totalPrice = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        totalQuantity = in.readInt();
        orderID = in.readString();
    }
    public Cart() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalPrice);
        dest.writeInt(totalQuantity);
        dest.writeString(orderID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();

        Map<SalebleProduct, Integer> itemMap = (Map<SalebleProduct, Integer>) getItemWithQuantity();

        for (Map.Entry<SalebleProduct, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product)entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItem.setItemTotal(getCost(entry.getKey()).doubleValue());
            cartItems.add(cartItem);
        }
        Log.d("TAG", "Cart item list: " + cartItems.size());
        return cartItems;
    }
}