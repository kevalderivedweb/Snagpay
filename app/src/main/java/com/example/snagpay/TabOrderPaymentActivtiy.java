package com.example.snagpay;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.snagpay.Fragments.Fragment_OrderHistory;
import com.example.snagpay.Fragments.Fragment_PaymentHistory;

public class TabOrderPaymentActivtiy extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public TabOrderPaymentActivtiy(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_OrderHistory fragmentOrderHistory = new Fragment_OrderHistory();
                return fragmentOrderHistory;
            case 1:
                Fragment_PaymentHistory fragmentPaymentHistory = new Fragment_PaymentHistory();
                return fragmentPaymentHistory;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
