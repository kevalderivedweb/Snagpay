package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterMyCart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recMyCart;
    private AdapterMyCart adapterMyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        recMyCart = findViewById(R.id.recMyCart);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recMyCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapterMyCart = new AdapterMyCart(CartActivity.this, new AdapterMyCart.OnItemClickListener() {
            @Override
            public void onItemClickPlus(int item, String s) {

            }

            @Override
            public void onItemClickMinus(int item, String s) {

            }
        });
        recMyCart.setAdapter(adapterMyCart);
    }
}