package com.poc.shoppingpos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.poc.shoppingpos.R;
import com.poc.shoppingpos.base.BaseActivity;
import com.poc.shoppingpos.utils.NetworkUtils;

public class SplashActivity extends BaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        hideKeyboard();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Network not available", Toast.LENGTH_SHORT).show();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
