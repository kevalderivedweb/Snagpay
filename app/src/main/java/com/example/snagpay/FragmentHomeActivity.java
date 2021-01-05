package com.example.snagpay;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.Adapter.AdapterHomeGrid;

public class FragmentHomeActivity extends Fragment {

    private RecyclerView recHomeInGrid;
    private AdapterHomeGrid resHomeGridAdapter;

    public FragmentHomeActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);

        recHomeInGrid = view.findViewById(R.id.recHomeInGrid);

        recHomeInGrid.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        resHomeGridAdapter = new AdapterHomeGrid(view.getContext());

        recHomeInGrid.setAdapter(resHomeGridAdapter);

        return view;
    }
}