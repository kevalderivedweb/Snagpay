package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_Splash extends AppCompatActivity {

    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));  // for navigation color

        session = new UserSession(Activity_Splash.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (session.isLoggedIn()){
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Activity_Splash.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 2000);

        }
        else {
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Activity_SelectCity.class));
                    finish();
                }
            }, secondsDelayed * 2000);
        }
    }
}