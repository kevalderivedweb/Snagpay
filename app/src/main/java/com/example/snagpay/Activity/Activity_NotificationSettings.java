package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_NotificationSettings extends AppCompatActivity {

    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_NotificationSettings.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





}