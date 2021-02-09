package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Fragment_PaymentHistory extends Fragment {

    private LinearLayout tabLinearPurcahse1Payment, tabLinearPurcahse2Payment;
    private TextView tabTxtPurcahse1Payment, tabTxtPurcahse2Payment;
    private UserSession session;

    public Fragment_PaymentHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_payment_history, container, false);

        session = new UserSession(getContext());

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLinearPaymentHistory, new Fragment_PaymentRecent(), "PaymentHistory1");
        ft.commit();

        tabLinearPurcahse1Payment = view.findViewById(R.id.tabLinearPurcahse1Payment);
        tabLinearPurcahse2Payment = view.findViewById(R.id.tabLinearPurcahse2Payment);
        tabTxtPurcahse1Payment = view.findViewById(R.id.tabTxtPurcahse1Payment);
        tabTxtPurcahse2Payment = view.findViewById(R.id.tabTxtPurcahse2Payment);

        tabLinearPurcahse1Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearPaymentHistory, new Fragment_PaymentRecent(), "PaymentHistory1");
                ft.commit();

                tabTxtPurcahse1Payment.setTextColor(Color.parseColor("#FFFFFFFF"));
                tabTxtPurcahse2Payment.setTextColor(Color.parseColor("#3e3e3e"));

                tabTxtPurcahse1Payment.setBackgroundResource(R.drawable.button_gray_order_history);
                tabTxtPurcahse2Payment.setBackgroundResource(R.drawable.btn_white_round_order);
            }
        });

        tabLinearPurcahse2Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearPaymentHistory, new Fragment_PaymentMonthly(), "PaymentHistory2");
                ft.commit();

                tabTxtPurcahse1Payment.setTextColor(Color.parseColor("#3e3e3e"));
                tabTxtPurcahse2Payment.setTextColor(Color.parseColor("#FFFFFFFF"));

                tabTxtPurcahse1Payment.setBackgroundResource(R.drawable.btn_white_round_order);
                tabTxtPurcahse2Payment.setBackgroundResource(R.drawable.button_gray_order_history);
            }
        });

        return view;
    }





}