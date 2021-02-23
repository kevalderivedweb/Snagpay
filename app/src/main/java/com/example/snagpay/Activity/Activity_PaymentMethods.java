package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterPaymentMethods;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_PaymentMethods extends AppCompatActivity {

    private RecyclerView resPaymentCardList;
    private AdapterPaymentMethods adapterPaymentMethods;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_PaymentMethods.this);

        findViewById(R.id.backToFragMyStuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resPaymentCardList = findViewById(R.id.resPaymentCardList);

        resPaymentCardList.setLayoutManager(new LinearLayoutManager(Activity_PaymentMethods.this));
        adapterPaymentMethods = new AdapterPaymentMethods(Activity_PaymentMethods.this, new AdapterPaymentMethods.OnItemClickListener() {
            @Override
            public void onItemClickRadio(int item) {

            }
        });
        resPaymentCardList.setAdapter(adapterPaymentMethods);

        findViewById(R.id.btnAddNewCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Activity_PaymentMethods.this, Activity_AddCards.class));
            }
        });
    }





}