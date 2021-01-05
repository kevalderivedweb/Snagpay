package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.InvestorRelationAdapter;

public class InvestorRelations extends AppCompatActivity {

    private RecyclerView resInvestor;
    private InvestorRelationAdapter investorRelationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_relations);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        resInvestor = findViewById(R.id.resInvestor);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resInvestor.setLayoutManager(new GridLayoutManager(InvestorRelations.this, 2));
        investorRelationAdapter = new InvestorRelationAdapter(InvestorRelations.this);
        resInvestor.setAdapter(investorRelationAdapter);
    }
}