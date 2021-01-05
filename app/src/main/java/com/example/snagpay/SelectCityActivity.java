package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectCityActivity extends AppCompatActivity {

    private String[] cityList = {"Central Phonenix"};
    private Spinner spinnerSelectCity;
    private Button btnUseMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        spinnerSelectCity = findViewById(R.id.spinnerSelectCity);
        btnUseMyLocation = findViewById(R.id.btnUseMyLocation);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityList);
        spinnerSelectCity.setAdapter(spinnerArrayAdapter);

        btnUseMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInSignUpActivity.class));
            }
        });

    }
}