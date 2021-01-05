package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Activity_GiveAsGift extends AppCompatActivity {

    private RadioGroup radioGroupGift;
    private LinearLayout lnrGiftEmail, lnrGiftText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_as_gift);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        radioGroupGift = findViewById(R.id.radioGroupGift);
        lnrGiftEmail = findViewById(R.id.lnrGiftEmail);
        lnrGiftText = findViewById(R.id.lnrGiftText);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnGiveGift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroupGift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioEmail:

                        lnrGiftEmail.setVisibility(View.VISIBLE);
                        lnrGiftText.setVisibility(View.GONE);

                        break;
                    case R.id.radioText:

                        lnrGiftEmail.setVisibility(View.GONE);
                        lnrGiftText.setVisibility(View.VISIBLE);

                        break;

                }
            }
        });

    }
}