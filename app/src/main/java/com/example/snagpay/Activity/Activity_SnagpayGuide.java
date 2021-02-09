package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterGuide;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_SnagpayGuide extends AppCompatActivity {

    private RecyclerView recGuide;
    private AdapterGuide adapterGuide;
    private String[] guideItems = {"Food & Drink", "Travel and Leisure", "Event Tickets", "Products and Services", "Advertising and Marketing"};
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_guide);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_SnagpayGuide.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recGuide = findViewById(R.id.recGuide);
        recGuide.setLayoutManager(new LinearLayoutManager(Activity_SnagpayGuide.this));
        adapterGuide = new AdapterGuide(Activity_SnagpayGuide.this, guideItems, new AdapterGuide.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

                Intent intent = new Intent(Activity_SnagpayGuide.this, Activity_SnagpayGuideItems.class);
                Bundle extras = new Bundle();
                extras.putString("header",guideItems[item]);
                extras.putString("poss", String.valueOf(item));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        recGuide.setAdapter(adapterGuide);

    }





}