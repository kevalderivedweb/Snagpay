package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.Adapter.AdapterOrderCurrent;

public class FragmentOrderCurrent extends Fragment {

    private RecyclerView resOrderCurrent;
    private AdapterOrderCurrent adapterOrderCurrent;

    public FragmentOrderCurrent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_order_current, container, false);

        resOrderCurrent = view.findViewById(R.id.resOrderCurrent);

        resOrderCurrent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterOrderCurrent = new AdapterOrderCurrent(getContext());
        resOrderCurrent.setAdapter(adapterOrderCurrent);

        return view;
    }
}