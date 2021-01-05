package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snagpay.Adapter.AdapterHomeInner;

public class SnagpayGuideItems extends AppCompatActivity {

    private RecyclerView resGuideItems;
    private AdapterHomeInner adapterHomeInner;
    private LinearLayout linrSnagPayItems;
    private TextView txtSnagpayItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_guide_items);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resGuideItems = findViewById(R.id.resGuideItems);
        linrSnagPayItems = findViewById(R.id.linrSnagPayItems);
        txtSnagpayItems = findViewById(R.id.txtSnagpayItems);

        resGuideItems.setLayoutManager(new GridLayoutManager(SnagpayGuideItems.this, 2));
        adapterHomeInner = new AdapterHomeInner(SnagpayGuideItems.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });

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
}