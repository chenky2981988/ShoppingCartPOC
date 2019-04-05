package com.poc.shoppingpos.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.databinding.PaymentSuccessFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSuccessFragment extends BaseFragment {

    private PaymentSuccessViewModel mPaymentSuccessViewModel;
    private PaymentSuccessFragmentBinding mPaymentSuccessFragmentBinding;

    public static PaymentSuccessFragment newInstance() {
        return new PaymentSuccessFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPaymentSuccessFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.payment_success_fragment, container, false);
        ButterKnife.bind(this, mPaymentSuccessFragmentBinding.getRoot());
        return mPaymentSuccessFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPaymentSuccessViewModel = ViewModelProviders.of(this).get(PaymentSuccessViewModel.class);
    }

    @OnClick(R.id.home_button)
    public void backToHome() {
        getBaseActivity().goToHome();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
