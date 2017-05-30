package com.ratings.doctors.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.ratings.doctors.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SPLASH_TIME_OUT = 0;

        if (mAuth.getCurrentUser() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onAuthSuccess(mAuth.getCurrentUser());
                }
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivity(MainActivity.class);
                }
            },1000);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
