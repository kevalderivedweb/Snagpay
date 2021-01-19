package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_HomeInner;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.AdapterHomeGrid;
import com.example.snagpay.Adapter.SelectCitySpinner;
import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.Model.CityModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_HomeActivity extends Fragment {

    private RecyclerView recHomeInGrid;
    private AdapterHomeGrid resHomeGridAdapter;
    private UserSession session;
    private ArrayList<CategoryModel> categoryModelArrayList;

    private ShimmerFrameLayout mShimmerViewContainer;

    public Fragment_HomeActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//By clicking below, I agree to the Terms of Use and have read the Privacy Statement.

        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);
        session = new UserSession(getContext());
        categoryModelArrayList = new ArrayList<>();

        recHomeInGrid = view.findViewById(R.id.recHomeInGrid);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        getCategories();

        recHomeInGrid.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        resHomeGridAdapter = new AdapterHomeGrid(view.getContext(), categoryModelArrayList, new AdapterHomeGrid.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                Intent intent = new Intent(getContext(), Activity_HomeInner.class);
                intent.putExtra("category_id", categoryModelArrayList.get(item).getCategory_id());
                startActivity(intent);
            }
        });
        recHomeInGrid.setAdapter(resHomeGridAdapter);

        return view;
    }

    public void getCategories(){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
             //   .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-categories",
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

                                    JSONArray jsonArray = jsonObject1.getJSONArray("categories");

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
                                    resHomeGridAdapter.notifyDataSetChanged();

                                    mShimmerViewContainer.stopShimmerAnimation();
                                    mShimmerViewContainer.setVisibility(View.GONE);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
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
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();

    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}