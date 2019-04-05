package com.poc.shoppingpos.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.databinding.PaymentSuccessFragmentBinding;
import com.poc.shoppingpos.ui.viewmodel.PaymentSuccessViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSuccessFragment extends BaseFragment {

    private final static String TAG = PaymentSuccessFragment.class.getSimpleName();
    private PaymentSuccessViewModel mPaymentSuccessViewModel;
    private PaymentSuccessFragmentBinding mPaymentSuccessFragmentBinding;
    @BindView(R.id.qr_code_iv)
    AppCompatImageView qrCodeImageView;

    public static PaymentSuccessFragment newInstance(Bundle bundle) {
        PaymentSuccessFragment paymentSuccessFragment = new PaymentSuccessFragment();
        paymentSuccessFragment.setArguments(bundle);
        return paymentSuccessFragment;
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
        mPaymentSuccessFragmentBinding.setPaymentSuccessViewModel(mPaymentSuccessViewModel);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String paymentResponse = bundle.toString();
            Log.d(TAG, "Payment Resposne : " + paymentResponse);
            qrCodeImageView.setImageBitmap(mPaymentSuccessViewModel.generateBitMap(paymentResponse));
            mPaymentSuccessFragmentBinding.setPaymentResponse(paymentResponse);
        }

    }

    //imageView.setImageBitmap(bitmap);
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
