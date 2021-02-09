package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_IncredibleDealsInner extends AppCompatActivity {

    private TextView topHeaderDealsTitle;
    private RecyclerView resDealsIncredibles;
    private AdapterHomeInner adapterHomeInner;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incredible_deals_inner);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_IncredibleDealsInner.this);

        topHeaderDealsTitle = findViewById(R.id.topHeaderDealsTitle);
        resDealsIncredibles = findViewById(R.id.resDealsIncredibles);

        resDealsIncredibles.setLayoutManager(new GridLayoutManager(Activity_IncredibleDealsInner.this, 2));
        /*adapterHomeInner = new AdapterHomeInner(Activity_IncredibleDealsInner.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });*/
        resDealsIncredibles.setAdapter(adapterHomeInner);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String headerName = extras.getString("naam");
        String posStr = extras.getString("val");
        int pos = Integer.parseInt(posStr);

        if (pos == 0) {
            topHeaderDealsTitle.setText(headerName);
        }
        if (pos == 1) {
            topHeaderDealsTitle.setText(headerName);
        }
        if (pos == 2) {
            topHeaderDealsTitle.setText(headerName);
        }
        if (pos == 3) {
            topHeaderDealsTitle.setText(headerName);
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