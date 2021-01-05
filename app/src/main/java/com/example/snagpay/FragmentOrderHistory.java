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

public class FragmentOrderHistory extends Fragment {

    private LinearLayout tabLinearPurcahse1Order, tabLinearPurcahse2Order, tabLinearPurcahse3Order;
    private TextView tabTxtPurcahse1Order, tabTxtPurcahse2Order, tabTxtPurcahse3Order;

    public FragmentOrderHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_order_history, container, false);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLinearOrderHistory, new FragmentOrderCurrent(), "NewFragmentTag");
        ft.commit();

        tabLinearPurcahse1Order = view.findViewById(R.id.tabLinearPurcahseOrder);
        tabLinearPurcahse2Order = view.findViewById(R.id.tabLinearPurcahse2Order);
        tabLinearPurcahse3Order = view.findViewById(R.id.tabLinearPurcahse3Order);
        tabTxtPurcahse1Order = view.findViewById(R.id.tabTxtPurcahse1Order);
        tabTxtPurcahse2Order = view.findViewById(R.id.tabTxtPurcahse2Order);
        tabTxtPurcahse3Order = view.findViewById(R.id.tabTxtPurcahse3Order);

        tabLinearPurcahse1Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearOrderHistory, new FragmentOrderCurrent(), "CurrentOrder");
                ft.commit();

                tabTxtPurcahse1Order.setTextColor(Color.parseColor("#FFFFFFFF"));
                tabTxtPurcahse2Order.setTextColor(Color.parseColor("#3e3e3e"));
                tabTxtPurcahse3Order.setTextColor(Color.parseColor("#3e3e3e"));

                tabTxtPurcahse1Order.setBackgroundResource(R.drawable.button_gray_order_history);
                tabTxtPurcahse2Order.setBackgroundResource(R.drawable.btn_white_round_order);
                tabTxtPurcahse3Order.setBackgroundResource(R.drawable.btn_white_round_order);
            }
        });

        tabLinearPurcahse2Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearOrderHistory, new FragmentOrderCompleted(), "CompletedOrder");
                ft.commit();

                tabTxtPurcahse1Order.setTextColor(Color.parseColor("#3e3e3e"));
                tabTxtPurcahse2Order.setTextColor(Color.parseColor("#FFFFFFFF"));
                tabTxtPurcahse3Order.setTextColor(Color.parseColor("#3e3e3e"));

                tabTxtPurcahse1Order.setBackgroundResource(R.drawable.btn_white_round_order);
                tabTxtPurcahse2Order.setBackgroundResource(R.drawable.button_gray_order_history);
                tabTxtPurcahse3Order.setBackgroundResource(R.drawable.btn_white_round_order);
            }
        });

        tabLinearPurcahse3Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLinearOrderHistory, new FragmentOrderCancelled(), "CancelledOrder");
                ft.commit();

                tabTxtPurcahse1Order.setTextColor(Color.parseColor("#3e3e3e"));
                tabTxtPurcahse2Order.setTextColor(Color.parseColor("#3e3e3e"));
                tabTxtPurcahse3Order.setTextColor(Color.parseColor("#FFFFFFFF"));

                tabTxtPurcahse1Order.setBackgroundResource(R.drawable.btn_white_round_order);
                tabTxtPurcahse2Order.setBackgroundResource(R.drawable.btn_white_round_order);
                tabTxtPurcahse3Order.setBackgroundResource(R.drawable.button_gray_order_history);
            }
        });

        return view;
    }
}