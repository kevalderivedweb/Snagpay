package com.example.snagpay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabSignInSignUpActivity extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public TabSignInSignUpActivity(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SignInActivity signInActivity = new SignInActivity();
                return signInActivity;
            case 1:
                SignUpActivity signUpActivity = new SignUpActivity();
                return signUpActivity;

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
