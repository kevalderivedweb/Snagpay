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

import com.example.snagpay.Fragments.Fragment_OrderCancelled;
import com.example.snagpay.Fragments.Fragment_OrderCompleted;
import com.example.snagpay.Fragments.Fragment_OrderCurrent;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Fragment_OrderHistory extends Fragment {

    private LinearLayout tabLinearPurcahse1Order, tabLinearPurcahse2Order, tabLinearPurcahse3Order;
    private TextView tabTxtPurcahse1Order, tabTxtPurcahse2Order, tabTxtPurcahse3Order;
    private UserSession session;

    public Fragment_OrderHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_order_history, container, false);

        session = new UserSession(getContext());

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLinearOrderHistory, new Fragment_OrderCurrent(), "NewFragmentTag");
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
                ft.replace(R.id.fragmentLinearOrderHistory, new Fragment_OrderCurrent(), "CurrentOrder");
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
                ft.replace(R.id.fragmentLinearOrderHistory, new Fragment_OrderCompleted(), "CompletedOrder");
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
                ft.replace(R.id.fragmentLinearOrderHistory, new Fragment_OrderCancelled(), "CancelledOrder");
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



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }
}