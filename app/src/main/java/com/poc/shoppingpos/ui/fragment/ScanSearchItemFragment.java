package com.poc.shoppingpos.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.adapters.CustomAutoCompleteTextViewAdapter;
import com.poc.shoppingpos.ui.viewmodel.ScanSearchItemViewModel;
import com.poc.shoppingpos.utils.CustomAutoCompleteView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


/*
    Remove View Model Logic
 */
public class ScanSearchItemFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    //private ScanSearchItemViewModel mSearchItemViewModel;
    public static String TAG = ScanSearchItemFragment.class.getName();
    private final static int REQUEST_CAMERA_PERMISSION = 1;

    //@BindView(R.id.zxing_barcode_scanner)
    private DecoratedBarcodeView barcodeScannerView;

    private BeepManager beepManager;

    @BindView(R.id.search_item_autocompletetv)
    CustomAutoCompleteView searchItemAutoCompleteTextView;

    private ScanSearchItemViewModel mScanSearchItemViewModel;
    private CustomAutoCompleteTextViewAdapter mCustomAutoCompleteTextViewAdapter;

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

        ButterKnife.bind(this, view);
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
        //searchItemAutoCompleteTextView.setThreshold(3);
        mScanSearchItemViewModel = ViewModelProviders.of(this).get(ScanSearchItemViewModel.class);
        mCustomAutoCompleteTextViewAdapter = new CustomAutoCompleteTextViewAdapter(getBaseActivity(), R.layout.auto_complete_list_item);
        searchItemAutoCompleteTextView.setAdapter(mCustomAutoCompleteTextViewAdapter);
        //searchItemAutoCompleteTextView.setThreshold(2);
        searchItemAutoCompleteTextView.setOnItemClickListener(this);
        subscribeToLiveData(mScanSearchItemViewModel.getObservableProductList());

        searchItemAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mScanSearchItemViewModel.searchProductByBarcode(s.toString());
                }
            }
        });
    }

    private void subscribeToLiveData(LiveData<List<ProductEntity>> liveData) {
        // Observe product data
        liveData.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntityList) {
                if (productEntityList != null) {
                    Log.d("TAG", "Searched Product List: " + productEntityList.size());
                    mCustomAutoCompleteTextViewAdapter.updateSearchList(productEntityList);
                } else {
                    Log.d("TAG", "Product Entity List Null");
                }
            }
        });
    }

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
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCustomAutoCompleteTextViewAdapter != null) {
            ProductEntity mProductEntity = mCustomAutoCompleteTextViewAdapter.getItem(position);
            searchItemAutoCompleteTextView.setText("");
            replaceFragment(ProductDetailsFragment.newInstance(mProductEntity.getBarcode()
            ), true, ProductDetailsFragment.class.getName());
        }
    }
}
