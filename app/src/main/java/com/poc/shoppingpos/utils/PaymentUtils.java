package com.poc.shoppingpos.utils;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;

import org.conscrypt.Conscrypt;

import java.security.Security;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Chirag Sidhiwala on 4/4/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class PaymentUtils {

    // Paytm Constants
    private static final String PAYTM_CLIENT_PASSWORD = "Suruchi@2981988";
    private static final String PAYTM_CLIENT_NAME = "Chirag";
    private static final String PAYTM_MID = "Capgem73247037907474";
    private static final String PAYTM_MERCHANT_KEY = "zqzSLuVTvOZRYez5";
    private static final String PAYTM_CHANNEL_ID = "WAP";
    private static final String PAYTM_WEBSITE = "APPSTAGING";
    private static final String PAYTM_INDUSTRY_TYPE_ID = "Retail";
    private static final String PAYTM_CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";

    public static HashMap<String, String> generatePaymentParams(String orderId, String custId, double txnAmount) {
        HashMap<String, String> paytmParams = new HashMap<>();
        paytmParams.put(AppConstants.KEY_MID, PAYTM_MID);
        paytmParams.put(AppConstants.KEY_ORDER_ID, orderId);
        paytmParams.put(AppConstants.KEY_CHANNEL_ID, PAYTM_CHANNEL_ID);
        paytmParams.put(AppConstants.KEY_CUST_ID, custId);
        paytmParams.put(AppConstants.KEY_TXN_AMOUNT, String.valueOf(txnAmount));
        paytmParams.put(AppConstants.KEY_WEBSITE, PAYTM_WEBSITE);
        paytmParams.put(AppConstants.KEY_INDUSTRY_TYPE_ID, PAYTM_INDUSTRY_TYPE_ID);
        paytmParams.put(AppConstants.KEY_CALLBACK_URL, new StringBuilder(PAYTM_CALLBACK_URL).append(orderId).toString());
        paytmParams.put(AppConstants.KEY_CHECKSUMHASH, generateChecksum(paytmParams));
        return paytmParams;
    }

    private static String generateChecksum(HashMap<String, String> paytmParams) {
        String paytmChecksum = null;
        try {
            Security.insertProviderAt(Conscrypt.newProvider("SunJCE"), 2);
            paytmChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PAYTM_MERCHANT_KEY, generateTreeMapFromHashMap(paytmParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paytmChecksum;
    }

    private static TreeMap<String, String> generateTreeMapFromHashMap(HashMap<String, String> paytmParams) {
        TreeMap<String, String> checksumTreeMap = new TreeMap<>();
        checksumTreeMap.putAll(paytmParams);
        return checksumTreeMap;
    }

    public static PaytmClientCertificate generatePaytmClientCertificate(){
        return new PaytmClientCertificate(PAYTM_CLIENT_PASSWORD, PAYTM_CLIENT_NAME);
    }

    public static PaytmOrder generatePaytmOrder(HashMap<String, String> paytmParams){
        return new PaytmOrder(paytmParams);
    }
}
