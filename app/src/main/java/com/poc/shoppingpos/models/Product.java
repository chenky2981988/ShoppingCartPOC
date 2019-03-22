package com.poc.shoppingpos.models;

import java.math.BigDecimal;

public interface Product extends SalebleProduct{

    int getProductID();
    String getProductName();
    String getProductDescription();
    double getMrp();
    double getSellingPrice();
    String getImageURL();
    int getAvlQuantity();
    String getBarcode();
}
