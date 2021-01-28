package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.snagpay.Adapter.AdapterWishlist;
import com.example.snagpay.Adapter.AdapterWishlistRecent;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_WishListAcivity extends Fragment {

    private RecyclerView resFragWishList,resFragRecentlyViewed;
    private AdapterWishlist adapterWishlist;
    private AdapterWishlistRecent adapterWishlistRecent;

    private RelativeLayout rltvWishListTopBar, rltvWishListTopBarDeleteCancel;
    private TextView txtEditWishlistTopBar;
    private ImageView imgCancelWishlishTopBar;

    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList = new ArrayList<>();
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayRecent = new ArrayList<>();

    private UserSession session;

    public Fragment_WishListAcivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_wish_list_acivity, container, false);

        session = new UserSession(getContext());

        imgCancelWishlishTopBar = view.findViewById(R.id.imgCancelWishlishTopBar);
        rltvWishListTopBar = view.findViewById(R.id.rltvWishListTopBar);
        rltvWishListTopBarDeleteCancel = view.findViewById(R.id.rltvWishListTopBarDeleteCancel);
        txtEditWishlistTopBar = view.findViewById(R.id.txtEditWishlistTopBar);

        resFragWishList = view.findViewById(R.id.resFragWishList);
        resFragRecentlyViewed = view.findViewById(R.id.resFragRecentlyViewed);

        getWishlist();

        resFragWishList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterWishlist = new AdapterWishlist(getActivity(), categoryDetailsModelArrayList);
        resFragWishList.setAdapter(adapterWishlist);

        resFragRecentlyViewed.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterWishlistRecent = new AdapterWishlistRecent(getActivity(), categoryDetailsModelArrayRecent);
        resFragRecentlyViewed.setAdapter(adapterWishlistRecent);


        // for top bar hidden
        txtEditWishlistTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltvWishListTopBar.setVisibility(View.GONE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.VISIBLE);
            }
        });

        imgCancelWishlishTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rltvWishListTopBar.setVisibility(View.VISIBLE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void getWishlist(){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-wishlist",
                new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONObject jsonObject1 = data.getJSONObject("wishlist");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            //  JSONObject recentViews = data.getJSONObject("recently_viewed");
                            //  JSONArray jsonArrayRecent = recentViews.getJSONArray("data");



                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();
                                categoryDetailsModel.setDeal_image(object.getString("deal_image"));
                                categoryDetailsModel.setTitle(object.getString("title"));
                                categoryDetailsModel.setSell_price(object.getString("sell_price"));
                                categoryDetailsModel.setBought(object.getString("bought"));
                                categoryDetailsModel.setMain_category_id(object.getString("main_category_id"));
                                categoryDetailsModel.setCategory_id(object.getString("category_id"));

                                categoryDetailsModelArrayList.add(categoryDetailsModel);
                            }

                            Log.e("ree", categoryDetailsModelArrayList.get(0).getTitle() + "--" + categoryDetailsModelArrayList.get(0).getSell_price()
                                    + categoryDetailsModelArrayList.get(0).getBought());

                            Log.e("daaaaata", jsonObject1.toString() + "  --");

                            adapterWishlist.notifyDataSetChanged();

                            for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();
                                categoryDetailsModel.setDeal_image(object.getString("deal_image"));
                                categoryDetailsModel.setTitle(object.getString("title"));
                                categoryDetailsModel.setSell_price(object.getString("sell_price"));
                                categoryDetailsModel.setBought(object.getString("bought"));
                                categoryDetailsModel.setMain_category_id(object.getString("main_category_id"));
                                categoryDetailsModel.setCategory_id(object.getString("category_id"));

                                categoryDetailsModelArrayRecent.add(categoryDetailsModel);
                            }
                            adapterWishlistRecent.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("rsp", e.getMessage() + "--");
                        }
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();

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