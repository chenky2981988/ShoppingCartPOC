package com.poc.shoppingpos.utils;

import android.content.Context;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

/**
 * Created by Chirag Sidhiwala on 4/4/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class PaymentManager {
    private static PaymentManager paymentManager;
    private PaytmPGService mPaytmPGService;

    private PaymentManager() {
    }

    public static synchronized PaymentManager getInstance() {
        if (paymentManager == null) {
            paymentManager = new PaymentManager();
        }
        return paymentManager;
    }

    private void initializePaymentService(HashMap<String, String> paytmParams) {
        PaytmClientCertificate paytmClientCertificate = PaymentUtils.generatePaytmClientCertificate();
        PaytmOrder paytmOrder = PaymentUtils.generatePaytmOrder(paytmParams);
        getPaytmService().initialize(paytmOrder, paytmClientCertificate);
    }

    public void startTransaction(Context mContext, HashMap<String, String> paytmParams, PaytmPaymentTransactionCallback paytmPaymentTransactionCallback){
        initializePaymentService(paytmParams);
        mPaytmPGService.startPaymentTransaction(mContext, true, true, paytmPaymentTransactionCallback);
    }

    private PaytmPGService getPaytmService(){
        if (mPaytmPGService == null) {
            mPaytmPGService = PaytmPGService.getStagingService();
        }
        return mPaytmPGService;
    }

}
