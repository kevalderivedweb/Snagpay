package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.snagpay.Model.OrderModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_OrderDetails extends AppCompatActivity {

    private UserSession session;
    private TextView txtOrderName, priceOrder, nameOrder, addressPin, phoneNo, priceOrderDet, bucks, taxAmount, amountShipping, totalPaidAmount;
    private ImageView imgOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_OrderDetails.this);

        txtOrderName = findViewById(R.id.txtOrderName);
        priceOrder = findViewById(R.id.priceOrder);
        nameOrder = findViewById(R.id.nameOrder);
        addressPin = findViewById(R.id.addressPin);
        phoneNo = findViewById(R.id.phoneNo);
        priceOrderDet = findViewById(R.id.priceOrderDet);
        bucks = findViewById(R.id.bucks);
        taxAmount = findViewById(R.id.taxAmount);
        amountShipping = findViewById(R.id.amountShipping);
        totalPaidAmount = findViewById(R.id.totalPaidAmount);
        imgOrderDetails = findViewById(R.id.imgOrderDetails);

        findViewById(R.id.backToCurrentOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String orderId = getIntent().getStringExtra("orderId");

        getOrderDetails(orderId);

    }

    public void getOrderDetails(String orderId){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_OrderDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "order-details?order_id=" + orderId, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());

                    if (jsonObject.getString("ResponseCode").equals("200")){

                        JSONObject jsonObject1 = jsonObject.getJSONObject("orders");

                        txtOrderName.setText(jsonObject1.getString("title"));
                        priceOrder.setText("$" + jsonObject1.getString("sub_total_amount"));
                        nameOrder.setText(jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
                        addressPin.setText(jsonObject1.getString("address") + " - " + jsonObject1.getString("postcode"));
                        phoneNo.setText(jsonObject1.getString("phone_no"));
                        priceOrderDet.setText("$" +jsonObject1.getString("sub_total_amount"));
                        bucks.setText("$" + jsonObject1.getString("paid_trade_credit"));
                        taxAmount.setText("$" + jsonObject1.getString("taxes_and_fees"));
                        amountShipping.setText("$" + jsonObject1.getString("estimated_shipping"));
                        totalPaidAmount.setText("$" + jsonObject1.getString("paid_amount"));

                        Picasso.get().load(jsonObject1.getString("deal_image")).into(imgOrderDetails);

                        Toast.makeText(Activity_OrderDetails.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_OrderDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(Activity_OrderDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Activity_OrderDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


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


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(Activity_OrderDetails.this).add(volleyMultipartRequest);

    }


}