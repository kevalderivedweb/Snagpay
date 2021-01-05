package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.snagpay.Adapter.AdapterShippingAddress;

public class ShippingAddressActivity extends AppCompatActivity {

    private RecyclerView resShippingAddress;
    private AdapterShippingAddress adapterShippingAddress;

    private LinearLayout btnAddNewAddress;
    private Button btnShippingContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        resShippingAddress = findViewById(R.id.resShippingAddress);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        btnShippingContinue = findViewById(R.id.btnShippingContinue);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resShippingAddress.setLayoutManager(new LinearLayoutManager(this));
        adapterShippingAddress = new AdapterShippingAddress(ShippingAddressActivity.this);
        resShippingAddress.setAdapter(adapterShippingAddress);

        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShippingAddressActivity.this, Activity_AddShippingAddress.class);
                startActivity(intent);
            }
        });

        btnShippingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newString = 0;
                if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if(extras == null) {
                        newString = 0;
                    } else {
                        newString = extras.getInt("value");
                    }
                }

                if (newString == 1) {
                    finish();
                }

                if (newString == 2) {
                    Intent intent = new Intent(ShippingAddressActivity.this, ThankYouActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });
    }
}