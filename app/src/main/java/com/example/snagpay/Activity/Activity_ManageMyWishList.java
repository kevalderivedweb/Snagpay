package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.example.snagpay.Model.CityModelSub;
import com.example.snagpay.Model.CityModelSubSub;
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
    private Dialog dialogForCity;

    private ArrayList<CityModel> mDataCity = new ArrayList<>();
    private ArrayList<CityModel> addCityList = new ArrayList<>();
    private ArrayList<CityModel> categoryArrayList = new ArrayList<>();

    private ArrayList<String> arrayCityId = new ArrayList<>();
    private ArrayList<String> arrayCategoryId = new ArrayList<>();
    private UserSession session;

    private CityNameListAdapter cityNameListAdapter;
    private AdapterAddAnotherCity adapterAddAnotherCity1;
    private CategorySaveWishListAdapter categoryWishListAdapter;

    private String citiesName;
    private String categoryName;

    private ArrayList<CityModel> mainCategoryArrayList = new ArrayList<>();
    private ArrayList<CityModelSub> subCategoryArrayList;
    private ArrayList<CityModelSubSub> subSubCategoryArrayList;

    private ArrayList<String> mainCategoryArrayListName = new ArrayList<>();
    private ArrayList<String> subCategoryArrayListName = new ArrayList<>();
    private ArrayList<String> subSubCategoryArrayListName = new ArrayList<>();

    private int posCategory = 0;
    private int posSubCategory = 0;
    private ArrayAdapter<String> dataAdapterSubCat;
    private ArrayAdapter<String> dataAdapterSubSubCat;

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

                String removeCity = addCityList.get(item).getCityId();

                mDataCity.get(item).setChecked(false);

                arrayCityId.remove(removeCity);

                String deleteCity = TextUtils.join(",", arrayCityId);
                Log.e("jussss", arrayCityId.toString() + " ");

                manageMyWishlist(deleteCity); // when api of remove will come then applied here...

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
                dialogForCity = new Dialog(Activity_ManageMyWishList.this);
                dialogForCity.setContentView(R.layout.custom_dialog_city);
                dialogForCity.setCancelable(true);
                dialogForCity.setCanceledOnTouchOutside(true);
                dialogForCity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialogForCity.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                Log.e("seperateCityId", arrayCityId.toString() + "--");
                RecyclerView recyclerViewCity = dialogForCity.findViewById(R.id.dialogCityList);
                recyclerViewCity.setLayoutManager(new LinearLayoutManager(dialogForCity.getContext()));
                recyclerViewCity.setAdapter(adapterAddAnotherCity1);

                adapterAddAnotherCity1.notifyDataSetChanged();

                dialogForCity.findViewById(R.id.dialogCityClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogForCity.dismiss();
                    }
                });

                dialogForCity.findViewById(R.id.btnDialogAddCity).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        citiesName = "";
                        citiesName = TextUtils.join("," , arrayCityId);

                        Log.e("checkCityId", arrayCityId.toString() + "--");
                        Log.e("joined", citiesName + "--");

                        manageMyWishlist(citiesName);

                        dialogForCity.dismiss();

                    }
                });
                dialogForCity.show();
            }
        });

        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogForCategories = new Dialog(Activity_ManageMyWishList.this);
                dialogForCategories.setContentView(R.layout.custom_dialog_category);
                dialogForCategories.setCancelable(true);
                dialogForCategories.setCanceledOnTouchOutside(true);
                dialogForCategories.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialogForCategories.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                mainCategoryArrayListName.clear();

                Spinner spinnerCategory = dialogForCategories.findViewById(R.id.spinnerService);
                Spinner spinnersubCategory = dialogForCategories.findViewById(R.id.spinnerHome);
                Spinner spinnersubsubCategory= dialogForCategories.findViewById(R.id.spinnerGardener);

                for (int i = 0; i < mainCategoryArrayList.size(); i++){
                    mainCategoryArrayListName.add(mainCategoryArrayList.get(i).getCityname());
                }




                ArrayAdapter<String> dataAdapterCategory = new ArrayAdapter<String>(dialogForCategories.getContext(),
                        android.R.layout.simple_spinner_item, mainCategoryArrayListName);
                dataAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(dataAdapterCategory);

                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        posCategory = position;

                        Toast.makeText(Activity_ManageMyWishList.this, "Category", Toast.LENGTH_SHORT).show();

                        if(subCategoryArrayListName.size() > 0){
                            subCategoryArrayListName.clear();
                        }
                        for (int i = 0; i < mainCategoryArrayList.get(position).getCityModelSubArrayList().size(); i++){
                            subCategoryArrayListName.add(mainCategoryArrayList.get(position).getCityModelSubArrayList().get(i).getCategoryName());
                        }

                        Log.e("sizeMain", mainCategoryArrayList.get(position).getCityModelSubArrayList().size() + "--");

                        dataAdapterSubCat.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dataAdapterSubCat = new ArrayAdapter<String>(dialogForCategories.getContext(),
                        android.R.layout.simple_spinner_item, subCategoryArrayListName);

                Log.e("sizzz", subCategoryArrayListName.size() + "--");
                dataAdapterSubCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnersubCategory.setAdapter(dataAdapterSubCat);

                spinnersubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (subSubCategoryArrayListName.size() > 0){
                            subSubCategoryArrayListName.clear();
                        }

                        posSubCategory = position;

                        Toast.makeText(Activity_ManageMyWishList.this, "subCategory", Toast.LENGTH_SHORT).show();

                        Log.e("posCategory", posCategory + "--");

                        for (int i = 0; i < mainCategoryArrayList.get(2).getCityModelSubArrayList().get(2).getCityModelSubSubArrayList().size(); i++){
                            subSubCategoryArrayListName.add(mainCategoryArrayList.get(2)
                            .getCityModelSubArrayList().get(2).getCityModelSubSubArrayList().get(i).getCategoryName());
                        }

                        dataAdapterSubSubCat.notifyDataSetChanged();

                        Log.e("subCategory", subSubCategoryArrayListName.get(0) + "---");

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                dataAdapterSubSubCat = new ArrayAdapter<String>(dialogForCategories.getContext(), android.R.layout.simple_spinner_item, subSubCategoryArrayListName);
                dataAdapterSubSubCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnersubsubCategory.setAdapter(dataAdapterSubSubCat);





                dialogForCategories.findViewById(R.id.dialogCategoryClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForCategories.dismiss();
                    }
                });
                dialogForCategories.findViewById(R.id.btnDialogAddCategory).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForCategories.dismiss();
                    }
                });
                dialogForCategories.show();
            }
        });

        getMyWishlist();
        getCity();

    }

    private void manageMyWishlist(String citiesNameSave) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ManageMyWishList.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "manage-my-wishlist",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {


                            addCityList.clear();

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString() + " --");
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {


                                    arrayCityId.clear();

                                    getMyWishlist();

                                    Toast.makeText(Activity_ManageMyWishList.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }else if(jsonObject.getString("ResponseCode").equals("401")){
                                Toast.makeText(Activity_ManageMyWishList.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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
                params.put("city_ids", citiesNameSave);
                params.put("category_ids", categoryName);

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

    private void getMyWishlist(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ManageMyWishList.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-my-wishlist",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {

                            addCityList.clear();
                            arrayCityId.clear();
                            categoryArrayList.clear();
                            arrayCategoryId.clear();

                            getAllCategories();

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

                                    for (int i = 0; i< categoryArrayList.size(); i++){
                                        arrayCategoryId.add(categoryArrayList.get(i).getCityId());
                                    }

                                    categoryName = TextUtils.join("," , arrayCategoryId);



                                    for (int i = 0; i < addCityList.size(); i++){
                                        arrayCityId.add(addCityList.get(i).getCityId());
                                    }

                                    adapterAddAnotherCity1 = new AdapterAddAnotherCity(Activity_ManageMyWishList.this, mDataCity, arrayCityId, new AdapterAddAnotherCity.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int item) {

                                            if (mDataCity.get(item).isChecked()){
                                                mDataCity.get(item).setChecked(false);
                                                arrayCityId.remove(mDataCity.get(item).getCityId());

                                            } else {
                                                mDataCity.get(item).setChecked(true);
                                                arrayCityId.add(mDataCity.get(item).getCityId());

                                            }
                                            cityNameListAdapter.notifyDataSetChanged();

                                        }
                                    });

                                    adapterAddAnotherCity1.notifyDataSetChanged();

                                    Log.e("seperateCityId", arrayCityId.toString() + "--");

                                    citiesName = TextUtils.join("," , arrayCityId);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){
                                Toast.makeText(Activity_ManageMyWishList.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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

    private void getAllCategories(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ManageMyWishList.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "categories",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString() + " --");
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject object = jsonObject.getJSONObject("data");

                                    JSONArray jsonArray = object.getJSONArray("all_categories");

                                    for (int i = 0; i < 3; i++){
                                        JSONObject object1 = jsonArray.getJSONObject(i);

                                        CityModel cityModel = new CityModel();
                                        cityModel.setCityId(object1.getString("category_id"));
                                        cityModel.setCityname(object1.getString("category_name"));

                                        mainCategoryArrayList.add(cityModel);

                                        JSONArray jsonArray1 = object1.getJSONArray("sub_categories");

                                        subCategoryArrayList = new ArrayList<>();

                                        for (int j = 0; j < jsonArray1.length(); j++){
                                            JSONObject object2 = jsonArray1.getJSONObject(j);

                                            CityModelSub cityModelSub = new CityModelSub();
                                            cityModelSub.setCategoryId(object2.getString("category_id"));
                                            cityModelSub.setCategoryName(object2.getString("category_name"));

                                            subCategoryArrayList.add(cityModelSub);
                                            cityModel.setCityModelSubArrayList(subCategoryArrayList);

                                            subSubCategoryArrayList = new ArrayList<>();

                                            JSONArray jsonArray2 = object2.getJSONArray("sub_sub_categories");

                                            for (int k = 0; k < jsonArray2.length(); k++){
                                                JSONObject object3 = jsonArray2.getJSONObject(k);

                                                CityModelSubSub cityModelSubSub = new CityModelSubSub();
                                                cityModelSubSub.setCategoryId(object3.getString("category_id"));
                                                cityModelSubSub.setCategoryName(object3.getString("category_name"));

                                                subSubCategoryArrayList.add(cityModelSubSub);

                                                cityModelSub.setCityModelSubSubArrayList(subSubCategoryArrayList);
                                            }
                                        }
                                    }



                                    Log.e("daaaaaaataaa", mainCategoryArrayList.get(1).getCityModelSubArrayList().get(0)
                                            .getCityModelSubSubArrayList().get(1).getCategoryName() + "----" );



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ManageMyWishList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){
                                Toast.makeText(Activity_ManageMyWishList.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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






}