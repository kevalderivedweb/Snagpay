package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Pager.TabSignInSignUpActivity;
import com.google.android.material.tabs.TabLayout;

public class SignInSignUpActivity extends AppCompatActivity {

    private TabLayout tabLayoutSignInUp;
    private ViewPager viewPagerSignInUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        tabLayoutSignInUp=(TabLayout)findViewById(R.id.tabLayoutSignInUp);
        viewPagerSignInUp=(ViewPager)findViewById(R.id.viewPagerSignInUp);

        tabLayoutSignInUp.setSelectedTabIndicatorColor(Color.parseColor("#3c5bc3"));
        tabLayoutSignInUp.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
        tabLayoutSignInUp.setTabTextColors(Color.parseColor("#6d6d6d"), Color.parseColor("#3c5bc3"));

        tabLayoutSignInUp.addTab(tabLayoutSignInUp.newTab().setText("Sign In"));
        tabLayoutSignInUp.addTab(tabLayoutSignInUp.newTab().setText("Sign Up"));

        tabLayoutSignInUp.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabSignInSignUpActivity pagerTabActivity = new TabSignInSignUpActivity(this,getSupportFragmentManager(), tabLayoutSignInUp.getTabCount());
        viewPagerSignInUp.setAdapter(pagerTabActivity);

        viewPagerSignInUp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutSignInUp));

        tabLayoutSignInUp.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerSignInUp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
/*
        TextView m_signUpTxt = findViewById(R.id.m_sign_up_txt);
        TextView m_signInTxt = findViewById(R.id.m_sign_in_txt);
        LinearLayout m_sign_in_color = findViewById(R.id.m_sign_in_color);
        LinearLayout m_sign_up_color = findViewById(R.id.m_sign_up_color);

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_signUpTxt.setTextColor(getResources().getColor(R.color.black));
                m_signInTxt.setTextColor(getResources().getColor(R.color.purple_700));
                m_sign_in_color.setBackgroundColor(getResources().getColor(R.color.purple_700));
                m_sign_up_color.setBackgroundColor(getResources().getColor(R.color.black));
            }
        });


        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_signUpTxt.setTextColor(getResources().getColor(R.color.purple_700));
                m_signInTxt.setTextColor(getResources().getColor(R.color.black));
                m_sign_in_color.setBackgroundColor(getResources().getColor(R.color.black));
                m_sign_up_color.setBackgroundColor(getResources().getColor(R.color.purple_700));
            }
        }); */
    }


}