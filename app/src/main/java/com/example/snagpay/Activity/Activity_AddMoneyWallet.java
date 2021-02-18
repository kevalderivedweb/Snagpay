package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_AddMoneyWallet extends AppCompatActivity {

    private UserSession session;
    private TextView txtAvailableBucks;
    private EditText amount_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_wallet);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_AddMoneyWallet.this);


        amount_ed = findViewById(R.id.amount_ed);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnAddMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_ed.getText().toString().isEmpty()){
                    Toast.makeText(Activity_AddMoneyWallet.this, "Please enter valid amount.", Toast.LENGTH_SHORT).show();

                }else {
                    AddWalletDetails(amount_ed.getText().toString());

                }
            }
        });

        txtAvailableBucks = findViewById(R.id.txtAvailableBucks);


        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("price");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("price");
        }

        txtAvailableBucks.setText(newString);

    }


    public void AddWalletDetails(String amount){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_AddMoneyWallet.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "add-credit-to-wallet", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){


                        Intent intent = new Intent(Activity_AddMoneyWallet.this, Activity_AddMoneySuccessfully.class);
                        intent.putExtra("amount",amount);
                        startActivity(intent);
                        finish();


                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_AddMoneyWallet.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(Activity_AddMoneyWallet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_AddMoneyWallet.this, "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(Activity_AddMoneyWallet.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + session.getAPITOKEN());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(Activity_AddMoneyWallet.this).add(volleyMultipartRequest);
    }

    




}