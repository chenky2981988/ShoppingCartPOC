package com.poc.shoppingpos.utils;

public final class AppConstants {

    //Paytm Payment GateWay Constants

    public static final String KEY_MID = "MID";
    public static final String KEY_ORDER_ID = "ORDER_ID";
    public static final String KEY_CHANNEL_ID = "CHANNEL_ID";
    public static final String KEY_CUST_ID = "CUST_ID";
    public static final String KEY_TXN_AMOUNT = "TXN_AMOUNT";

    public static final String KEY_WEBSITE = "WEBSITE";
    public static final String KEY_INDUSTRY_TYPE_ID = "INDUSTRY_TYPE_ID";
    public static final String KEY_CALLBACK_URL = "CALLBACK_URL";
    public static final String KEY_CHECKSUMHASH = "CHECKSUMHASH";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvvm.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "mindorks_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final String KEY_BARCODE = "key_barcode";

    public static final String KEY_TXN_STATUS = "STATUS";
    public static final String TXN_SUCCESS = "TXN_SUCCESS";
    public static final String KEY_RESPMSG = "RESPMSG";


    private AppConstants() {
        // This utility class is not publicly instantiable
    }


}
