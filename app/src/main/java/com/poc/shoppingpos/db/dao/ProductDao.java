package com.poc.shoppingpos.db.dao;

import com.poc.shoppingpos.db.entity.ProductEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Query("select * from products where productID = :productId")
    LiveData<ProductEntity> loadProduct(int productId);

    @Query("select * from products where productID = :productId")
    ProductEntity loadProductSync(int productId);

    @Query("select * from products where barcode LIKE :barcode")
    LiveData<ProductEntity> loadProductByBarcode(String barcode);

    @Query("select * from products where barcode LIKE :barcode")
    LiveData<List<ProductEntity>> searchProductByBarcode(String barcode);

    @Query("select * from products where barcode = :barcode")
    ProductEntity loadProductByBarcodeSync(String barcode);

    @Query("SELECT products.* FROM products JOIN productsFts ON (products.productID = productsFts.rowid) "
            + "WHERE productsFts MATCH :query")
    LiveData<List<ProductEntity>> searchAllProducts(String query);


}
