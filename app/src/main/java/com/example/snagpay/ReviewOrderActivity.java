package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewOrderActivity extends AppCompatActivity {

    private Button btnCompletePurchase;
    private ImageView backToProductDetail;

    private ImageView orderMinus, orderPlus;
    private TextView txtOrderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        btnCompletePurchase = findViewById(R.id.btnCompletePurchase);
        backToProductDetail = findViewById(R.id.backToProductDetail);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        orderMinus = findViewById(R.id.orderMinus);
        orderPlus = findViewById(R.id.orderPlus);

        backToProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCompletePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewOrderActivity.this, AddCardsActivity.class);
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
}