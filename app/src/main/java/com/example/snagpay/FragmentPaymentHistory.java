package com.example.snagpay;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentPaymentHistory extends Fragment {

    private LinearLayout tabLinearPurcahse1Payment, tabLinearPurcahse2Payment;
    private TextView tabTxtPurcahse1Payment, tabTxtPurcahse2Payment;

    public FragmentPaymentHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_payment_history, container, false);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLinearPaymentHistory, new FragmentPaymentRecent(), "PaymentHistory1");
        ft.commit();

        tabLinearPurcahse1Payment = view.findViewById(R.id.tabLinearPurcahse1Payment);
        tabLinearPurcahse2Payment = view.findViewById(R.id.tabLinearPurcahse2Payment);
        tabTxtPurcahse1Payment = view.findViewById(R.id.tabTxtPurcahse1Payment);
        tabTxtPurcahse2Payment = view.findViewById(R.id.tabTxtPurcahse2Payment);

        tabLinearPurcahse1Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearPaymentHistory, new FragmentPaymentRecent(), "PaymentHistory1");
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
                ft.replace(R.id.fragmentLinearPaymentHistory, new FragmentPaymentMonthly(), "PaymentHistory2");
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