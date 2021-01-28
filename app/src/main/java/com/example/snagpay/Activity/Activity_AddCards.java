package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.snagpay.Utils.FourDigitCardFormatWatcher;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

public class Activity_AddCards extends AppCompatActivity {

    private Button btnSavePaymentInfo;
    private ImageView backToReviewOrder;
    private EditText editCardNumber, editExpiryDate;

    public int pos = 0;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cards);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_AddCards.this);

        btnSavePaymentInfo = findViewById(R.id.btnSavePaymentInfo);
        backToReviewOrder = findViewById(R.id.backToReviewOrder);
        editCardNumber = findViewById(R.id.editCardNumber);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        backToReviewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSavePaymentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newString = 0;
                if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if(extras == null) {
                        newString = 0;
                    } else {
                        newString = extras.getInt("let");
                    }
                }

                if (newString == 11) {
                    Intent intent = new Intent(Activity_AddCards.this, Activity_ShippingAddress.class);
                    intent.putExtra("value", 2);
                    startActivity(intent);
                }
                else {
                    finish();
                }


            }
        });

        editCardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());

        editExpiryDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                if(editExpiryDate.getText().length()==2 && pos!=3)
                {   editExpiryDate.setText(editExpiryDate.getText().toString()+"/");
                    editExpiryDate.setSelection(3);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                pos = editExpiryDate.getText().length();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
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