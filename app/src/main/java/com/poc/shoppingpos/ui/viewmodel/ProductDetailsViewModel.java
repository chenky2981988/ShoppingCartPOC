package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import com.poc.shoppingpos.DataRepository;
import com.poc.shoppingpos.ShoppingPOSApplication;
import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.models.Cart;
import com.poc.shoppingpos.models.Product;
import com.poc.shoppingpos.utils.CartHelper;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductDetailsViewModel extends AndroidViewModel {

    public ObservableField<ProductEntity> productEntityObservableField = new ObservableField<>();

    private LiveData<ProductEntity> mObservableProduct;
    private Product mProduct;

//    public ProductDetailsViewModel(@NonNull Application application) {
//        super(application);
//        mObservableProduct = new MediatorLiveData<>();
//        mObservableProduct.setValue(null);
//        mRepository = ((ShoppingPOSApplication) application).getRepository();
//    }

    public ProductDetailsViewModel(@NonNull Application application, DataRepository repository,
                                   final String barcode) {
        super(application);
        mObservableProduct = repository.searchProductBYBarcode(barcode);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public void setProduct(ProductEntity product) {
        this.productEntityObservableField.set(product);
    }

    public void addProductToCart(){
        Cart myCart = CartHelper.getCartInstance();
        //Log.d("TAG", "Adding product: " + productEntityObservableField.get().getProductName());
        myCart.add(productEntityObservableField.get(), 1);
    }

    public void getCartItemTotal(){
        Cart myCart = CartHelper.getCartInstance();
        Log.d("TAG","Total Quantity : " + myCart.getTotalQuantity());
        Log.d("TAG","Total Cart Value : " + myCart.getTotalPrice());
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mBarcode;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String barcode) {
            mApplication = application;
            mBarcode = barcode;
            mRepository = ((ShoppingPOSApplication) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductDetailsViewModel(mApplication, mRepository, mBarcode);
        }
    }

}
