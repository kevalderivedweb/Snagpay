package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_ReviewOrder extends AppCompatActivity {

    private Button btnCompletePurchase;
    private ImageView backToProductDetail;

    private ImageView orderMinus, orderPlus;
    private TextView txtOrderCount;
    private UserSession session;
    private AppCompatButton add_amount;
    private EditText ed_txt;
    private TextView credit;
    private AppCompatButton check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ReviewOrder.this);

        btnCompletePurchase = findViewById(R.id.btnCompletePurchase);
        backToProductDetail = findViewById(R.id.backToProductDetail);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        orderMinus = findViewById(R.id.orderMinus);
        orderPlus = findViewById(R.id.orderPlus);
        add_amount = findViewById(R.id.add_amount);
        ed_txt = findViewById(R.id.ed_txt);
        credit = findViewById(R.id.credit);
        check = findViewById(R.id.check);

        backToProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCompletePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ReviewOrder.this, Activity_AddCards.class);
                intent.putExtra("let", 11);
                startActivity(intent);
            }
        });

        orderMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = Integer.parseInt(txtOrderCount.getText().toString());
                if (order > 0) {
                    txtOrderCount.setText(String.valueOf(order - 1));
                }
            }
        });

        orderPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = Integer.parseInt(txtOrderCount.getText().toString());
                txtOrderCount.setText(String.valueOf(order + 1));
            }
        });
        add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_txt.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ReviewOrder.this, "Please enter valid amount.", Toast.LENGTH_SHORT).show();

                }else {
                    credit.setVisibility(View.GONE);
                    AddWalletDetails(ed_txt.getText().toString());
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCreditDetails();
            }
        });
    }



    public void AddWalletDetails(String amount){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ReviewOrder.this)
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

                        Toast.makeText(Activity_ReviewOrder.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();



                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ReviewOrder.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(Activity_ReviewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_ReviewOrder.this, "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(Activity_ReviewOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_ReviewOrder.this).add(volleyMultipartRequest);
    }

    public void getCreditDetails(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ReviewOrder.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "credit-available-in-wallet", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        credit.setVisibility(View.VISIBLE);
                        credit.setText("Available Credit in your wallet $ "+jsonObject.getJSONObject("data").getString("trade_credit"));
                        Toast.makeText(Activity_ReviewOrder.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ReviewOrder.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(Activity_ReviewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_ReviewOrder.this, "No Data", Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();*/

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(Activity_ReviewOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_ReviewOrder.this).add(volleyMultipartRequest);
    }





}