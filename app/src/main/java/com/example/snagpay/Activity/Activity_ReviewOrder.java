package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterDealsOrder;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.DealOptionsListModel;
import com.example.snagpay.Model.DealsOrderModel;
import com.example.snagpay.Model.ReviewModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.Database;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_ReviewOrder extends AppCompatActivity {

    private Button btnCompletePurchase;
    private ImageView backToProductDetail;
    private RelativeLayout lyoutForPromo;
    private View viewPromo;

    private EditText editPromo;
    private TextView checkPromoCode, promoCode, totalPrice, bucksSnagpay, taxAmount, shippingAmount, payableAmount;
    private UserSession session;
    private Database dbHelper;

    private ArrayList<CategoryDetailsModel> detailsModelArrayList;
    private ArrayList<DealsOrderModel> orderModelArrayList;

    private AdapterDealsOrder adapterDealsOrder;

    private RecyclerView recReviewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        session = new UserSession(Activity_ReviewOrder.this);
        dbHelper = new Database(Activity_ReviewOrder.this);
        orderModelArrayList = new ArrayList<>();

        backToProductDetail = findViewById(R.id.backToProductDetail);
        backToProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detailsModelArrayList = dbHelper.getAllUser();


        btnCompletePurchase = findViewById(R.id.btnCompletePurchase);

        recReviewOrder = findViewById(R.id.recReviewOrder);

        lyoutForPromo = findViewById(R.id.lyoutForPromo);
        viewPromo = findViewById(R.id.viewPromo);
        editPromo = findViewById(R.id.editPromo);
        checkPromoCode = findViewById(R.id.checkPromoCode);
        promoCode = findViewById(R.id.promoCode);
        totalPrice = findViewById(R.id.totalPrice);
        bucksSnagpay = findViewById(R.id.bucksSnagpay);
        taxAmount = findViewById(R.id.taxAmount);
        shippingAmount = findViewById(R.id.shippingAmount);
        payableAmount = findViewById(R.id.payableAmount);

        recReviewOrder.setLayoutManager(new LinearLayoutManager(Activity_ReviewOrder.this));
        adapterDealsOrder = new AdapterDealsOrder(Activity_ReviewOrder.this, orderModelArrayList);
        recReviewOrder.setAdapter(adapterDealsOrder);


        promoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoCode.setVisibility(View.GONE);
                viewPromo.setVisibility(View.GONE);
                lyoutForPromo.setVisibility(View.VISIBLE);
            }
        });

        checkPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPromo.setText("");
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


        getReviewOrderDetail(detailsModelArrayList);


    }


    public void getReviewOrderDetail(ArrayList<CategoryDetailsModel> detailsModelArrayList){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ReviewOrder.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "review-your-order",
                new Response.Listener<NetworkResponse>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    orderModelArrayList.clear();

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = data.getJSONArray("deals");

                            for (int d = 0; d < jsonArray.length(); d++){
                                JSONObject object = jsonArray.getJSONObject(d);

                                DealsOrderModel dealsOrderModel = new DealsOrderModel();
                                dealsOrderModel.setDeal_id(object.getString("deal_id"));
                                dealsOrderModel.setTitle(object.getString("title"));
                                dealsOrderModel.setDeal_image(object.getString("deal_image"));
                                dealsOrderModel.setDeal_option_id(object.getString("deal_option_id"));
                                dealsOrderModel.setDeal_option_name(object.getString("deal_option_name"));
                                dealsOrderModel.setSell_price(object.getString("sell_price"));
                                dealsOrderModel.setQty(object.getString("qty"));

                                orderModelArrayList.add(dealsOrderModel);
                            }

                            totalPrice.setText("$" + data.getString("total_price"));
                            bucksSnagpay.setText(data.getString("snagpay_trade_credit"));
                            taxAmount.setText("$" + data.getString("tax"));
                            shippingAmount.setText("$" + data.getString("shipping_cost"));
                            payableAmount.setText("$" + data.getString("total_amount_payable"));

                            adapterDealsOrder.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ReviewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ReviewOrder.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_ReviewOrder.this, "No Data", Toast.LENGTH_SHORT).show();

                   /* session.logout();
                    Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

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

                for (int i = 0; i < detailsModelArrayList.size(); i++){
                    params.put("deal_option_ids[" + detailsModelArrayList.get(i).getShow_deal_option_id() + "]", detailsModelArrayList.get(i).getQuantity());
                }

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