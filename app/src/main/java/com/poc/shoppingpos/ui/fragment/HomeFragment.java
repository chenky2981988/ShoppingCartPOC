package com.poc.shoppingpos.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.databinding.HomeFragmentBinding;
import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.viewmodel.HomeViewModel;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    private HomeViewModel mHomeViewModel;
    public static String TAG = HomeFragment.class.getName();
    @BindView(R.id.scan_button)
    AppCompatButton scanItemButton;

    //HomeFragmentBinding mHOHomeFragmentBinding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //mHOHomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        //return mHOHomeFragmentBinding.getRoot();
        return view;
    }

    @OnClick(R.id.scan_button)
    public void onScanFragmentClicked(){
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            replaceFragment(ScanSearchItemFragment.newInstance() , true,ScanSearchItemFragment.class.getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getBaseActivity()).setMenuVisible(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        subscribeUi(mHomeViewModel.getProducts());
    }

    private void subscribeUi(LiveData<ProductEntity> liveData) {
        // Update the list when the data changes
        liveData.observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity myProducts) {
                if (myProducts != null) {
//                    mBinding.setIsLoading(false);
//                    mProductAdapter.setProductList(myProducts);
                    Log.d("TAG","Product List Size : " + myProducts.getProductName());
                } else {
                    //mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                //mBinding.executePendingBindings();
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment;
    }
}
