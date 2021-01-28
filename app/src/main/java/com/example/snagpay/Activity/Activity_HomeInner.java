package com.example.snagpay.Activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.snagpay.Fragments.Fragment_HomeInner;
import com.example.snagpay.Fragments.Fragment_Map;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_HomeInner extends AppCompatActivity {

    private ImageView backToHome;

    private LinearLayout openMap;
    private String category_id;
    private String subCategoryId = "";
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_inner);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);  //  set status text dark
        session = new UserSession(Activity_HomeInner.this);

        backToHome = findViewById(R.id.backToHome);
        openMap = findViewById(R.id.openMap);

        category_id = getIntent().getStringExtra("category_id");
        subCategoryId = getIntent().getStringExtra("subCategoryId");

        Fragment_HomeInner homeInnerFragment = new Fragment_HomeInner(category_id, subCategoryId);
        replaceFragment(R.id.fragHomeMap, homeInnerFragment, "Inner");

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
                .commit();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}