package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.snagpay.Adapter.ExpListAdapterPaymentRecent;

import java.util.ArrayList;

import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Fragment_PaymentRecent extends Fragment {

    private ExpListAdapterPaymentRecent listAdapterPayment;
    private ExpandableListView expListViewPayment;
    private ArrayList<PaymentModel> paymentModelArrayList = new ArrayList<>();
    private UserSession session;

    public Fragment_PaymentRecent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_fragment_payment_recent, container, false);
        session = new UserSession(getContext());

        expListViewPayment = (ExpandableListView) view.findViewById(R.id.lvExpPayment);
        expListViewPayment.setChildDivider(getResources().getDrawable(R.color.white));

        // preparing list data


        listAdapterPayment = new ExpListAdapterPaymentRecent(getContext(), paymentModelArrayList);
        // setting list adapter
        expListViewPayment.setAdapter(listAdapterPayment);
        // Listview Group click listener
        expListViewPayment.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        prepareListData();

        // Listview on child click listener
        expListViewPayment.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });

        return view;
    }

    private void prepareListData() {

        for (int i=0; i<8; i++){
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setStatusPayment("Paid on SNAGpay");
            paymentModel.setDate("18 Sept 2020, 10:04 AM");
            paymentModel.setPrice("$120");
            paymentModel.setCard("Visa card");

            ArrayList<DetailPaymentModel> detailPaymentModels = new ArrayList<>();

            DetailPaymentModel detailPaymentModel = new DetailPaymentModel();
            detailPaymentModel.setCardType("Credit Card");
            detailPaymentModel.setOrderId("123-12345645-4565");

            detailPaymentModels.add(detailPaymentModel);

            paymentModel.setDetailPaymentModels(detailPaymentModels);
            paymentModelArrayList.add(paymentModel);
        }
    }




}