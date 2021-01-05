package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.snagpay.Adapter.AdapterReviewProductDetails;
import com.example.snagpay.R;

public class Activity_ProductDetails extends AppCompatActivity {

    private Button btnBuyNowProduct;
    private RecyclerView resProductUserReview;

    private AdapterReviewProductDetails adapterReviewProductDetails;
    private ImageView backToHomeInner;
    private ImageView productFavourite;

    private boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        btnBuyNowProduct = findViewById(R.id.btnBuyNowProduct);
        resProductUserReview = findViewById(R.id.resProductUserReview);
        backToHomeInner = findViewById(R.id.backToHomeInner);
        productFavourite = findViewById(R.id.productFavourite);

        backToHomeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.giveAsAGift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ProductDetails.this, Activity_GiveAsGift.class));
            }
        });

        productFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClick) {
                    firstClick = false;
                    productFavourite.setImageResource(R.drawable.fill_heart);
                }
                else {
                    firstClick = true;
                    productFavourite.setImageResource(R.drawable.heart);
                }

            }
        });

        resProductUserReview.setLayoutManager(new LinearLayoutManager(this));
        adapterReviewProductDetails = new AdapterReviewProductDetails(Activity_ProductDetails.this);
        resProductUserReview.setAdapter(adapterReviewProductDetails);

        btnBuyNowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ProductDetails.this, Activity_ReviewOrder.class));
            }
        });
    }
}