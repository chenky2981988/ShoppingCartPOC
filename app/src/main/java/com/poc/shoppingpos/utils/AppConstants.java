package com.poc.shoppingpos.utils;

public final class AppConstants {

    //Paytm Payment GateWay Constants

    public static final String PAYTM_MID = "Capgem73247037907474";
    public static final String PAYTM_MERCHANT_KEY = "zqzSLuVTvOZRYez5";
    public static final String PAYTM_CHANNEL_ID = "WAP";
    public static final String PAYTM_WEBSITE = "APPSTAGING";
    public static final String PAYTM_INDUSTRY_TYPE_ID = "Retail";
    public static final String PAYTM_CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";

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
