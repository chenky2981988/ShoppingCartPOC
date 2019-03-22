package com.poc.shoppingpos.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "productsFts")
@Fts4(contentEntity = ProductEntity.class)
public class ProductFtsEntity {
    private String productName;
    private String productDescription;

    public ProductFtsEntity(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductDescription() {
        return this.productDescription;
    }
}
