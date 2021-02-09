package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

import java.util.ArrayList;

public class Activity_Gifting extends AppCompatActivity {

    private RecyclerView recGifting;
    private AdapterHomeInner adapterHomeInner;
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifting);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_Gifting.this);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recGifting = findViewById(R.id.recGifting);

        categoryDetailsModelArrayList = new ArrayList<>();

        recGifting.setLayoutManager(new GridLayoutManager(this, 2));
        adapterHomeInner = new AdapterHomeInner(this, categoryDetailsModelArrayList, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });
        recGifting.setAdapter(adapterHomeInner);

        LinearLayout layout = (LinearLayout)findViewById(R.id.s2);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = layout.getMeasuredWidth();
                int height = layout.getMeasuredHeight();

                ViewGroup.LayoutParams params = findViewById(R.id.s1).getLayoutParams();
                params.height = height - 121;

                findViewById(R.id.s1).setLayoutParams(params);
            }
        });

    }





}