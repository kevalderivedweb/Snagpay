package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_WorkwithSnagpay extends AppCompatActivity {

    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workwith_snagpay);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        session = new UserSession(Activity_WorkwithSnagpay.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.learnSnagpay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_WorkwithSnagpay.this, Activity_LearnMerchant.class));
            }
        });

        findViewById(R.id.vendorCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_WorkwithSnagpay.this, Activity_VendorCodeProduct.class));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}