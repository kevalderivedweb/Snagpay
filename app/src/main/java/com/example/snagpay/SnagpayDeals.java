package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterHomeGrid;
import com.example.snagpay.Adapter.AdapterHomeInner;

public class SnagpayDeals extends AppCompatActivity {

    private RecyclerView recDeals;
    private AdapterHomeInner adapterHomeInner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_deals);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        recDeals = findViewById(R.id.recDeals);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recDeals.setLayoutManager(new GridLayoutManager(SnagpayDeals.this, 2));
        adapterHomeInner = new AdapterHomeInner(SnagpayDeals.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });
        recDeals.setAdapter(adapterHomeInner);
    }
}