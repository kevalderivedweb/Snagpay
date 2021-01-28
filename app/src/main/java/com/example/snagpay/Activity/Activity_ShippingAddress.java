package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.snagpay.Adapter.AdapterShippingAddress;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_ShippingAddress extends AppCompatActivity {

    private RecyclerView resShippingAddress;
    private AdapterShippingAddress adapterShippingAddress;

    private LinearLayout btnAddNewAddress;
    private Button btnShippingContinue;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ShippingAddress.this);

        resShippingAddress = findViewById(R.id.resShippingAddress);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        btnShippingContinue = findViewById(R.id.btnShippingContinue);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resShippingAddress.setLayoutManager(new LinearLayoutManager(this));
        adapterShippingAddress = new AdapterShippingAddress(Activity_ShippingAddress.this);
        resShippingAddress.setAdapter(adapterShippingAddress);

        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ShippingAddress.this, Activity_AddShippingAddress.class);
                startActivity(intent);
            }
        });

        btnShippingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newString = 0;
                if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if(extras == null) {
                        newString = 0;
                    } else {
                        newString = extras.getInt("value");
                    }
                }

                if (newString == 1) {
                    finish();
                }

                if (newString == 2) {
                    Intent intent = new Intent(Activity_ShippingAddress.this, Activity_ThankYou.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

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