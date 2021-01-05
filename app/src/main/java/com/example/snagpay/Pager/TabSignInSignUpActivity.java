package com.example.snagpay.Pager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.snagpay.Fragments.Fragment_SignIn;
import com.example.snagpay.Fragments.Fragment_SignUp;

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
                Fragment_SignIn signInActivity = new Fragment_SignIn();
                return signInActivity;
            case 1:
                Fragment_SignUp signUpActivity = new Fragment_SignUp();
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
