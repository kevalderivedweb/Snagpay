package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.R;

public class Activity_IncredibleDeals extends AppCompatActivity {

    private String[] dealsName = {"Everyday Gifts", "End of the Week Specials", "Gifts of Moms", "Things to Do"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incredible_deals);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.everydayDeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_IncredibleDeals.this, Activity_IncredibleDealsInner.class);
                Bundle extras = new Bundle();
                extras.putString("naam",dealsName[0]);
                extras.putString("val", String.valueOf(0));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        findViewById(R.id.weekSpecialDeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_IncredibleDeals.this, Activity_IncredibleDealsInner.class);
                Bundle extras = new Bundle();
                extras.putString("naam",dealsName[1]);
                extras.putString("val", String.valueOf(1));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        findViewById(R.id.momsGifts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_IncredibleDeals.this, Activity_IncredibleDealsInner.class);
                Bundle extras = new Bundle();
                extras.putString("naam",dealsName[2]);
                extras.putString("val", String.valueOf(2));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        findViewById(R.id.thingsToDoDeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_IncredibleDeals.this, Activity_IncredibleDealsInner.class);
                Bundle extras = new Bundle();
                extras.putString("naam",dealsName[3]);
                extras.putString("val", String.valueOf(3));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}