package com.poc.shoppingpos.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.db.entity.ProductEntity;
import com.poc.shoppingpos.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Chirag Sidhiwala on 3/26/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class CustomAutoCompleteTextViewAdapter extends ArrayAdapter<ProductEntity> {

    private Context mContext;
    private List<ProductEntity> searchedProductList;
    private int resourceId;

    public CustomAutoCompleteTextViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.resourceId = resource;
        this.searchedProductList = new ArrayList<ProductEntity>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            if (convertView == null) {
                // inflate the layout
                LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
                convertView = inflater.inflate(resourceId, parent, false);
            }
            ProductEntity productEntity = searchedProductList.get(position);
            AppCompatTextView productBarcodeTv = (AppCompatTextView) convertView.findViewById(R.id.product_barcode_tv);
            AppCompatTextView productNameTv = (AppCompatTextView) convertView.findViewById(R.id.product_name_tv);

            productBarcodeTv.setText(productEntity.getBarcode());
            productNameTv.setText(productEntity.getProductName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return searchedProductList.size();
    }

    @Nullable
    @Override
    public ProductEntity getItem(int position) {
        Log.d("TAG","List Size in getItem : " + searchedProductList.size());
        return searchedProductList.get(position);
    }

    public void updateSearchList(List<ProductEntity> productEntityList){
        this.clear();
        this.searchedProductList = productEntityList;
        notifyDataSetChanged();
    }
}
