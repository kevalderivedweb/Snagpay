package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.snagpay.Adapter.ExpListAdapterPaymentRecent;
import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class Activity_SnagpayWallet extends AppCompatActivity {

    private ExpListAdapterPaymentRecent listAdapterPayment;
    private ExpandableListView expListViewPayment;
    private ArrayList<PaymentModel> paymentModelArrayList = new ArrayList<>();

    private RelativeLayout rltvRequestStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_wallet);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.rltvRequestStatement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_SnagpayWallet.this, Activity_Statement.class));
            }
        });

         findViewById(R.id.rltvAddMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_SnagpayWallet.this, Activity_AddMoneyWallet.class));
            }
        });


        expListViewPayment = findViewById(R.id.lvExpWallet);
        expListViewPayment.setChildDivider(getResources().getDrawable(R.color.white));

        // preparing list data


        listAdapterPayment = new ExpListAdapterPaymentRecent(Activity_SnagpayWallet.this, paymentModelArrayList);
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