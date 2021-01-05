package com.example.snagpay.Activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snagpay.Fragments.Fragment_CategoriesActivity;
import com.example.snagpay.Fragments.Fragment_HomeActivity;
import com.example.snagpay.Fragments.Fragment_MyStuffActivity;
import com.example.snagpay.Fragments.Fragment_NotificationsActivity;
import com.example.snagpay.Fragments.Fragment_WishListAcivity;
import com.example.snagpay.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navLinear1, navLinear2, navLinear3, navLinear4, navLinear5;
    private ImageView navBottomImageHome, navBottomImageCategories, navBottomImageNotifications, navBottomImageWishList, navBottomImageMyStuff;
    private TextView navBottomTxtHome, navBottomTxtCategories, navBottomTxtNotifications, navBottomTxtWishList, navBottomTxtMyStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        navLinear1 = findViewById(R.id.navLinear1);
        navLinear2 = findViewById(R.id.navLinear2);
        navLinear3 = findViewById(R.id.navLinear3);
        navLinear4 = findViewById(R.id.navLinear4);
        navLinear5 = findViewById(R.id.navLinear5);
        navBottomImageHome = findViewById(R.id.navBottomImageHome);
        navBottomImageCategories = findViewById(R.id.navBottomImageCategories);
        navBottomImageNotifications = findViewById(R.id.navBottomImageNotifications);
        navBottomImageWishList = findViewById(R.id.navBottomImageWishList);
        navBottomImageMyStuff = findViewById(R.id.navBottomImageMyStuff);
        navBottomTxtHome = findViewById(R.id.navBottomTxtHome);
        navBottomTxtCategories = findViewById(R.id.navBottomTxtCategories);
        navBottomTxtNotifications = findViewById(R.id.navBottomTxtNotifications);
        navBottomTxtWishList = findViewById(R.id.navBottomTxtWishList);
        navBottomTxtMyStuff = findViewById(R.id.navBottomTxtMyStuff);

        Fragment_HomeActivity fragmentHomeActivity = new Fragment_HomeActivity();
        replaceFragment(R.id.fragmentLinearHome, fragmentHomeActivity, "Home");

        navLinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_HomeActivity fragmentHomeActivity = new Fragment_HomeActivity();
                replaceFragment(R.id.fragmentLinearHome, fragmentHomeActivity, "Home");

                navBottomImageHome.setImageResource(R.drawable.bottom_nav_home_active);
                navBottomImageCategories.setImageResource(R.drawable.bottom_nav_category);
                navBottomImageNotifications.setImageResource(R.drawable.bottom_nav_notification);
                navBottomImageWishList.setImageResource(R.drawable.bottom_nav_wishlist);
                navBottomImageMyStuff.setImageResource(R.drawable.bottom_nav_profile);

                navBottomTxtHome.setTextColor(getResources().getColor(R.color.blue));
                navBottomTxtCategories.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtNotifications.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtWishList.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtMyStuff.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        navLinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_CategoriesActivity fragmentCategoriesActivity = new Fragment_CategoriesActivity();
                replaceFragment(R.id.fragmentLinearHome, fragmentCategoriesActivity, "Categories");

                navBottomImageHome.setImageResource(R.drawable.bottom_nav_home);
                navBottomImageCategories.setImageResource(R.drawable.bottom_nav_category_active);
                navBottomImageNotifications.setImageResource(R.drawable.bottom_nav_notification);
                navBottomImageWishList.setImageResource(R.drawable.bottom_nav_wishlist);
                navBottomImageMyStuff.setImageResource(R.drawable.bottom_nav_profile);

                navBottomTxtHome.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtCategories.setTextColor(getResources().getColor(R.color.blue));
                navBottomTxtNotifications.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtWishList.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtMyStuff.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        navLinear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_NotificationsActivity fragmentNotificationsActivity = new Fragment_NotificationsActivity();
                replaceFragment(R.id.fragmentLinearHome, fragmentNotificationsActivity, "Notifications");

                navBottomImageHome.setImageResource(R.drawable.bottom_nav_home);
                navBottomImageCategories.setImageResource(R.drawable.bottom_nav_category);
                navBottomImageNotifications.setImageResource(R.drawable.bottom_nav_notification_active);
                navBottomImageWishList.setImageResource(R.drawable.bottom_nav_wishlist);
                navBottomImageMyStuff.setImageResource(R.drawable.bottom_nav_profile);

                navBottomTxtHome.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtCategories.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtNotifications.setTextColor(getResources().getColor(R.color.blue));
                navBottomTxtWishList.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtMyStuff.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        navLinear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_WishListAcivity fragmentWishListAcivity = new Fragment_WishListAcivity();
                replaceFragment(R.id.fragmentLinearHome, fragmentWishListAcivity, "WishList");

                navBottomImageHome.setImageResource(R.drawable.bottom_nav_home);
                navBottomImageCategories.setImageResource(R.drawable.bottom_nav_category);
                navBottomImageNotifications.setImageResource(R.drawable.bottom_nav_notification);
                navBottomImageWishList.setImageResource(R.drawable.bottom_nav_wishlist_active);
                navBottomImageMyStuff.setImageResource(R.drawable.bottom_nav_profile);

                navBottomTxtHome.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtCategories.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtNotifications.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtWishList.setTextColor(getResources().getColor(R.color.blue));
                navBottomTxtMyStuff.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        navLinear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_MyStuffActivity fragmentMyStuffActivity = new Fragment_MyStuffActivity();
                replaceFragment(R.id.fragmentLinearHome, fragmentMyStuffActivity, "MyStuff");

                navBottomImageHome.setImageResource(R.drawable.bottom_nav_home);
                navBottomImageCategories.setImageResource(R.drawable.bottom_nav_category);
                navBottomImageNotifications.setImageResource(R.drawable.bottom_nav_notification);
                navBottomImageWishList.setImageResource(R.drawable.bottom_nav_wishlist);
                navBottomImageMyStuff.setImageResource(R.drawable.bottom_nav_profile_active);

                navBottomTxtHome.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtCategories.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtNotifications.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtWishList.setTextColor(getResources().getColor(R.color.gray));
                navBottomTxtMyStuff.setTextColor(getResources().getColor(R.color.blue));
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