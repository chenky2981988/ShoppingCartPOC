package com.poc.shoppingpos.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.models.CartItem;
import com.poc.shoppingpos.models.SalebleProduct;
import com.poc.shoppingpos.utils.CartHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag Sidhiwala on 3/22/2019.
 * chirag.sidhiwala@capgemini.com
 */
public class CartRecycleViewAdapter extends RecyclerView.Adapter<CartItemViewHolder> {

    private Context mContext;
    private List<CartItem> cartItemList;
    public CartRecycleViewAdapter(Context context) {
        this.mContext = context;
        cartItemList = CartHelper.getCartInstance().getCartItems();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_item_layout, null);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);

        holder.itemNameTv.setText(cartItem.getProduct().getProductName());
        holder.itemIdTv.setText(cartItem.getProduct().getBarcode());
        String qtyUnitRate = String.format(mContext.getResources().getString(R.string.qty_x_unit), cartItem.getQuantity(), cartItem.getProduct().getSellingPrice());
        holder.itemQtyRateTv.setText(qtyUnitRate);
        holder.itemWisePriceTv.setText(String.format(mContext.getResources().getString(R.string.item_wise_price),cartItem.getItemTotal()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

}
