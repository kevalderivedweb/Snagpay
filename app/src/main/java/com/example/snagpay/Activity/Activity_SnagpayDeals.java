package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_SnagpayDeals extends AppCompatActivity {

    private RecyclerView recDeals;
    private AdapterHomeInner adapterHomeInner;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_deals);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_SnagpayDeals.this);

        recDeals = findViewById(R.id.recDeals);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recDeals.setLayoutManager(new GridLayoutManager(Activity_SnagpayDeals.this, 2));
        /*adapterHomeInner = new AdapterHomeInner(Activity_SnagpayDeals.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });*/
        recDeals.setAdapter(adapterHomeInner);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}