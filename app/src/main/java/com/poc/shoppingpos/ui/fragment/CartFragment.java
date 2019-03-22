package com.poc.shoppingpos.ui.fragment;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseFragment;
import com.poc.shoppingpos.models.Cart;
import com.poc.shoppingpos.ui.activity.MainActivity;
import com.poc.shoppingpos.ui.adapters.CartRecycleViewAdapter;
import com.poc.shoppingpos.utils.CartHelper;

public class CartFragment extends BaseFragment {

    private CartViewModel mViewModel;

    @BindView(R.id.cart_recycle_view)
    RecyclerView cartRecycleView;
    @BindView(R.id.cart_total_amount_tv)
    AppCompatTextView cartTotalAmountTv;
    @BindView(R.id.proceed_to_pay_btn)
    AppCompatButton proceedToPayBtn;
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
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getBaseActivity()).setMenuVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        if(CartHelper.getCartInstance().getTotalQuantity() > 0) {
            emptyCartTextView.setVisibility(View.GONE);
            cartRecycleView.setVisibility(View.VISIBLE);
            cartTotalAmountTv.setVisibility(View.VISIBLE);
            proceedToPayBtn.setVisibility(View.VISIBLE);

            cartRecycleView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            mCartRecycleViewAdapter = new CartRecycleViewAdapter(getBaseActivity().getApplicationContext());
            cartRecycleView.setAdapter(mCartRecycleViewAdapter);
            cartTotalAmountTv.setText(String.format(getString(R.string.grand_total), CartHelper.getCartInstance().getTotalPrice()));
        }else {
            cartRecycleView.setVisibility(View.GONE);
            cartTotalAmountTv.setVisibility(View.GONE);
            proceedToPayBtn.setVisibility(View.GONE);
            emptyCartTextView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.proceed_to_pay_btn)
    public void actionProceedToPay(){
        Log.d("TAG","Proceed To Pay Clicked");
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
