package com.poc.shoppingpos.ui.adapters;

import android.view.View;

import com.poc.shoppingpos.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chirag Sidhiwala on 3/22/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class CartItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_name_tv)
    AppCompatTextView itemNameTv;
    @BindView(R.id.item_id_tv)
    AppCompatTextView itemIdTv;
    @BindView(R.id.item_qty_unit_rate_tv)
    AppCompatTextView itemQtyRateTv;
    @BindView(R.id.item_wise_price)
    AppCompatTextView itemWisePriceTv;

    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }


}
