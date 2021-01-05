package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterPaymentMethods;

public class PaymentMethodsActivity extends AppCompatActivity {

    private RecyclerView resPaymentCardList;
    private AdapterPaymentMethods adapterPaymentMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToFragMyStuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resPaymentCardList = findViewById(R.id.resPaymentCardList);

        resPaymentCardList.setLayoutManager(new LinearLayoutManager(PaymentMethodsActivity.this));
        adapterPaymentMethods = new AdapterPaymentMethods(PaymentMethodsActivity.this);
        resPaymentCardList.setAdapter(adapterPaymentMethods);

        findViewById(R.id.btnAddNewCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(PaymentMethodsActivity.this, AddCardsActivity.class));
            }
        });
    }
}