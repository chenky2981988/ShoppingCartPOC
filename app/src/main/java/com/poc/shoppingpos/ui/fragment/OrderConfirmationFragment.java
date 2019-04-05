package com.poc.shoppingpos.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.databinding.OrderConfirmationLayoutBinding;
import com.poc.shoppingpos.ui.viewmodel.OrderConfirmationViewModel;
import com.poc.shoppingpos.utils.AppConstants;
import com.poc.shoppingpos.utils.CartHelper;
import com.poc.shoppingpos.utils.PaymentManager;
import com.poc.shoppingpos.utils.PaymentUtils;

import java.util.HashMap;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderConfirmationFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, PaytmPaymentTransactionCallback {

    private final static String TAG = OrderConfirmationFragment.class.getSimpleName();
    private OrderConfirmationViewModel mViewModel;
    private OrderConfirmationLayoutBinding mOrderConfirmationFragmentBinding;
    private final static int REQUEST_SMS_READ_PERMISSION = 105;

    @BindView(R.id.payment_selection_radio_group)
    RadioGroup paymentSelectionRadioGrp;

    private PaytmPGService mPaytmPGService;

    public static OrderConfirmationFragment newInstance() {
        return new OrderConfirmationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mOrderConfirmationFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.order_confirmation_layout, container, false);
        ButterKnife.bind(this, mOrderConfirmationFragmentBinding.getRoot());
        return mOrderConfirmationFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OrderConfirmationViewModel.class);
        paymentSelectionRadioGrp.setOnCheckedChangeListener(this);
        mOrderConfirmationFragmentBinding.setOrderConfirmationViewModel(mViewModel);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.paytm_radio_button:
                mOrderConfirmationFragmentBinding.setIsPayBtnEnable(true);
                break;
            default:
                mOrderConfirmationFragmentBinding.setIsPayBtnEnable(false);
        }
    }

    @OnClick(R.id.proceed_to_pay_btn)
    public void onProceedToPayClicked() {
        if (ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_READ_PERMISSION);
        } else {
//            CartHelper.getCartInstance().clear();
//            getBaseActivity().invalidateOptionsMenu();
//            replaceFragment(PaymentSuccessFragment.newInstance(), true, ProductDetailsFragment.class.getName());
            sendOrderToPaytm();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_READ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseActivity().getApplicationContext(), "SMS Read Permission Granted!", Toast.LENGTH_SHORT).show();
            mPaytmPGService = PaytmPGService.getStagingService();
            sendOrderToPaytm();
        }
    }

    private void sendOrderToPaytm() {
        try {
            String custId = "Cust" + String.valueOf(new Random().nextInt(1000));

            PaymentManager paymentManager = PaymentManager.getInstance();

            HashMap<String, String> paytmParams = PaymentUtils.generatePaymentParams(mViewModel.getOrderId(), custId, mViewModel.getCartTotal());
            paymentManager.startTransaction(getBaseActivity(), paytmParams, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        if (inResponse != null) {
            String txnStatus = inResponse.getString(AppConstants.KEY_TXN_STATUS);
            Log.i(TAG, "Trsaction Response : " + txnStatus);
            if (!TextUtils.isEmpty(txnStatus) && txnStatus.equalsIgnoreCase(AppConstants.TXN_SUCCESS)) {
                CartHelper.getCartInstance().clear();
                getBaseActivity().invalidateOptionsMenu();
                replaceFragment(PaymentSuccessFragment.newInstance(inResponse), true, PaymentSuccessFragment.class.getSimpleName());
            }
            Toast.makeText(getBaseActivity().getApplicationContext(), "Payment Transaction response " + inResponse.get(AppConstants.KEY_RESPMSG), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(getBaseActivity().getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Log.e(TAG, "clientAuthenticationFailed : " + inErrorMessage);
        Toast.makeText(getBaseActivity().getApplicationContext(), "Authentication failed: Server error : " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Log.e(TAG, "someUIErrorOccurred : " + inErrorMessage);
        Toast.makeText(getBaseActivity().getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Log.e(TAG, "onErrorLoadingWebPage [iniErrorCode] : " + iniErrorCode);
        Log.e(TAG, "onErrorLoadingWebPage [inErrorMessage] : " + inErrorMessage);
        Log.e(TAG, "onErrorLoadingWebPage [inFailingUrl] : " + inFailingUrl);
        Toast.makeText(getBaseActivity().getApplicationContext(), "Unable to load webpage " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(getBaseActivity().getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Log.e(TAG, "onTransactionCancel [inErrorMessage] : " + inErrorMessage);
        Log.d(TAG, "onTransactionCancel [inResponse] : " + inResponse.toString());
        Toast.makeText(getBaseActivity().getApplicationContext(), "Transaction Cancelled" + inErrorMessage, Toast.LENGTH_LONG).show();
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
