package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.AdapterMyCart;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_Cart extends AppCompatActivity {

    private RecyclerView recMyCart;
    private AdapterMyCart adapterMyCart;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_Cart.this);

        recMyCart = findViewById(R.id.recMyCart);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recMyCart.setLayoutManager(new LinearLayoutManager(Activity_Cart.this));
        adapterMyCart = new AdapterMyCart(Activity_Cart.this, new AdapterMyCart.OnItemClickListener() {
            @Override
            public void onItemClickPlus(int item, String s) {

            }

            @Override
            public void onItemClickMinus(int item, String s) {

            }
        });
        recMyCart.setAdapter(adapterMyCart);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}