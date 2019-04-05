package com.poc.shoppingpos.ui.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PaymentSuccessViewModel extends AndroidViewModel {
    public PaymentSuccessViewModel(@NonNull Application application) {
        super(application);
    }

    public Bitmap generateBitMap(String dataString) {
        Bitmap bitmap = null; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(dataString, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
