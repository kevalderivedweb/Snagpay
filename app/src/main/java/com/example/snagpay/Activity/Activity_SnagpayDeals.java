package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.EndlessRecyclerViewScrollListener;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_SnagpayDeals extends AppCompatActivity {

    private RecyclerView recDeals;
    private AdapterHomeInner adapterHomeInner;
    private UserSession session;
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList = new ArrayList<>();

    private int last_size;
    private String Mpage = "1";

    private LinearLayoutManager linearlayout;

    private TextView availBucksDeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_deals);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_SnagpayDeals.this);

        recDeals = findViewById(R.id.recDeals);
        availBucksDeals = findViewById(R.id.availBucksDeals);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearlayout = new GridLayoutManager(this, 2);
        recDeals.setLayoutManager(linearlayout);
        adapterHomeInner = new AdapterHomeInner(Activity_SnagpayDeals.this, categoryDetailsModelArrayList, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

                Bundle bundle = new Bundle();
                bundle.putString("dealId", categoryDetailsModelArrayList.get(item).getDeal_id());

                Intent intent = new Intent(Activity_SnagpayDeals.this, Activity_ProductDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recDeals.setAdapter(adapterHomeInner);

        recDeals.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearlayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e("PageStatus",page + "  " + last_size);
                if (page!=last_size){
                    Mpage = String.valueOf(page+1);

                    getCategoriesDetailsDeals(Mpage);

                }
            }
        });

        getCategoriesDetailsDeals("1");

        getCreditDetails();

    }

    public void getCategoriesDetailsDeals(String page){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_SnagpayDeals.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-my-snagpay-deals"
                + "?page=" + page, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject jsonObject1 = data.getJSONObject("deals");

                            last_size = jsonObject1.getInt("last_page");


                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();
                                categoryDetailsModel.setDeal_image(object.getString("deal_image"));
                                categoryDetailsModel.setTitle(object.getString("title"));
                                categoryDetailsModel.setCity_name(object.getString("city_name"));
                                categoryDetailsModel.setState_name(object.getString("state_name"));
                                categoryDetailsModel.setTotal_rating(object.getString("total_rating"));
                                categoryDetailsModel.setAvg_rating(object.getString("avg_rating"));
                                categoryDetailsModel.setCategory_id(object.getString("category_id"));
                                categoryDetailsModel.setSell_price(object.getString("sell_price"));
                                categoryDetailsModel.setBought(object.getString("bought"));
                                categoryDetailsModel.setDeal_id(object.getString("deal_id"));
                                categoryDetailsModel.setIs_wishlist(object.getString("is_wishlist"));

                                categoryDetailsModelArrayList.add(categoryDetailsModel);
                            }

                            Log.e("servicess", categoryDetailsModelArrayList.get(0).getDeal_id() + "---" +
                                    categoryDetailsModelArrayList.get(0).getIs_wishlist() + "---" +
                                    categoryDetailsModelArrayList.get(0).getState_name() + "---" +
                                    categoryDetailsModelArrayList.get(0).getAvg_rating() + "---" +
                                    categoryDetailsModelArrayList.get(0).getSell_price());

                            adapterHomeInner.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_SnagpayDeals.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_SnagpayDeals.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Activity_SnagpayDeals.this, "No Data", Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(Activity_SnagpayDeals.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_SnagpayDeals.this).add(volleyMultipartRequest);
    }

    public void getCreditDetails(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_SnagpayDeals.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
               // .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "credit-available-in-wallet", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


              //  progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){


                        String number = jsonObject.getJSONObject("data").getString("trade_credit");
                        double amount = Double.parseDouble(number);
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String formatted = formatter.format(amount);
                        availBucksDeals.setText(formatted);


                        Log.e("sdsdsd", "$" + jsonObject.getJSONObject("data").getString("trade_credit"));


                        Toast.makeText(Activity_SnagpayDeals.this,jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_SnagpayDeals.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(Activity_SnagpayDeals.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                   //     progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(Activity_SnagpayDeals.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_SnagpayDeals.this).add(volleyMultipartRequest);

    }



}