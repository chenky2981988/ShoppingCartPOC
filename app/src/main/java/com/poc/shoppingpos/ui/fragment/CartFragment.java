package com.poc.shoppingpos.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.adapters.CartRecycleViewAdapter;
import com.poc.shoppingpos.ui.interfaces.RecyclerViewButtonClickListener;
import com.poc.shoppingpos.ui.viewmodel.CartViewModel;
import com.poc.shoppingpos.utils.AppConstants;
import com.poc.shoppingpos.utils.CartHelper;

import org.conscrypt.Conscrypt;

import java.security.Security;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends BaseFragment implements RecyclerViewButtonClickListener {

    private CartViewModel mViewModel;

    @BindView(R.id.cart_recycle_view)
    RecyclerView cartRecycleView;
    @BindView(R.id.cart_total_amount_tv)
    AppCompatTextView cartTotalAmountTv;
    @BindView(R.id.order_confirmation_btn)
    AppCompatButton orderConfirmationBtn;
    @BindView(R.id.empty_cart_tv)
    AppCompatTextView emptyCartTextView;
    private CartRecycleViewAdapter mCartRecycleViewAdapter;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getBaseActivity()).setMenuVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        if (mViewModel.getTotalCartQuantity() > 0) {
            emptyCartTextView.setVisibility(View.GONE);
            cartRecycleView.setVisibility(View.VISIBLE);
            cartTotalAmountTv.setVisibility(View.VISIBLE);
            orderConfirmationBtn.setVisibility(View.VISIBLE);

            cartRecycleView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            mCartRecycleViewAdapter = new CartRecycleViewAdapter(getBaseActivity().getApplicationContext(),this, mViewModel.getCartItems());
            cartRecycleView.setAdapter(mCartRecycleViewAdapter);
            cartTotalAmountTv.setText(String.format(getString(R.string.grand_total), mViewModel.getCartTotalAmount()));
        } else {
            cartRecycleView.setVisibility(View.GONE);
            cartTotalAmountTv.setVisibility(View.GONE);
            orderConfirmationBtn.setVisibility(View.GONE);
            emptyCartTextView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.order_confirmation_btn)
    public void actionProceedToPay() {
        Log.d("TAG", "Proceed To Confirm Order Clicked");
        replaceFragment(OrderConfirmationFragment.newInstance(
        ), true, OrderConfirmationFragment.class.getName());

    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }


    @Override
    public void onRemoveBtnClick(int position) {
        Log.d("TAG","Recycle View Item clicked at position : " + position);
        mViewModel.removeItemFromCart(position);
        mCartRecycleViewAdapter.updateCart(mViewModel.getCartItems());
        cartTotalAmountTv.setText(String.format(getString(R.string.grand_total), mViewModel.getCartTotalAmount()));
    }
}
