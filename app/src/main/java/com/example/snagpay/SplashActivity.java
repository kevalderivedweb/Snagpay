package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.snagpay.API.UserSession;

public class SplashActivity extends AppCompatActivity {

    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new UserSession(SplashActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (session.isLoggedIn()){
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 2000);

        }
        else {
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(getApplicationContext(), SelectCityActivity.class));
                    finish();
                }
            }, secondsDelayed * 2000);
        }
    }
}