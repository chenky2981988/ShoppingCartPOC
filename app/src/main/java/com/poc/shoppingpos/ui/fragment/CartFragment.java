package com.poc.shoppingpos.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.adapters.CartRecycleViewAdapter;
import com.poc.shoppingpos.utils.AppConstants;
import com.poc.shoppingpos.utils.CartHelper;

import org.conscrypt.Conscrypt;

import java.security.Security;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends BaseFragment implements PaytmPaymentTransactionCallback {

    private CartViewModel mViewModel;

    @BindView(R.id.cart_recycle_view)
    RecyclerView cartRecycleView;
    @BindView(R.id.cart_total_amount_tv)
    AppCompatTextView cartTotalAmountTv;
    @BindView(R.id.proceed_to_pay_btn)
    AppCompatButton proceedToPayBtn;
    @BindView(R.id.empty_cart_tv)
    AppCompatTextView emptyCartTextView;
    private final static int REQUEST_SMS_READ_PERMISSION = 105;

    private CartRecycleViewAdapter mCartRecycleViewAdapter;
    private PaytmPGService mPaytmPGService;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getBaseActivity()).setMenuVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        if (mViewModel.getTotalCartQuantity() > 0) {
            emptyCartTextView.setVisibility(View.GONE);
            cartRecycleView.setVisibility(View.VISIBLE);
            cartTotalAmountTv.setVisibility(View.VISIBLE);
            proceedToPayBtn.setVisibility(View.VISIBLE);

            cartRecycleView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            mCartRecycleViewAdapter = new CartRecycleViewAdapter(getBaseActivity().getApplicationContext(), mViewModel.getCartItems());
            cartRecycleView.setAdapter(mCartRecycleViewAdapter);
            cartTotalAmountTv.setText(String.format(getString(R.string.grand_total), mViewModel.getCartTotalAmount()));
        } else {
            cartRecycleView.setVisibility(View.GONE);
            cartTotalAmountTv.setVisibility(View.GONE);
            proceedToPayBtn.setVisibility(View.GONE);
            emptyCartTextView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.proceed_to_pay_btn)
    public void actionProceedToPay() {
        Log.d("TAG", "Proceed To Pay Clicked");
        if (ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getBaseActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        } else {
            mPaytmPGService = PaytmPGService.getStagingService();
            sendOrderToPaytm();

        }

    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
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
// Key in your staging and production MID available in your dashboard
            String merchantKey = AppConstants.PAYTM_MERCHANT_KEY;
// Key in your staging and production MID available in your dashboard
            String orderId = "OrderId"+String.valueOf(new Random().nextInt(10000));
            String custId = "Cust"+String.valueOf(new Random().nextInt(1000));

            String txnAmount = mViewModel.getCartTotalAmount().toString();

            HashMap<String, String> paytmParams = new HashMap<>();
            paytmParams.put("MID", AppConstants.PAYTM_MID);
            paytmParams.put("ORDER_ID", orderId);
            paytmParams.put("CHANNEL_ID", AppConstants.PAYTM_CHANNEL_ID);
            paytmParams.put("CUST_ID", custId);
//            paytmParams.put("MOBILE_NO", mobileNo);
//            paytmParams.put("EMAIL", email);
            Log.d("TAG", "Trasaction amount : " + txnAmount);
            paytmParams.put("TXN_AMOUNT", txnAmount);
            paytmParams.put("WEBSITE", AppConstants.PAYTM_WEBSITE);
            paytmParams.put("INDUSTRY_TYPE_ID", AppConstants.PAYTM_INDUSTRY_TYPE_ID);
            paytmParams.put("CALLBACK_URL", AppConstants.PAYTM_CALLBACK_URL + orderId);
            TreeMap<String, String> checksumTreeMap = new TreeMap<>();
            checksumTreeMap.putAll(paytmParams);
            Security.insertProviderAt(Conscrypt.newProvider("SunJCE"), 2);
            String paytmChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchantKey, checksumTreeMap);
            Log.d("TAG", "PaytmCheckSum : " + paytmChecksum);
            paytmParams.put("CHECKSUMHASH", paytmChecksum);
            PaytmOrder paytmOrder = new PaytmOrder(paytmParams);

            PaytmClientCertificate Certificate = new PaytmClientCertificate("Suruchi@2981988", "Chirag");
            mPaytmPGService.initialize(paytmOrder, Certificate);

            mPaytmPGService.startPaymentTransaction(getBaseActivity(), true, true, this);

//            mPaytmPGService.startPaymentTransaction(getBaseActivity(), true, true, new PaytmPaymentTransactionCallback() {
//                /*Call Backs*/
//                public void someUIErrorOccurred(String inErrorMessage) {
//
//                }
//
//                public void onTransactionResponse(Bundle inResponse) {
//
//                }
//
//                public void networkNotAvailable() {
//
//                }
//
//                public void clientAuthenticationFailed(String inErrorMessage) {
//
//                }
//
//                public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//
//                }
//
//                public void onBackPressedCancelTransaction() {
//
//                }
//
//                public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//
//                }
//            });
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        if (inResponse != null) {
            String txnStatus = inResponse.getString(AppConstants.KEY_TXN_STATUS);
            Log.d("TAG", "Trsaction Response : " + txnStatus);
            if (!TextUtils.isEmpty(txnStatus) && txnStatus.equalsIgnoreCase(AppConstants.TXN_SUCCESS)) {
                CartHelper.getCartInstance().clear();
                getBaseActivity().invalidateOptionsMenu();
                getBaseActivity().onBackPressed();
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
        Log.d("TAG", "clientAuthenticationFailed : " + inErrorMessage);
        Toast.makeText(getBaseActivity().getApplicationContext(), "Authentication failed: Server error : " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Log.d("TAG", "someUIErrorOccurred : " + inErrorMessage);
        Toast.makeText(getBaseActivity().getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Log.d("TAG", "iniErrorCode : " + iniErrorCode);
        Log.d("TAG", "inErrorMessage : " + inErrorMessage);
        Log.d("TAG", "inFailingUrl : " + inFailingUrl);
        Toast.makeText(getBaseActivity().getApplicationContext(), "Unable to load webpage " + inErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(getBaseActivity().getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Log.d("TAG", "inErrorMessage : " + inErrorMessage);
        Log.d("TAG", "inResponse : " + inResponse.toString());
        Toast.makeText(getBaseActivity().getApplicationContext(), "Transaction Cancelled" + inResponse.toString(), Toast.LENGTH_LONG).show();
    }
}
