package com.example.snagpay.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Fragment_PaymentMonthly extends Fragment {

    private String[] yearList = {"2020"};
    private String[] monthList = {"March"};
    private Spinner spinnerSelectYearHistory,spinnerSelectMonthHistory;
    private UserSession session;

    public Fragment_PaymentMonthly() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.activity_fragment_payment_monthly, container, false);

        session = new UserSession(getContext());

        spinnerSelectYearHistory = view.findViewById(R.id.spinnerSelectYearHistory);
        spinnerSelectMonthHistory = view.findViewById(R.id.spinnerSelectMonthHistory);

        ArrayAdapter<String> spinnerArrayAdapterYear = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, yearList);
        spinnerSelectYearHistory.setAdapter(spinnerArrayAdapterYear);

        ArrayAdapter<String> spinnerArrayAdapterMonth = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, monthList);
        spinnerSelectMonthHistory.setAdapter(spinnerArrayAdapterMonth);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}