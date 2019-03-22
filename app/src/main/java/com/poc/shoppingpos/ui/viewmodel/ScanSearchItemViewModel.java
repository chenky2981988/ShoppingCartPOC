package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;

import com.poc.shoppingpos.DataRepository;
import com.poc.shoppingpos.ShoppingPOSApplication;
import com.poc.shoppingpos.db.entity.ProductEntity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class ScanSearchItemViewModel extends AndroidViewModel {

    private MediatorLiveData<ProductEntity> mObservableProduct;
    private DataRepository mRepository;
    private LiveData<ProductEntity> products;

    public ObservableField<ProductEntity> product = new ObservableField<>();
    public ScanSearchItemViewModel(@NonNull Application application) {
        super(application);
        mObservableProduct = new MediatorLiveData<>();
        mObservableProduct.setValue(null);
        mRepository = ((ShoppingPOSApplication) application).getRepository();
    }
    public void searchProductByBarcode(String barcode) {
        products = mRepository.searchProductBYBarcode(barcode);
        mObservableProduct.addSource(products, mObservableProduct::setValue);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public void setProduct(ProductEntity product) {
        this.product.set(product);
    }
}
