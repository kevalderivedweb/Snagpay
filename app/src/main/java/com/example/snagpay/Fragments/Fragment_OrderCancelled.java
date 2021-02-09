package com.example.snagpay.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Fragment_OrderCancelled extends Fragment {

    private UserSession session;

    public Fragment_OrderCancelled() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.activity_fragment_order_cancelled, container, false);

        session = new UserSession(getContext());

        return view;
    }




}