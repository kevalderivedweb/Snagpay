package com.example.snagpay;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.snagpay.Fragments.Fragment_HomeInner;
import com.example.snagpay.Fragments.Fragment_Map;

public class Activity_HomeInner extends AppCompatActivity {

    private ImageView backToHome;
    private LinearLayout linearFilterSortBy;
    private LinearLayout openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_inner);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        backToHome = findViewById(R.id.backToHome);
        linearFilterSortBy = findViewById(R.id.linearFilterSortBy);
        openMap = findViewById(R.id.openMap);

        Fragment_HomeInner homeInnerFragment = new Fragment_HomeInner();
        replaceFragment(R.id.fragHomeMap, homeInnerFragment, "Inner");

        linearFilterSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_HomeInner.this, Activity_FilterSortBy.class));
            }
        });

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.openMap).setBackgroundResource(R.drawable.list_view);

                Fragment_Map mapFragment = new Fragment_Map();
                replaceFragment(R.id.fragHomeMap, mapFragment, "MapFrag");
            }
        });

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }
}