package com.example.snagpay.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.snagpay.Pager.TabOrderPaymentActivtiy;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.google.android.material.tabs.TabLayout;

public class Activity_MyPurchases extends AppCompatActivity {

    private TabLayout tabLayoutOrderPayment;
    private ViewPager viewPagerOrderPayment;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_MyPurchases.this);

        tabLayoutOrderPayment=(TabLayout)findViewById(R.id.tabLayoutOrderPayment);
        viewPagerOrderPayment=(ViewPager)findViewById(R.id.viewPagerOrderPayment);

        findViewById(R.id.backToFragMyStuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayoutOrderPayment.setSelectedTabIndicatorColor(Color.parseColor("#A7A7A7"));
        tabLayoutOrderPayment.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
        tabLayoutOrderPayment.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#353333"));

        tabLayoutOrderPayment.addTab(tabLayoutOrderPayment.newTab().setText("Order History"));
        tabLayoutOrderPayment.addTab(tabLayoutOrderPayment.newTab().setText("Payment History"));

        tabLayoutOrderPayment.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabOrderPaymentActivtiy tabOrderPaymentActivtiy = new TabOrderPaymentActivtiy(this,getSupportFragmentManager(), tabLayoutOrderPayment.getTabCount());
        viewPagerOrderPayment.setAdapter(tabOrderPaymentActivtiy);

        viewPagerOrderPayment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutOrderPayment));

        tabLayoutOrderPayment.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerOrderPayment.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}