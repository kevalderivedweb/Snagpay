package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_SnagpayGuideItems extends AppCompatActivity {

    private RecyclerView resGuideItems;
    private AdapterHomeInner adapterHomeInner;
    private LinearLayout linrSnagPayItems;
    private TextView txtSnagpayItems;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_guide_items);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_SnagpayGuideItems.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resGuideItems = findViewById(R.id.resGuideItems);
        linrSnagPayItems = findViewById(R.id.linrSnagPayItems);
        txtSnagpayItems = findViewById(R.id.txtSnagpayItems);

        resGuideItems.setLayoutManager(new GridLayoutManager(Activity_SnagpayGuideItems.this, 2));
        /*adapterHomeInner = new AdapterHomeInner(Activity_SnagpayGuideItems.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });*/

        resGuideItems.setAdapter(adapterHomeInner);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String headerName = extras.getString("header");
        String posStr = extras.getString("poss");
        int pos = Integer.parseInt(posStr);

        txtSnagpayItems.setText(headerName);

        if (pos == 3){
            linrSnagPayItems.setVisibility(View.VISIBLE);
        }
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