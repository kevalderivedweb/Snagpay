package com.example.snagpay.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Adapter.AdapterCategoriesItems;
import com.example.snagpay.Activity.Activity_CategoriesInnerItems;
import com.example.snagpay.R;

public class Fragment_CategoriesActivity extends Fragment {

    private RecyclerView recCategories;
    private AdapterCategoriesItems adapterCategoriesItems;
    private LinearLayout lnrLyot;

    public Fragment_CategoriesActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_categories, container, false);

        recCategories = view.findViewById(R.id.recCategories);
        lnrLyot = view.findViewById(R.id.lnrLyot);

        recCategories.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        adapterCategoriesItems = new AdapterCategoriesItems(view.getContext());
        recCategories.setAdapter(adapterCategoriesItems);

        lnrLyot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_CategoriesInnerItems.class));
            }
        });

        return view;
    }
}