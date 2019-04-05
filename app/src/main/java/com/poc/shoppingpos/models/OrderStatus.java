package com.poc.shoppingpos.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chirag Sidhiwala on 4/5/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class OrderStatus implements Parcelable {

    private String STATUS;
    private String CHECKSUMHASH;
    private double TXNAMOUNT;
    private String BANKNAME;
    private String ORDERID;
    private String TXNDATE;
    private String MID;
    private String TXNID;
    private String RESPCODE;
    private String PAYMENTMODE;
    private String BANKTXNID;
    private String CURRENCY;
    private String GATEWAYNAME;
    private String RESPMSG;

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCHECKSUMHASH() {
        return CHECKSUMHASH;
    }

    public void setCHECKSUMHASH(String CHECKSUMHASH) {
        this.CHECKSUMHASH = CHECKSUMHASH;
    }

    public double getTXNAMOUNT() {
        return TXNAMOUNT;
    }

    public void setTXNAMOUNT(double TXNAMOUNT) {
        this.TXNAMOUNT = TXNAMOUNT;
    }

    public String getBANKNAME() {
        return BANKNAME;
    }

    public void setBANKNAME(String BANKNAME) {
        this.BANKNAME = BANKNAME;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getTXNDATE() {
        return TXNDATE;
    }

    public void setTXNDATE(String TXNDATE) {
        this.TXNDATE = TXNDATE;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }

    public String getRESPCODE() {
        return RESPCODE;
    }

    public void setRESPCODE(String RESPCODE) {
        this.RESPCODE = RESPCODE;
    }

    public String getPAYMENTMODE() {
        return PAYMENTMODE;
    }

    public void setPAYMENTMODE(String PAYMENTMODE) {
        this.PAYMENTMODE = PAYMENTMODE;
    }

    public String getBANKTXNID() {
        return BANKTXNID;
    }

    public void setBANKTXNID(String BANKTXNID) {
        this.BANKTXNID = BANKTXNID;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getGATEWAYNAME() {
        return GATEWAYNAME;
    }

    public void setGATEWAYNAME(String GATEWAYNAME) {
        this.GATEWAYNAME = GATEWAYNAME;
    }

    public String getRESPMSG() {
        return RESPMSG;
    }

    public void setRESPMSG(String RESPMSG) {
        this.RESPMSG = RESPMSG;
    }

    protected OrderStatus(Parcel in) {
        STATUS = in.readString();
        CHECKSUMHASH = in.readString();
        TXNAMOUNT = in.readDouble();
        BANKNAME = in.readString();
        ORDERID = in.readString();
        TXNDATE = in.readString();
        MID = in.readString();
        TXNID = in.readString();
        RESPCODE = in.readString();
        PAYMENTMODE = in.readString();
        BANKTXNID = in.readString();
        CURRENCY = in.readString();
        GATEWAYNAME = in.readString();
        RESPMSG = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(STATUS);
        dest.writeString(CHECKSUMHASH);
        dest.writeDouble(TXNAMOUNT);
        dest.writeString(BANKNAME);
        dest.writeString(ORDERID);
        dest.writeString(TXNDATE);
        dest.writeString(MID);
        dest.writeString(TXNID);
        dest.writeString(RESPCODE);
        dest.writeString(PAYMENTMODE);
        dest.writeString(BANKTXNID);
        dest.writeString(CURRENCY);
        dest.writeString(GATEWAYNAME);
        dest.writeString(RESPMSG);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OrderStatus> CREATOR = new Parcelable.Creator<OrderStatus>() {
        @Override
        public OrderStatus createFromParcel(Parcel in) {
            return new OrderStatus(in);
        }

        @Override
        public OrderStatus[] newArray(int size) {
            return new OrderStatus[size];
        }
    };
}
