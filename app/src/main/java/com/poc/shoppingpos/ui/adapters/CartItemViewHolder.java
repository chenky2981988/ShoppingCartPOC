package com.poc.shoppingpos.ui.adapters;

import android.view.View;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.ui.interfaces.RecyclerViewButtonClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.remove_item_btn)
    AppCompatImageButton itemRemoveButton;
    RecyclerViewButtonClickListener mListener;

    public CartItemViewHolder(@NonNull View itemView, RecyclerViewButtonClickListener listener) {
        super(itemView);
        this.mListener = listener;
        ButterKnife.bind(this,itemView);
    }

    @OnClick(R.id.remove_item_btn)
    public void onRemoveBtnClick(){
        mListener.onRemoveBtnClick(getAdapterPosition());
    }


}
