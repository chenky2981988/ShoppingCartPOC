package com.poc.shoppingpos.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.ui.activity.MainActivity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/*
    Remove View Model Logic
 */
public class ScanSearchItemFragment extends BaseFragment {

    //private ScanSearchItemViewModel mSearchItemViewModel;
    public static String TAG = ScanSearchItemFragment.class.getName();
    private final static int REQUEST_CAMERA_PERMISSION = 1;

    //@BindView(R.id.zxing_barcode_scanner)
    private DecoratedBarcodeView barcodeScannerView;


    //private String lastText;
    private BeepManager beepManager;

    public static ScanSearchItemFragment newInstance() {
        return new ScanSearchItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_search_item_fragment, container, false);

        barcodeScannerView = (DecoratedBarcodeView) view.findViewById(R.id.zxing_barcode_scanner);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeScannerView.initializeFromIntent(getBaseActivity().getIntent());
        barcodeScannerView.decodeContinuous(callback);
        beepManager = new BeepManager(getBaseActivity());

        if (!getBaseActivity().hasPermission(Manifest.permission.CAMERA)) {
            getBaseActivity().requestPermissionsSafely(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mSearchItemViewModel = ViewModelProviders.of(this).get(ScanSearchItemViewModel.class);
//        subscribeToViewModel(mSearchItemViewModel.getObservableProduct());
    }

//    private void subscribeToViewModel(LiveData<ProductEntity> liveDatal) {
//
//        // Observe product data
//        liveDatal.observe(this, new Observer<ProductEntity>() {
//            @Override
//            public void onChanged(@Nullable ProductEntity productEntity) {
//                if (productEntity != null) {
//                    Log.d("TAG", "Searched Product : " + productEntity.getProductName());
//                    replaceFragment(ProductDetailsFragment.newInstance(productEntity.getBarcode()
//                    ), true, ProductDetailsFragment.class.getName());
//                } else {
//                    Log.d("TAG", "Product Entity Null");
//                }
//                // mProductDetailsViewModel.setProduct(productEntity);
//            }
//        });
//    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.scan_search_item_fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseActivity().getApplicationContext(), "Camera Permission Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getBaseActivity()).setMenuVisible(false);
        if (barcodeScannerView != null) {
            barcodeScannerView.resume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeScannerView != null) {
            barcodeScannerView.pause();
        }
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            //if (result.getText() == null || result.getText().equals(lastText)) {
            if (result.getText() == null) {
                // Prevent duplicate scans
                Toast.makeText(getBaseActivity().getApplicationContext(), "duplicate scan", Toast.LENGTH_SHORT).show();
                return;
            }

            //lastText = result.getText();
            barcodeScannerView.setStatusText(result.getText());
            Toast.makeText(getBaseActivity().getApplicationContext(), "Scanned: " + result.getText(), Toast.LENGTH_SHORT).show();
            beepManager.playBeepSoundAndVibrate();
            replaceFragment(ProductDetailsFragment.newInstance(result.getText()
            ), true, ProductDetailsFragment.class.getName());
            //mSearchItemViewModel.searchProductByBarcode(lastText);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
}
