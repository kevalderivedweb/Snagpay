package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.snagpay.Adapter.InvestorRelationAdapter;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_InvestorRelations extends AppCompatActivity {

    private RecyclerView resInvestor;
    private InvestorRelationAdapter investorRelationAdapter;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_relations);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_InvestorRelations.this);

        resInvestor = findViewById(R.id.resInvestor);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resInvestor.setLayoutManager(new GridLayoutManager(Activity_InvestorRelations.this, 2));
        investorRelationAdapter = new InvestorRelationAdapter(Activity_InvestorRelations.this);
        resInvestor.setAdapter(investorRelationAdapter);
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