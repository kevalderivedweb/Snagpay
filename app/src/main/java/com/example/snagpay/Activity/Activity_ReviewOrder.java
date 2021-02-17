package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_ReviewOrder extends AppCompatActivity {

    private Button btnCompletePurchase;
    private ImageView backToProductDetail;
    private RelativeLayout lyoutForPromo;
    private View viewPromo;

    private EditText editPromo;
    private ImageView orderMinus, orderPlus;
    private TextView txtOrderCount, checkPromoCode, promoCode;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ReviewOrder.this);

        btnCompletePurchase = findViewById(R.id.btnCompletePurchase);
        backToProductDetail = findViewById(R.id.backToProductDetail);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        orderMinus = findViewById(R.id.orderMinus);
        orderPlus = findViewById(R.id.orderPlus);

        lyoutForPromo = findViewById(R.id.lyoutForPromo);
        viewPromo = findViewById(R.id.viewPromo);
        editPromo = findViewById(R.id.editPromo);
        checkPromoCode = findViewById(R.id.checkPromoCode);
        promoCode = findViewById(R.id.promoCode);

        backToProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        promoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoCode.setVisibility(View.GONE);
                viewPromo.setVisibility(View.GONE);
                lyoutForPromo.setVisibility(View.VISIBLE);
            }
        });

        checkPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editPromo.setText("");
            }
        });


        btnCompletePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ReviewOrder.this, Activity_AddCards.class);
                intent.putExtra("let", 11);
                startActivity(intent);
            }
        });

        orderMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = Integer.parseInt(txtOrderCount.getText().toString());
                if (order > 0) {
                    txtOrderCount.setText(String.valueOf(order - 1));
                }
            }
        });

        orderPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = Integer.parseInt(txtOrderCount.getText().toString());
                txtOrderCount.setText(String.valueOf(order + 1));
            }
        });


    }

    public void getReviewOrderDetail(){

    }

}