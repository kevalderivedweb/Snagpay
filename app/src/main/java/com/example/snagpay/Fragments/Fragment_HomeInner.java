package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.Activity.Activity_ProductDetails;
import com.example.snagpay.R;

public class Fragment_HomeInner extends Fragment {

    private RecyclerView recHomeInner;
    private AdapterHomeInner adapterHomeInner;

    public Fragment_HomeInner() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_home_inner_fragment, container, false);
        recHomeInner = view.findViewById(R.id.recHomeInner);

        recHomeInner.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapterHomeInner = new AdapterHomeInner(getContext(), new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                startActivity(new Intent(getContext(), Activity_ProductDetails.class));
            }
        });
        recHomeInner.setAdapter(adapterHomeInner);

        return view;
    }
}