package com.poc.shoppingpos.db.entity;

import com.poc.shoppingpos.models.Product;

import java.math.BigDecimal;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity implements Product {

    @PrimaryKey
    private int productID;
    private String productName;
    private String productDescription;
    private double mrp;
    private double sellingPrice;
    private String imageURL;
    private int avlQuantity;
    private String barcode;

    @Override
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    @Override
    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int getAvlQuantity() {
        return avlQuantity;
    }

    public void setAvlQuantity(int avlQuantity) {
        this.avlQuantity = avlQuantity;
    }

    @Override
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ProductEntity() {
    }

    @Ignore
    public ProductEntity(int productID, String productName, String productDescription, double mrp, double sellingPrice, String imageURL, int avlQuantity, String barcode) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.mrp = mrp;
        this.sellingPrice = sellingPrice;
        this.imageURL = imageURL;
        this.avlQuantity = avlQuantity;
        this.barcode = barcode;
    }

    public ProductEntity(Product product){
        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.mrp = product.getMrp();
        this.sellingPrice = product.getSellingPrice();
        this.imageURL = product.getImageURL();
        this.avlQuantity = product.getAvlQuantity();
        this.barcode = product.getBarcode();
    }
}
