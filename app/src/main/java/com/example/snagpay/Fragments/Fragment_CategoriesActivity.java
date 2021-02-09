package com.example.snagpay.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_HomeInner;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.AdapterAllCategories;
import com.example.snagpay.Adapter.AdapterCategoriesItems;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.Model.SubCategoriesModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_CategoriesActivity extends Fragment {

    private RecyclerView recCategories, recAllCategories;
    private AdapterCategoriesItems adapterCategoriesItems;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private ArrayList<CategoryModel> allCategoryArrayList;
    private UserSession session;

    private AdapterAllCategories adapterAllCategories;

    public Fragment_CategoriesActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_categories, container, false);

        recCategories = view.findViewById(R.id.recCategories);
        recAllCategories = view.findViewById(R.id.recAllCategories);
        session = new UserSession(getContext());
        categoryModelArrayList = new ArrayList<>();
        allCategoryArrayList = new ArrayList<>();

        getCategories();

        recCategories.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        adapterCategoriesItems = new AdapterCategoriesItems(view.getContext(), categoryModelArrayList, new AdapterCategoriesItems.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                Intent intent = new Intent(getContext(), Activity_HomeInner.class);
                intent.putExtra("category_id", categoryModelArrayList.get(item).getCategory_id());
                startActivity(intent);
            }
        });
        recCategories.setAdapter(adapterCategoriesItems);

        recAllCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterAllCategories = new AdapterAllCategories(view.getContext(), allCategoryArrayList);
        recAllCategories.setAdapter(adapterAllCategories);

        return view;
    }

    private void getCategories(){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //   .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "categories",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();
                        categoryModelArrayList.clear();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString() + " dd");
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                    JSONArray jsonArray = jsonObject1.getJSONArray("popular_categories");

                                    JSONArray jsonArrayAllCat = jsonObject1.getJSONArray("all_categories");

                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        CategoryModel categoryModel = new CategoryModel();
                                        categoryModel.setCategory_id(object.getString("category_id"));
                                        categoryModel.setCategory_name(object.getString("category_name"));
                                        categoryModel.setSlug(object.getString("slug"));
                                        categoryModel.setBackround_color(object.getString("backround_color"));
                                        categoryModel.setCategory_image(object.getString("category_image"));
                                        categoryModel.setParent_id(object.getString("parent_id"));
                                        categoryModel.setParent_level(object.getString("parent_level"));

                                        categoryModelArrayList.add(categoryModel);
                                    }
                                    adapterCategoriesItems.notifyDataSetChanged();

                                    for (int i = 0; i<jsonArrayAllCat.length(); i++){
                                        JSONObject object = jsonArrayAllCat.getJSONObject(i);

                                        CategoryModel categoryModel = new CategoryModel();
                                        categoryModel.setCategory_id(object.getString("category_id"));
                                        categoryModel.setCategory_name(object.getString("category_name"));

                                        JSONArray jsonArray1 = object.getJSONArray("sub_categories");

                                        ArrayList<SubCategoriesModel> subCategoriesModelArrayList = new ArrayList<>();

                                        for (int j = 0; j<jsonArray1.length(); j++){
                                            JSONObject object1 = jsonArray1.getJSONObject(j);

                                            SubCategoriesModel subCategoriesModel = new SubCategoriesModel();
                                            subCategoriesModel.setSubCategory_id(object1.getString("category_id"));
                                            subCategoriesModel.setSubCategory_name(object1.getString("category_name"));

                                            subCategoriesModelArrayList.add(subCategoriesModel);

                                        }
                                        categoryModel.setSubCategoriesModelArrayList(subCategoriesModelArrayList);

                                        allCategoryArrayList.add(categoryModel);
                                    }
                                    adapterAllCategories.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }



}