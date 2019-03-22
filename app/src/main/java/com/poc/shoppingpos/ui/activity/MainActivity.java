package com.poc.shoppingpos.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseActivity;
import com.poc.shoppingpos.ui.fragment.CartFragment;
import com.poc.shoppingpos.ui.fragment.HomeFragment;
import com.poc.shoppingpos.utils.CartHelper;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AppCompatTextView cartItemCountTextView;
    private boolean isMenuVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Add project list fragment if this is first creation
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_frame_layout, HomeFragment.newInstance(), HomeFragment.TAG).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem itemData = menu.findItem(R.id.action_addcart);
//        CartCounterActionView actionView = (CartCounterActionView) itemData.getActionView();
//        actionView.setItemData(menu, itemData);
//        actionView.setCount(CartHelper.getCartInstance().getTotalQuantity());

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        cartItemCountTextView = (AppCompatTextView) actionView.findViewById(R.id.cart_badge);

        setupBadge(menuItem);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
//        actionView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                CartHelper.getCartInstance().clear();
//                invalidateOptionsMenu();
//                return true;
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_cart: {
                Log.d("TAG", "On Cart Selected");
                replaceFragment(CartFragment.newInstance(), true, CartFragment.class.getName());
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge(MenuItem menuItem) {

        if (isMenuVisible) {
            menuItem.setVisible(true);
            if (cartItemCountTextView != null) {
                int mCartItemCount = CartHelper.getCartInstance().getTotalQuantity();
                if (mCartItemCount == 0) {
                    if (cartItemCountTextView.getVisibility() != View.GONE) {
                        cartItemCountTextView.setVisibility(View.GONE);
                    }
                } else {
                    cartItemCountTextView.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                    if (cartItemCountTextView.getVisibility() != View.VISIBLE) {
                        cartItemCountTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        } else {
            menuItem.setVisible(false);
        }
    }

    public void setMenuVisible(boolean menuVisible) {
        if (isMenuVisible != menuVisible) {
            isMenuVisible = menuVisible;
            invalidateOptionsMenu();
        }
    }
}
