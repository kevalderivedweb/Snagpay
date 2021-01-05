package com.example.snagpay.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.API.UserSession;
import com.example.snagpay.CartActivity;
import com.example.snagpay.ChangePasswordActivity;
import com.example.snagpay.Company;
import com.example.snagpay.IncredibleDeals;
import com.example.snagpay.ManageMyWishListActivity;
import com.example.snagpay.More;
import com.example.snagpay.MyPurchasesActivity;
import com.example.snagpay.NotificationSettingsActivity;
import com.example.snagpay.PaymentMethodsActivity;
import com.example.snagpay.R;
import com.example.snagpay.SelectCityActivity;
import com.example.snagpay.ShippingAddressActivity;
import com.example.snagpay.SnagpayDeals;
import com.example.snagpay.SnagpayGuide;
import com.example.snagpay.SnagpayWalletActivity;
import com.example.snagpay.WorkwithSnagpay;

public class Fragment_MyStuffActivity extends Fragment {

    private UserSession session;

    public Fragment_MyStuffActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_my_stuff, container, false);

        session = new UserSession(getContext());

        view.findViewById(R.id.lyotDeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SnagpayDeals.class));
            }
        });

        view.findViewById(R.id.lyotMyPurchases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyPurchasesActivity.class));
            }
        });

        view.findViewById(R.id.lyotManageMyWishlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ManageMyWishListActivity.class));
            }
        });


        view.findViewById(R.id.lyotPaymentMethods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PaymentMethodsActivity.class));
            }
        });

        view.findViewById(R.id.lyotShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShippingAddressActivity.class);
                intent.putExtra("value", 1);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.lyotNotificationSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationSettingsActivity.class));
            }
        });

        view.findViewById(R.id.lyotSnagpayWallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SnagpayWalletActivity.class));
            }
        });

        view.findViewById(R.id.btnCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        view.findViewById(R.id.lyotChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });

        view.findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout();

                // for remove notification in notif bar
                NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.cancel(0);

                Intent intent = new Intent(getContext(), SelectCityActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        view.findViewById(R.id.lyotCompany).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Company.class));
            }
        });

        view.findViewById(R.id.lyotSNAGpayGuide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SnagpayGuide.class));
            }
        });

        view.findViewById(R.id.lyotIncredibles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), IncredibleDeals.class));
            }
        });

        view.findViewById(R.id.lyotWorkWith).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WorkwithSnagpay.class));
            }
        });

        view.findViewById(R.id.lyotMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), More.class));
            }
        });

        return view;
    }
}