package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.example.snagpay.Adapter.AdapterDealsOptionList;
import com.example.snagpay.Adapter.AdapterReviewProductDetails;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.DealOptionsListModel;
import com.example.snagpay.Model.ReviewModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.Database;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_ProductDetails extends AppCompatActivity {

    private Button btnBuyNowProduct;
    private RecyclerView resProductUserReview, dealsOptionList;

    private ImageView backToHomeInner, productFavourite, dealImage, arrowDetailsExpand, listArrow;
    private TextView detailsProductTxt, dealTitle, rating, totalRating, highLights, customeR,
            countRtng, selectDealsName, selectDealsPrice, noReviewsText, txtAddRemoveCart;

    private RatingBar ratingBarProductDetailsMain, ratingAgainProduct;

    private AdapterReviewProductDetails adapterReviewProductDetails;
    private AdapterDealsOptionList adapterDealsOptionList;

    private UserSession session;

    private String isWishlist;

    private RelativeLayout addToWishlist, removeFromWishlist, listOfDealsRelative, addToCart;

    private ArrayList<ReviewModel> reviewModelArrayList = new ArrayList<>();
    private ArrayList<DealOptionsListModel> dealOptionsListModelArrayList = new ArrayList<>();

    private boolean isTextViewClicked = true;
    private boolean islistClicked = true;

    private String imageCart;
    private String titleCart;
    private String priceCart;
    private String boughtCart;
    private String dealOptionIdCart;
    private String availableStocks;

    private String shippingAddressId;
    private String bucksInWallet;

    private Database dbHelper;

    private ArrayList<CategoryDetailsModel> detailsModelArrayList = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ProductDetails.this);

        dbHelper = new Database(Activity_ProductDetails.this);

        btnBuyNowProduct = findViewById(R.id.btnBuyNowProduct);
        resProductUserReview = findViewById(R.id.resProductUserReview);
        backToHomeInner = findViewById(R.id.backToHomeInner);
        productFavourite = findViewById(R.id.productFavourite);
        addToWishlist = findViewById(R.id.addToWishlist);
        removeFromWishlist = findViewById(R.id.removeFromWishlist);
        ratingBarProductDetailsMain = findViewById(R.id.ratingBarProductDetailsMain);
        detailsProductTxt = findViewById(R.id.detailsProductTxt);
        arrowDetailsExpand = findViewById(R.id.arrowDetailsExpand);

        txtAddRemoveCart = findViewById(R.id.txtAddRemoveCart);

        dealImage = findViewById(R.id.dealImage);
        dealTitle = findViewById(R.id.dealTitle);
        rating = findViewById(R.id.rating);
        totalRating = findViewById(R.id.totalRating);
        highLights = findViewById(R.id.highLights);

        customeR = findViewById(R.id.customeR);
        countRtng = findViewById(R.id.countRtng);
        ratingAgainProduct = findViewById(R.id.ratingAgainProduct);

        dealsOptionList = findViewById(R.id.dealsPriceList);
        listOfDealsRelative = findViewById(R.id.listOfDealsRelative);

        selectDealsName = findViewById(R.id.selectDealsName);
        selectDealsPrice = findViewById(R.id.selectDealsPrice);
        listArrow = findViewById(R.id.listArrow);
        noReviewsText = findViewById(R.id.noReviewsText);

        addToCart = findViewById(R.id.addToCart);

        Bundle bundle = getIntent().getExtras();
        String category_id = bundle.getString("category_id");
        String subCategoryId = bundle.getString("subCategoryId");
        String dealId = bundle.getString("dealId");

        Log.e("osddf", category_id + "---" + subCategoryId + "---" + dealId);

        detailsModelArrayList = dbHelper.getAllUser();




        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("cdfdefedf", dealOptionIdCart + "--");


                if (!txtAddRemoveCart.getText().toString().equals("Remove from Cart")){
                    dbHelper.InsertDetails(imageCart, titleCart, dealId, dealOptionIdCart, priceCart, boughtCart, availableStocks, "1");
                    txtAddRemoveCart.setText("Remove from Cart");
                }
                else {
                    dbHelper.removeCart(dealId);
                    txtAddRemoveCart.setText("Add to Cart");
                }

            }
        });


        listOfDealsRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (islistClicked){
                    dealsOptionList.setVisibility(View.VISIBLE);
                    islistClicked = false;
                    listArrow.setImageResource(R.drawable.top);
                }
                else {
                    dealsOptionList.setVisibility(View.GONE);
                    islistClicked = true;
                    listArrow.setImageResource(R.drawable.dropdown);
                }
            }
        });


        backToHomeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.shrImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse("android.resource://com.android.test/*");
                try {
                    InputStream stream = getContentResolver().openInputStream(screenshotUri);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));


            }
        });

        detailsProductTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTextViewClicked){
                    //This will shrink textview to 2 lines if it is expanded.
                    detailsProductTxt.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = false;
                    arrowDetailsExpand.setImageResource(R.drawable.top);
                } else {
                    //This will expand the textview if it is of 2 lines
                    detailsProductTxt.setMaxLines(2);
                    isTextViewClicked = true;
                    arrowDetailsExpand.setImageResource(R.drawable.dropdown);
                }
            }
        });

        arrowDetailsExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsProductTxt.performClick();
            }
        });


        findViewById(R.id.giveAsAGift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Activity_ProductDetails.this, Activity_GiveAsGift.class);
                intent.putExtra("shipping_address_id", shippingAddressId);
                intent.putExtra("dealOptionIdCart", dealOptionIdCart);
                intent.putExtra("priceCart", priceCart);
                intent.putExtra("bucksInWallet", bucksInWallet);

                startActivity(intent);
            }
        });

        productFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addWishList(dealId, isWishlist);
            }
        });

        resProductUserReview.setLayoutManager(new LinearLayoutManager(this));
        adapterReviewProductDetails = new AdapterReviewProductDetails(Activity_ProductDetails.this,reviewModelArrayList);
        resProductUserReview.setAdapter(adapterReviewProductDetails);

        btnBuyNowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_ProductDetails.this, Activity_ReviewOrder.class);
                intent.putExtra("valueForOrder", "fromProductDetails");
                intent.putExtra("dealOptionId", dealOptionIdCart);
                startActivity(intent);
            }
        });

        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWishList(dealId, isWishlist);
            }
        });

        removeFromWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWishList(dealId, isWishlist);
            }
        });


        dealsOptionList.setLayoutManager(new LinearLayoutManager(this));
        adapterDealsOptionList = new AdapterDealsOptionList(this, dealOptionsListModelArrayList, new AdapterDealsOptionList.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                selectDealsName.setText(dealOptionsListModelArrayList.get(item).getDeal_option_name());
                selectDealsPrice.setText("$ " + dealOptionsListModelArrayList.get(item).getSell_price());

                dealOptionIdCart = dealOptionsListModelArrayList.get(item).getDeal_option_id();

                availableStocks = dealOptionsListModelArrayList.get(item).getAvailable_stock_qty();

                Toast.makeText(Activity_ProductDetails.this, availableStocks, Toast.LENGTH_SHORT).show();

                Log.e("cdfdefedf", dealOptionIdCart + "--");
            }
        });
        dealsOptionList.setAdapter(adapterDealsOptionList);



        getDealDetials(dealId);


        findViewById(R.id.seeAllReviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReviewDetails(dealId, "1");
            }
        });

        for (int t = 0; t < detailsModelArrayList.size(); t++){
            if (detailsModelArrayList.get(t).getDeal_id().equals(dealId)){
                txtAddRemoveCart.setText("Remove from Cart");
            }

            Log.e("sfsfsdf", detailsModelArrayList.get(t).getDeal_id() +"--" + dealId);

        }

    }

    public void getDealDetials(String dealId){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ProductDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "deal-details?deal_id=" + dealId + "&sort_by_rating=HighestRated", new Response.Listener<NetworkResponse>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = data.getJSONArray("customer_reviews");
                            JSONObject jsonObject1 = data.getJSONObject("deal_details");
                            JSONArray jsonArray1 = data.getJSONArray("deal_options");

                            shippingAddressId = data.getString("shipping_address_id");


                            for (int i = 0; i < jsonArray1.length(); i++){
                                JSONObject object = jsonArray1.getJSONObject(i);

                                DealOptionsListModel dealOptionsListModel = new DealOptionsListModel();
                                dealOptionsListModel.setDeal_id(object.getString("deal_id"));
                                dealOptionsListModel.setDeal_option_id(object.getString("deal_option_id"));
                                dealOptionsListModel.setDeal_option_name(object.getString("deal_option_name"));
                                dealOptionsListModel.setSell_price(object.getString("sell_price"));
                                dealOptionsListModel.setDeal_option_description(object.getString("deal_option_description"));
                                dealOptionsListModel.setAvailable_stock_qty(object.getString("available_stock_qty"));


                                dealOptionsListModelArrayList.add(dealOptionsListModel);
                            }

                            dealOptionIdCart = dealOptionsListModelArrayList.get(0).getDeal_option_id();

                            selectDealsName.setText(dealOptionsListModelArrayList.get(0).getDeal_option_name());
                            selectDealsPrice.setText("$ " + dealOptionsListModelArrayList.get(0).getSell_price());

                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                ReviewModel reviewModel = new ReviewModel();
                                reviewModel.setDeal_rating_id( object.getString("deal_rating_id"));
                                reviewModel.setFirst_name( object.getString("first_name"));
                                reviewModel.setLast_name( object.getString("last_name"));
                                reviewModel.setDeal_id( object.getString("deal_id"));
                                reviewModel.setUser_id( object.getString("user_id"));
                                reviewModel.setRating( object.getString("rating"));
                                reviewModel.setReview( object.getString("review"));
                                reviewModel.setDate( object.getString("date"));
                                reviewModelArrayList.add(reviewModel);

                            }

                            if (reviewModelArrayList.isEmpty()){
                                noReviewsText.setVisibility(View.VISIBLE);
                                findViewById(R.id.seeAllReviews).setVisibility(View.INVISIBLE);
                            }


                            ratingBarProductDetailsMain.setStepSize(0.1f);
                            ratingBarProductDetailsMain.setRating(Float.parseFloat(jsonObject1.getString("avg_rating")));
                            ratingBarProductDetailsMain.setIsIndicator(true);

                            Picasso.get().load(jsonObject1.getString("deal_image")).
                                    placeholder(Activity_ProductDetails.this.getResources().getDrawable(R.drawable.appicon_1024x1024))
                                    .error(Activity_ProductDetails.this.getResources().getDrawable(R.drawable.appicon_1024x1024)).into(dealImage);

                            dealTitle.setText(jsonObject1.getString("title"));
                            totalRating.setText(jsonObject1.getString("total_rating") + " ratings");
                            rating.setText(jsonObject1.getString("avg_rating"));
                            highLights.setText(jsonObject1.getString("description"));

                            customeR.setText(jsonObject1.getString("avg_rating"));
                            ratingAgainProduct.setStepSize(0.1f);
                            ratingAgainProduct.setRating(Float.parseFloat(jsonObject1.getString("avg_rating")));
                            ratingAgainProduct.setIsIndicator(true);
                            countRtng.setText(jsonObject1.getString("total_rating") + " ratings");

                            imageCart = jsonObject1.getString("deal_image");
                            titleCart = jsonObject1.getString("title");
                            boughtCart = jsonObject1.getString("bought");
                            priceCart = jsonObject1.getString("sell_price");
                            availableStocks = dealOptionsListModelArrayList.get(0).getAvailable_stock_qty();

                            isWishlist = jsonObject1.getString("is_wishlist");


                            getSnagpauBucks();


                            adapterReviewProductDetails.notifyDataSetChanged();
                            adapterDealsOptionList.notifyDataSetChanged();


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

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

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



    private void addWishList(String dealId, String wishStatus) {
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
                    } else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

                    /*session.logout();
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


    public void getReviewDetails(String dealId, String page){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ProductDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-all-deal-reviews?deal_id=" + dealId + "&sort_by_rating=HighestRated?page=" + page, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();
                findViewById(R.id.seeAllReviews).setVisibility(View.GONE);

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("data");

                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                ReviewModel reviewModel = new ReviewModel();
                                reviewModel.setDeal_rating_id( object.getString("deal_rating_id"));
                                reviewModel.setFirst_name( object.getString("first_name"));
                                reviewModel.setLast_name( object.getString("last_name"));
                                reviewModel.setDeal_id( object.getString("deal_id"));
                                reviewModel.setUser_id( object.getString("user_id"));
                                reviewModel.setRating( object.getString("rating"));
                                reviewModel.setReview( object.getString("review"));
                                reviewModel.setDate( object.getString("date"));
                                reviewModelArrayList.add(reviewModel);


                            }

                            adapterReviewProductDetails.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

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

    public void getSnagpauBucks(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ProductDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
             //   .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "credit-available-in-wallet"
                , new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


            //    progressDialog.dismiss();
                findViewById(R.id.seeAllReviews).setVisibility(View.GONE);

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");


                            bucksInWallet = data.getString("trade_credit");

                            adapterReviewProductDetails.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_ProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_ProductDetails.this, "No Data", Toast.LENGTH_SHORT).show();

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
               //         progressDialog.dismiss();

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


}