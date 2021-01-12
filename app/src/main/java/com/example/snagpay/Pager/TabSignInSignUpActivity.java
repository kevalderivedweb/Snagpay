package com.example.snagpay.Pager;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.snagpay.Fragments.Fragment_SignIn;
import com.example.snagpay.Fragments.Fragment_SignUp;

public class TabSignInSignUpActivity extends FragmentPagerAdapter {

    private Context myContext;
    private String mCityId;
    private int totalTabs;

    public TabSignInSignUpActivity(Context context, FragmentManager fm, int totalTabs,String CityId) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.mCityId = CityId;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_SignIn signInActivity = new Fragment_SignIn(myContext);
                return signInActivity;
            case 1:
                Fragment_SignUp signUpActivity = new Fragment_SignUp(myContext);
                Bundle bundle = new Bundle();
                bundle.putString("city_id", mCityId);
                signUpActivity.setArguments(bundle);
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
