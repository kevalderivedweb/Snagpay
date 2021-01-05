package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StatementActivity extends AppCompatActivity {

    private String[] yearList = {"2020"};
    private String[] monthList = {"March"};
    private Spinner spinnerSelectYearHistory,spinnerSelectMonthHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerSelectYearHistory = findViewById(R.id.spinnerSelectYearStatement);
        spinnerSelectMonthHistory = findViewById(R.id.spinnerSelectMonthStatement);

        ArrayAdapter<String> spinnerArrayAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearList);
        spinnerSelectYearHistory.setAdapter(spinnerArrayAdapterYear);

        ArrayAdapter<String> spinnerArrayAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthList);
        spinnerSelectMonthHistory.setAdapter(spinnerArrayAdapterMonth);
    }
}