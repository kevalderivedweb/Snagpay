package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.snagpay.Model.DealsOrderModel;
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
    private RelativeLayout lyoutForPromo, singleItems;
    private View viewPromo;

    private ImageView imageDeal, orderMinus, orderPlus;
    private TextView titleDeal, priceOrder, txtOrderCount;

    private EditText editPromo;
    private TextView checkPromoCode, promoCode, totalPrice, bucksSnagpay, taxAmount, shippingAmount, payableAmount;
    private UserSession session;
    private Database dbHelper;

    private ArrayList<CategoryDetailsModel> detailsModelArrayList;
    private ArrayList<DealsOrderModel> orderModelArrayList;

    private AdapterDealsOrder adapterDealsOrder;

    private RecyclerView recReviewOrder;

    private TextView credit;
    private AppCompatButton check;
    private AppCompatButton add_amount;
    private EditText ed_txt;

    private String stringPromo = "";

    private String strValue;
    private String dealOptionId;
    private int quantity = 1;
    private String requiredAmount;
    private String availBucks;

    private String disCount = "";


    //API calling Data

    private String mDealPromoId = "";
    private String mTotalPrice = "";
    private String mDiscount = "0";
    private String mSnagpayBucks = "";
    private String mTaxs = "";
    private String mShipping = "";
    private String mTotalAmountPay = "";
    private String mShippingAddressId = "";


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
        credit = findViewById(R.id.credit);
        check = findViewById(R.id.check);
        add_amount = findViewById(R.id.add_amount);
        ed_txt = findViewById(R.id.ed_txt);
        singleItems = findViewById(R.id.singleItems);
        imageDeal = findViewById(R.id.imageDeal);
        titleDeal = findViewById(R.id.titleDeal);
        priceOrder = findViewById(R.id.priceOrder);
        orderMinus = findViewById(R.id.orderMinus);
        orderPlus = findViewById(R.id.orderPlus);
        txtOrderCount = findViewById(R.id.txtOrderCount);


        orderMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 < Integer.parseInt(txtOrderCount.getText().toString())){
                    quantity--;
                    txtOrderCount.setText(String.valueOf(quantity));
                    getReviewOrderDetail(detailsModelArrayList);
                }

            }
        });

        orderPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                txtOrderCount.setText(String.valueOf(quantity));
                getReviewOrderDetail(detailsModelArrayList);
            }
        });


        strValue = getIntent().getStringExtra("valueForOrder");
        dealOptionId = getIntent().getStringExtra("dealOptionId");

        if (strValue.equals("fromProductDetails")){
            recReviewOrder.setVisibility(View.GONE);


        }
        else if (strValue.equals("fromCart")){
            singleItems.setVisibility(View.GONE);
            detailsModelArrayList = dbHelper.getAllUser();
        }



        checkPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editPromo.getText().toString().isEmpty()){
                    stringPromo = editPromo.getText().toString();
                    getReviewOrderDetail(detailsModelArrayList);
                } else if (editPromo.getText().toString().isEmpty()){
                    stringPromo = "";
                    getReviewOrderDetail(detailsModelArrayList);
                }
            }
        });


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

        getCreditDetails();


        btnCompletePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (disCount.equals("")){
                    disCount = "0";
                }

                if (Integer.parseInt(totalPrice.getText().toString().substring(1)) <= (Integer.parseInt(disCount) + Integer.parseInt(availBucks))) {

                    Intent intent = new Intent(Activity_ReviewOrder.this, Activity_ShippingAddress.class);
                        intent.putExtra("value", 2);
                        startActivityForResult(intent, 2);// Activity is started with requestCode 2

                } else if (Integer.parseInt(totalPrice.getText().toString().substring(1)) > (Integer.parseInt(disCount) + Integer.parseInt(availBucks))){
                    Toast.makeText(Activity_ReviewOrder.this, "Required $" + requiredAmount + " bucks", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getReviewOrderDetail(detailsModelArrayList);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            mShippingAddressId =data.getStringExtra("MESSAGE");
            CreateOrder();
        }
    }

    public void AddWalletDetails(String amount){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ReviewOrder.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
            //    .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "add-credit-to-wallet", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


              //  progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        ed_txt.setText("");

                        Toast.makeText(Activity_ReviewOrder.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        getCreditDetails();

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

                      /*  credit.setVisibility(View.VISIBLE);
                        credit.setText("Available Credit in your wallet $ " + jsonObject.getJSONObject("data").getString("trade_credit"));*/

                        bucksSnagpay.setText("$" + jsonObject.getJSONObject("data").getString("trade_credit"));

                        Log.e("sdsdsd", "$" + jsonObject.getJSONObject("data").getString("trade_credit"));

                        getReviewOrderDetail(detailsModelArrayList);


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
        Volley.newRequestQueue(Activity_ReviewOrder.this).add(volleyMultipartRequest);

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

                            if(data.has("deal_promo_code_id")){
                                mDealPromoId = data.getString("deal_promo_code_id");
                            }

                             mTotalPrice = data.getString("total_price");
                             mSnagpayBucks = data.getString("snagpay_trade_credit").substring(1);
                             mTaxs = data.getString("tax");
                             mShipping = data.getString("shipping_cost");
                             mTotalAmountPay = data.getString("total_amount_payable");



                            Picasso.get().load(orderModelArrayList.get(0).getDeal_image()).into(imageDeal);

                            titleDeal.setText(orderModelArrayList.get(0).getTitle());
                            priceOrder.setText("$" + orderModelArrayList.get(0).getSell_price());


                            totalPrice.setText("$" + data.getString("total_price"));
                            taxAmount.setText("$" + data.getString("tax"));
                            shippingAmount.setText("$" + data.getString("shipping_cost"));
                            payableAmount.setText("$" + data.getString("total_amount_payable"));

                            if (!data.getString("required_snagpay_trade_credit").equals("0")) {
                                requiredAmount = data.getString("required_snagpay_trade_credit");
                                ed_txt.setText(requiredAmount);
                            }

                            availBucks = data.getString("snagpay_trade_credit").substring(1);

                            Log.e("sdfsfsd", Integer.parseInt(data.getString("total_price")) + "---" +
                                    availBucks);


                            if (Integer.parseInt(data.getString("total_price")) > Integer.parseInt(data.getString("snagpay_trade_credit").substring(1))){
                                bucksSnagpay.setTextColor(getResources().getColor(R.color.red));

                                Toast.makeText(Activity_ReviewOrder.this, "red", Toast.LENGTH_SHORT).show();
                            } else {
                                bucksSnagpay.setTextColor(getResources().getColor(R.color.green));
                                Toast.makeText(Activity_ReviewOrder.this, "green", Toast.LENGTH_SHORT).show();
                            }

                            mDiscount = data.getString("discount");
                            if (!stringPromo.equals("")){
                                mDiscount = data.getString("discount");
                                disCount = data.getString("discount");

                            } else {
                                disCount = "";
                                mDiscount = "";

                            }



                            adapterDealsOrder.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ReviewOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("422")){

                        Toast.makeText(Activity_ReviewOrder.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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

                if (strValue.equals("fromCart")) {
                    for (int i = 0; i < detailsModelArrayList.size(); i++) {
                        params.put("deal_option_ids[" + detailsModelArrayList.get(i).getShow_deal_option_id() + "]", detailsModelArrayList.get(i).getQuantity());
                    }
                    params.put("promo_code", stringPromo);
                }
                else if (strValue.equals("fromProductDetails")){
                    params.put("deal_option_ids[" + dealOptionId + "]", String.valueOf(quantity));
                    params.put("promo_code", stringPromo);
                }

                Log.e("sdsd", dealOptionId + "--" + quantity);

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



    public void CreateOrder(){

        Log.e("mDiscound" , "--"+mDiscount+"--"+disCount);
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ReviewOrder.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "create-order",
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


                                Intent intent = new Intent(Activity_ReviewOrder.this, Activity_ThankYou.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            else if(jsonObject.getString("ResponseCode").equals("422")){

                                Toast.makeText(Activity_ReviewOrder.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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

                for (int d = 0; d < orderModelArrayList.size(); d++){
                    params.put("deal_option_ids["+orderModelArrayList.get(d).getDeal_id()+"]", orderModelArrayList.get(d).getQty());
                }
                params.put("deal_promo_code_id", mDealPromoId);
                params.put("total_price", mTotalPrice);
                params.put("discount", "0");
                params.put("snagpay_trade_credit", mSnagpayBucks);
                params.put("taxes_and_fees", mTaxs);
                params.put("estimated_shipping", mShipping);
                params.put("total_amount_payable", mTotalAmountPay);
                params.put("shipping_address_id", mShippingAddressId);
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