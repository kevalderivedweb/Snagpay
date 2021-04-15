package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterEgiftDeals;
import com.example.snagpay.Model.EGiftCardModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_VoucherDetails extends AppCompatActivity {

    private UserSession session;
    private ArrayList<EGiftCardModel> eGiftCardModelArrayList = new ArrayList<>();
    private RecyclerView recDealsMerchant;
    private AdapterEgiftDeals adapterEgiftDeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        session = new UserSession(Activity_VoucherDetails.this);

        findViewById(R.id.backToPaymentInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recDealsMerchant = findViewById(R.id.recDealsMerchant);

      /*  if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //    window.setStatusBarColor(getResources().getColor(R.color.design_default_color_primary));
        }*/

        String marchantId = getIntent().getStringExtra("marchantId");

        recDealsMerchant.setLayoutManager(new LinearLayoutManager(Activity_VoucherDetails.this));
        adapterEgiftDeals = new AdapterEgiftDeals(Activity_VoucherDetails.this, eGiftCardModelArrayList, new AdapterEgiftDeals.OnItemClickListener() {
            @Override
            public void onChecked(int item, boolean isChecked) {

                if (isChecked){
                    eGiftCardModelArrayList.get(item).setChecked("1");
                } else {
                    eGiftCardModelArrayList.get(item).setChecked("0");
                }
            }

            @Override
            public void onItemTextChange(int item, String amount) {

                eGiftCardModelArrayList.get(item).setAmount(amount);
            }

            @Override
            public void onItemClick(String giftID) {

                Intent intent = new Intent(Activity_VoucherDetails.this, Activity_GiftCardDetails.class);
                intent.putExtra("giftIdCard", giftID);
                startActivity(intent);
            }
        });
        recDealsMerchant.setAdapter(adapterEgiftDeals);

        recDealsMerchant.post(new Runnable() {
            @Override
            public void run() {
                adapterEgiftDeals.notifyDataSetChanged();
            }
        });


        findViewById(R.id.btnRadeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radeemGift(marchantId, eGiftCardModelArrayList);
            }
        });

        getDeals(marchantId);

    }

    public void radeemGift(String marchantId, ArrayList<EGiftCardModel> eGiftCardModelArrayList){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_VoucherDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "redeem-e-gift-card", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        Toast.makeText(Activity_VoucherDetails.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("422")){
                        Toast.makeText(Activity_VoucherDetails.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_VoucherDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {

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

         //               Log.e("dssdsd", error.getMessage() + "--");

//                        Toast.makeText(Activity_VoucherDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                for (int i = 0; i < eGiftCardModelArrayList.size(); i++) {
                    params.put("e_gift_card_ids[" + eGiftCardModelArrayList.get(i).getE_gift_card_id() + "]", eGiftCardModelArrayList.get(i).getAmount());
                }
                params.put("merchant_id", marchantId);

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
        Volley.newRequestQueue(Activity_VoucherDetails.this).add(volleyMultipartRequest);

    }


    public void getDeals(String marchantId){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_VoucherDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "e-gift-cards", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            EGiftCardModel eGiftCardModel = new EGiftCardModel();
                            eGiftCardModel.setE_gift_card_id(jsonObject1.getString("e_gift_card_id"));
                            eGiftCardModel.setE_gift_card_code(jsonObject1.getString("e_gift_card_code"));
                            eGiftCardModel.setUser_id(jsonObject1.getString("user_id"));
                            eGiftCardModel.setOrder_id(jsonObject1.getString("order_id"));
                            eGiftCardModel.setAmount(jsonObject1.getString("amount"));
                            eGiftCardModel.setQty(jsonObject1.getString("qty"));
                            eGiftCardModel.setQrcode(jsonObject1.getString("qrcode"));
                            eGiftCardModel.setStatus(jsonObject1.getString("status"));
                            eGiftCardModel.setChecked("0");

                            eGiftCardModelArrayList.add(eGiftCardModel);

                        }
                        adapterEgiftDeals.notifyDataSetChanged();

                        Toast.makeText(Activity_VoucherDetails.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_VoucherDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {

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

                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("merchant_id", marchantId);

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
        Volley.newRequestQueue(Activity_VoucherDetails.this).add(volleyMultipartRequest);

    }

}