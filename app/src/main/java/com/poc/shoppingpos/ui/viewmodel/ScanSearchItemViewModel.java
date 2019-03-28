package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;

import com.poc.shoppingpos.DataRepository;
import com.poc.shoppingpos.ShoppingPOSApplication;
import com.poc.shoppingpos.db.entity.ProductEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * Created by Chirag Sidhiwala on 3/26/2019.
 * chirag.sidhiwala@capgemini.com
 */

public class ScanSearchItemViewModel extends AndroidViewModel {

    private MediatorLiveData<List<ProductEntity>> mObservableProduct;
    private DataRepository mRepository;
    private LiveData<List<ProductEntity>> productList;

    public ObservableField<ProductEntity> product = new ObservableField<>();
    public ScanSearchItemViewModel(@NonNull Application application) {
        super(application);
        mObservableProduct = new MediatorLiveData<>();
        mObservableProduct.setValue(null);
        mRepository = ((ShoppingPOSApplication) application).getRepository();
    }
    public void searchProductByBarcode(String barcode) {
        productList = mRepository.searchProductBYBarcode(barcode+"%");
        mObservableProduct.addSource(productList, mObservableProduct::setValue);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<List<ProductEntity>> getObservableProductList() {
        return mObservableProduct;
    }

//    public void setProductList(List<ProductEntity> productList) {
//        this.product.set(product);
//    }
}
