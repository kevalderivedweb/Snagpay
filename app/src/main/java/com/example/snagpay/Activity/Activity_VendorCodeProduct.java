package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterVendorLanguages;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

import java.util.ArrayList;

public class Activity_VendorCodeProduct extends AppCompatActivity {

    private RecyclerView recVendorLanguages;
    private AdapterVendorLanguages adapterVendorLanguages;
    private ArrayList<String> languages = new ArrayList<>();
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_code_product);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        session = new UserSession(Activity_VendorCodeProduct.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        languages.add("English");
        languages.add("Nederlands");
        languages.add("Francais");
        languages.add("Deutsch");

        recVendorLanguages = findViewById(R.id.recVendorLanguages);

        recVendorLanguages.setLayoutManager(new GridLayoutManager(this,2));
        adapterVendorLanguages = new AdapterVendorLanguages(this, languages);
        recVendorLanguages.setAdapter(adapterVendorLanguages);
    }





}