package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.R;

public class Activity_CategoriesInnerItems extends AppCompatActivity {

    private RecyclerView resCategoriesInner;
    private AdapterHomeInner resHomeInnerAdapter;
    private ImageView backToCategories;
    private LinearLayout linearFilterSortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_inner_items);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        resCategoriesInner = findViewById(R.id.recCategoriesInner);
        backToCategories = findViewById(R.id.backToCategories);
        linearFilterSortBy = findViewById(R.id.linearFilterSortBy);

        backToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearFilterSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_CategoriesInnerItems.this, Activity_FilterSortBy.class);
                startActivity(intent);
            }
        });

        resCategoriesInner.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
       /* resHomeInnerAdapter = new AdapterHomeInner(Activity_CategoriesInnerItems.this, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                startActivity(new Intent(Activity_CategoriesInnerItems.this, Activity_ProductDetails.class));
            }
        });*/

        resCategoriesInner.setAdapter(resHomeInnerAdapter);

    }
}