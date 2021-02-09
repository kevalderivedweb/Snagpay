package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterReviewProductDetails;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_ProductDetails extends AppCompatActivity {

    private Button btnBuyNowProduct;
    private RecyclerView resProductUserReview;

    private AdapterReviewProductDetails adapterReviewProductDetails;
    private ImageView backToHomeInner;
    private ImageView productFavourite;

    private boolean firstClick = true;
    private UserSession session;

    private String isWishlist;
    private String dealId;

    private RelativeLayout addToWishlist, removeFromWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ProductDetails.this);

        btnBuyNowProduct = findViewById(R.id.btnBuyNowProduct);
        resProductUserReview = findViewById(R.id.resProductUserReview);
        backToHomeInner = findViewById(R.id.backToHomeInner);
        productFavourite = findViewById(R.id.productFavourite);
        addToWishlist = findViewById(R.id.addToWishlist);
        removeFromWishlist = findViewById(R.id.removeFromWishlist);

        Bundle bundle = getIntent().getExtras();

        String category_id = bundle.getString("category_id");
        String subCategoryId = bundle.getString("subCategoryId");

        backToHomeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.giveAsAGift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ProductDetails.this, Activity_GiveAsGift.class));
            }
        });

        productFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editWishList(dealId, isWishlist);
            }
        });

        resProductUserReview.setLayoutManager(new LinearLayoutManager(this));
        adapterReviewProductDetails = new AdapterReviewProductDetails(Activity_ProductDetails.this);
        resProductUserReview.setAdapter(adapterReviewProductDetails);

        btnBuyNowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ProductDetails.this, Activity_ReviewOrder.class));
            }
        });

        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishList(dealId, isWishlist);
            }
        });

        removeFromWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishList(dealId, isWishlist);
            }
        });

        getCategoriesDetails(category_id, "", subCategoryId, "", "", "1");



    }

    private void editWishList(String dealId, String wishStatus) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ProductDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "add-to-wishlist",
                new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());

                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {
                            if (wishStatus.equals("0")){
                                isWishlist = "1";
                                productFavourite.setImageResource(R.drawable.fill_heart);
                                addToWishlist.setVisibility(View.GONE);
                                removeFromWishlist.setVisibility(View.VISIBLE);

                            } else if (wishStatus.equals("1")){
                                isWishlist = "0";
                                productFavourite.setImageResource(R.drawable.heart);
                                addToWishlist.setVisibility(View.VISIBLE);
                                removeFromWishlist.setVisibility(View.GONE);
                            }

                            Toast.makeText(Activity_ProductDetails.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ProductDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("deal_id", dealId);

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
        Volley.newRequestQueue(Activity_ProductDetails.this).add(volleyMultipartRequest);
    }

    public void getCategoriesDetails(String category_id, String mShort, String mCategory, String startPrice, String endPrice, String page){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ProductDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "category-details?category_id="+category_id
                + "&sort_by_deals="+mShort
                + "&filter_category_id="+mCategory
                + "&from_price_range="+startPrice
                + "&to_price_range="+endPrice
                + "?page=" + page, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                Log.e("dataa", category_id + "---"+ mShort + "---"+ mCategory + "---" + startPrice + "---" + endPrice);

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            Log.e("cat", category_id + "---" + mCategory);

                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject jsonObject1 = data.getJSONObject("deals");

                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                dealId = object.getString("deal_id");
                                isWishlist = object.getString("is_wishlist");

                                Log.e("dealWish", dealId + "---" + isWishlist);

                            }

                            if (isWishlist.equals("0")){
                                productFavourite.setImageResource(R.drawable.heart);
                                addToWishlist.setVisibility(View.VISIBLE);
                                removeFromWishlist.setVisibility(View.GONE);
                            } else if (isWishlist.equals("1")){
                                productFavourite.setImageResource(R.drawable.fill_heart);
                                removeFromWishlist.setVisibility(View.VISIBLE);
                                addToWishlist.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ProductDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_ProductDetails.this).add(volleyMultipartRequest);
    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 21) {

                 String dealId = data.getStringExtra("dealID");
                 String isWishlist = data.getStringExtra("isWishlist");

                Log.e("daaaaaataaaaa", dealId + "--" + isWishlist);

            }
        }else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "main", Toast.LENGTH_SHORT).show();

    }*/





}