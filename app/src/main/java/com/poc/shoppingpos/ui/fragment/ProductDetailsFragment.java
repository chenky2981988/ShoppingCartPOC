package com.poc.shoppingpos.ui.fragment;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.databinding.ProductDetailsFragmentBinding;
import com.poc.shoppingpos.db.AppDatabase;
import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.viewmodel.ProductDetailsViewModel;
import com.poc.shoppingpos.utils.AppConstants;

public class ProductDetailsFragment extends BaseFragment {

    private ProductDetailsViewModel mViewModel;


    @BindView(R.id.quantity_count_tv)
    AppCompatTextView quantityCountTextView;

    private String productBarcode;
    private static final String TAG = ProductDetailsFragment.class.getName();

    private ProductDetailsFragmentBinding mProductDetailsFragmentBinding;

    public static ProductDetailsFragment newInstance(String barcode) {
        ProductDetailsFragment mProductDetailsFragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.KEY_BARCODE, barcode);
        mProductDetailsFragment.setArguments(args);
        return mProductDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getBaseActivity()).setMenuVisible(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mProductDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.product_details_fragment, container, false);
        //View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        ButterKnife.bind(this, mProductDetailsFragmentBinding.getRoot());

        return mProductDetailsFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            productBarcode = getArguments().getString(AppConstants.KEY_BARCODE);
            Log.d(TAG, "Received Barcode : " + productBarcode);

            ProductDetailsViewModel.Factory factory = new ProductDetailsViewModel.Factory(
                    getActivity().getApplication(), productBarcode);

            mViewModel = ViewModelProviders.of(this, factory).get(ProductDetailsViewModel.class);
            subscribeToModel(mViewModel);
            mProductDetailsFragmentBinding.setProductDetailViewModel(mViewModel);
        }

    }


    private void subscribeToModel(ProductDetailsViewModel mProductDetailsViewModel){
        mProductDetailsViewModel.getObservableProduct().observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(ProductEntity productEntity) {
                if(productEntity != null) {
                    Log.d("TAG", "Product Details: " + productEntity.getProductName());
                    mViewModel.setProduct(productEntity);

                }else {
                    Log.d("TAG", "Product Entity Null");
                }
            }
        });
    }

//    private void subscribeToViewModel(LiveData<ProductEntity> liveDatal) {
//
//        // Observe product data
//        liveDatal.observe(this, new Observer<ProductEntity>() {
//            @Override
//            public void onChanged(@Nullable ProductEntity productEntity) {
//                if(productEntity != null) {
//                    Log.d("TAG", "Product Details: " + productEntity.getProductName());
//                    mViewModel.setProduct(productEntity);
//
//                }else {
//                    Log.d("TAG", "Product Entity Null");
//                }
//            }
//        });
//    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.product_details_fragment;
    }

    @OnClick(R.id.plus_btn)
    public void onPlusBtnClick() {
        Log.d("TAG", "Plus Button Click");
    }

    @OnClick(R.id.minus_btn)
    public void onMinusBtnClick() {
        Log.d("TAG", "Minus Button Click");
    }

    @OnClick(R.id.add_to_cart_button)
    public void onAddToCartBtnClick() {
        Log.d("TAG", "Add To Cart Button Click");
        mViewModel.addProductToCart();
        //mViewModel.getCartItemTotal();
        getBaseActivity().invalidateOptionsMenu();
        getBaseActivity().onBackPressed();
    }

}
