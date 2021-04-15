package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterGiftCardDetail;
import com.example.snagpay.Model.GiftDetailModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_GiftCardDetails extends AppCompatActivity {

    private UserSession session;
    private ArrayList<GiftDetailModel> giftDetailModelArrayList = new ArrayList<>();
    private AdapterGiftCardDetail adapterGiftCardDetail;
    private RecyclerView recGiftDetail;
    private TextView noTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        session = new UserSession(Activity_GiftCardDetails.this);

        noTransaction = findViewById(R.id.noTransaction);

        recGiftDetail = findViewById(R.id.recGiftDetail);
        recGiftDetail.setLayoutManager(new LinearLayoutManager(Activity_GiftCardDetails.this));
        adapterGiftCardDetail = new AdapterGiftCardDetail(Activity_GiftCardDetails.this, giftDetailModelArrayList);
        recGiftDetail.setAdapter(adapterGiftCardDetail);

        String giftIdCard = getIntent().getStringExtra("giftIdCard");

        getGiftDetail(giftIdCard);

    }

    public void getGiftDetail(String giftIdCard){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_GiftCardDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "e-gift-card-transactions", new Response.Listener<NetworkResponse>() {
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

                            GiftDetailModel giftDetailModel = new GiftDetailModel();
                            giftDetailModel.setE_gift_card_transaction_id(jsonObject1.getString("e_gift_card_transaction_id"));
                            giftDetailModel.setE_gift_card_id(jsonObject1.getString("e_gift_card_id"));
                            giftDetailModel.setE_gift_card_tran_code(jsonObject1.getString("e_gift_card_tran_code"));
                            giftDetailModel.setAmount(jsonObject1.getString("amount"));
                            giftDetailModel.setIs_paid_additional_amount(jsonObject1.getString("is_paid_additional_amount"));

                            giftDetailModelArrayList.add(giftDetailModel);

                        }
                        adapterGiftCardDetail.notifyDataSetChanged();

                        if (giftDetailModelArrayList.isEmpty()){
                            noTransaction.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(Activity_GiftCardDetails.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_GiftCardDetails.this, Activity_SelectCity.class);
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
                params.put("e_gift_card_id", giftIdCard);

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
        Volley.newRequestQueue(Activity_GiftCardDetails.this).add(volleyMultipartRequest);

    }

}