package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;

import com.poc.shoppingpos.DataRepository;
import com.poc.shoppingpos.ShoppingPOSApplication;
import com.poc.shoppingpos.db.entity.ProductEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ProductEntity> mObservableProducts;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mObservableProducts = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableProducts.setValue(null);

        mRepository = ((ShoppingPOSApplication) application).getRepository();
        //LiveData<List<ProductEntity>> products = mRepository.getProducts();
        LiveData<ProductEntity> products = mRepository.loadProduct(2);
        // observe the changes of the products from the database and forward them
        mObservableProducts.addSource(products, mObservableProducts::setValue);
    }
    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<ProductEntity> getProducts() {
        return mObservableProducts;
    }

}
