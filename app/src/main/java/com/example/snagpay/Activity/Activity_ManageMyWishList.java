package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterAddAnotherCity;
import com.example.snagpay.Adapter.CategorySaveWishListAdapter;
import com.example.snagpay.Adapter.CityNameListAdapter;
import com.example.snagpay.Model.CityModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_ManageMyWishList extends AppCompatActivity {

    private RecyclerView recAddAnotherCity, recListCategory;
    private Button btnAddAnotherCity, btnAddNewCategory;

    private ArrayList<CityModel> mDataCity = new ArrayList<>();
    private ArrayList<CityModel> addCityList = new ArrayList<>();
    private ArrayList<CityModel> categoryArrayList = new ArrayList<>();
    private UserSession session;

    private CityNameListAdapter cityNameListAdapter;
    private AdapterAddAnotherCity adapterAddAnotherCity1;
    private CategorySaveWishListAdapter categoryWishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_wish_list);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ManageMyWishList.this);

        findViewById(R.id.backToFragMyStuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recAddAnotherCity = findViewById(R.id.recAddAnotherCity);
        recListCategory = findViewById(R.id.recListCategory);
        btnAddAnotherCity = findViewById(R.id.btnAddAnotherCity);
        btnAddNewCategory = findViewById(R.id.btnAddNewCategory);

        recAddAnotherCity.setLayoutManager(new LinearLayoutManager(Activity_ManageMyWishList.this));
        cityNameListAdapter = new CityNameListAdapter(this, addCityList, new CityNameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });
        recAddAnotherCity.setAdapter(cityNameListAdapter);

        recListCategory.setLayoutManager(new LinearLayoutManager(Activity_ManageMyWishList.this));
        categoryWishListAdapter = new CategorySaveWishListAdapter(this, categoryArrayList, new CategorySaveWishListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

            }
        });
        recListCategory.setAdapter(categoryWishListAdapter);


        btnAddAnotherCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Activity_ManageMyWishList.this);
                dialog.setContentView(R.layout.custom_dialog_city);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);



                RecyclerView recyclerViewCity = dialog.findViewById(R.id.dialogCityList);
                recyclerViewCity.setLayoutManager(new LinearLayoutManager(dialog.getContext()));

                adapterAddAnotherCity1 = new AdapterAddAnotherCity(Activity_ManageMyWishList.this, mDataCity, addCityList, new AdapterAddAnotherCity.OnItemClickListener() {
                    @Override
                    public void onItemClick(int item) {

                    }
                });
                recyclerViewCity.setAdapter(adapterAddAnotherCity1);

                dialog.findViewById(R.id.dialogCityClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.btnDialogAddCity).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityNameListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Activity_ManageMyWishList.this);
                dialog.setContentView(R.layout.custom_dialog_category);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                String[] Home = {"Home"};
                String[] Service = {"Service"};
                String[] Gardener = {"Gardener"};

                Spinner spinnerService = dialog.findViewById(R.id.spinnerService);
                Spinner spinnerHome = dialog.findViewById(R.id.spinnerHome);
                Spinner spinnerGardener = dialog.findViewById(R.id.spinnerGardener);

                ArrayAdapter<String> dataAdapterService = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Service);
                dataAdapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerService.setAdapter(dataAdapterService);

                ArrayAdapter<String> dataAdapterHome = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Home);
                dataAdapterHome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHome.setAdapter(dataAdapterHome);

                ArrayAdapter<String> dataAdapterGardener = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Gardener);
                dataAdapterGardener.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGardener.setAdapter(dataAdapterGardener);

                dialog.findViewById(R.id.dialogCategoryClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnDialogAddCategory).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        getCategories();
        getCity();

    }

    private void getCategories(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ManageMyWishList.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //   .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-my-wishlist",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString() + " --");
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonArray = jsonObject.getJSONArray("city");

                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        CityModel cityModel = new CityModel();
                                        cityModel.setCityId(object.getString("city_id"));
                                        cityModel.setCityname(object.getString("city_name"));

                                        addCityList.add(cityModel);
                                    }
                                    cityNameListAdapter.notifyDataSetChanged();

                                    JSONArray jsonArrayCategory = jsonObject.getJSONArray("category");

                                    for (int i = 0 ; i<jsonArrayCategory.length() ; i++){
                                        JSONObject object = jsonArrayCategory.getJSONObject(i);

                                        CityModel cityModel = new CityModel();
                                        cityModel.setCityId(object.getString("category_id"));
                                        cityModel.setCityname(object.getString("category_name"));

                                        categoryArrayList.add(cityModel);
                                    }
                                    categoryWishListAdapter.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){


                            }

                        } catch (Exception e) {
                            Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ManageMyWishList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_ManageMyWishList.this).add(volleyMultipartRequest);
    }

    private void getCity() {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ManageMyWishList.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-cities?state_id=2",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        CityModel BatchModel = new CityModel();
                                        BatchModel.setCityname(object.getString("city_name"));
                                        BatchModel.setCityId(object.getString("city_id"));
                                        mDataCity.add(BatchModel);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_ManageMyWishList.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            Toast.makeText(Activity_ManageMyWishList.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ManageMyWishList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                // params.put("Authorization", "Bearer " + session.getAPIToken());
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
        volleyMultipartRequest.setShouldRetryServerErrors(true);

        Volley.newRequestQueue(Activity_ManageMyWishList.this).add(volleyMultipartRequest);
    }


    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}