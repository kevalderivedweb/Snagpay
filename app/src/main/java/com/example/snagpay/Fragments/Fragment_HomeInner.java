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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_FilterSortBy;
import com.example.snagpay.Adapter.AdapterHomeInner;
import com.example.snagpay.Activity.Activity_ProductDetails;
import com.example.snagpay.Model.CategoryDetailsModel;
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

public class Fragment_HomeInner extends Fragment {

    private RecyclerView recHomeInner;
    private AdapterHomeInner adapterHomeInner;
    private String category_id;
    private UserSession session;
    public static ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList;

    private ShimmerFrameLayout mShimmerViewContainer;
    private String SubCatString;
    private LinearLayout linearFilterSortBy;


    public Fragment_HomeInner(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_home_inner_fragment, container, false);

        session = new UserSession(getContext());
        categoryDetailsModelArrayList = new ArrayList<>();

        getCategoriesDetails(category_id, "", "", "", "");

        recHomeInner = view.findViewById(R.id.recHomeInner);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        recHomeInner.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapterHomeInner = new AdapterHomeInner(getContext(), categoryDetailsModelArrayList, new AdapterHomeInner.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                startActivity(new Intent(getContext(), Activity_ProductDetails.class));
            }
        });
        recHomeInner.setAdapter(adapterHomeInner);


        linearFilterSortBy = view.findViewById(R.id.linearFilterSortBy);

        linearFilterSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), Activity_FilterSortBy.class);
                intent.putExtra("category",SubCatString);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String mShort=data.getStringExtra("listShort").replace(" ", "");
            String mCategory=data.getStringExtra("listCategory");
            String mPrice=data.getStringExtra("listPrice");

            String[] separated = mPrice.split("-");
            String startPrice =  separated[0].replace("$", "");
            String endPrice =  separated[1].replace("$", "");

            getCategoriesDetails(category_id, mShort, mCategory, startPrice, endPrice);
        }
    }

    public void getCategoriesDetails(String category_id, String mShort, String mCategory, String startPrice, String endPrice){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
                //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "category-details?category_id="+category_id
                +"&sort_by_deals="+mShort
                +"&filter_category_id="+mCategory
                +"&from_price_range="+startPrice
                +"&to_price_range="+endPrice, new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.e("dataa", category_id + "---"+ mShort + "---"+ mCategory + "---" + startPrice + "---" + endPrice);

                        progressDialog.dismiss();
                        categoryDetailsModelArrayList.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONObject jsonObject1 = data.getJSONObject("deals");
                                    JSONArray sub_categories = data.getJSONArray("sub_categories");
                                    
                                    SubCatString = sub_categories.toString();

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
                                        categoryDetailsModel.setRegular_price(object.getString("regular_price"));
                                        categoryDetailsModel.setBought(object.getString("bought"));

                                        categoryDetailsModelArrayList.add(categoryDetailsModel);
                                    }

                                    Log.e("servicess", categoryDetailsModelArrayList.get(0).getTitle() + "---" +
                                            categoryDetailsModelArrayList.get(0).getCity_name() + "---" +
                                            categoryDetailsModelArrayList.get(0).getState_name() + "---" +
                                            categoryDetailsModelArrayList.get(0).getAvg_rating() + "---" +
                                            categoryDetailsModelArrayList.get(0).getRegular_price());
                                    adapterHomeInner.notifyDataSetChanged();

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