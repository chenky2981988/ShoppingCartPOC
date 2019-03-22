package com.poc.shoppingpos.models;

/**
 * Created by Chirag Sidhiwala on 3/20/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class CartItem {
    private Product product;
    private int quantity;
    private double itemTotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public double getItemTotal() {
        return (product.getSellingPrice() * quantity);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", itemTotal=" + itemTotal +
                '}';
    }
}
