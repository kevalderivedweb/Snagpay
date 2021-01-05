package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snagpay.Adapter.AdapterNotificationFrag;

public class FragmentNotificationsActivity extends Fragment {

    private RecyclerView resNotificationList;
    private AdapterNotificationFrag adapterNotificationFrag;

    public FragmentNotificationsActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_notifications, container, false);

        resNotificationList = view.findViewById(R.id.resNotificationList);

        resNotificationList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterNotificationFrag = new AdapterNotificationFrag(getActivity());
        resNotificationList.setAdapter(adapterNotificationFrag);

        return view;
    }
}