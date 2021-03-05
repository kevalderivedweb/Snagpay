package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.snagpay.Activity.Activity_ProductDetails;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.AdapterWishlist;
import com.example.snagpay.Adapter.AdapterWishlistRecent;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.RecentViewModel;
import com.example.snagpay.Model.TitleModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.EndlessRecyclerViewScrollListener;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_WishListAcivity extends Fragment {

    private RecyclerView resFragWishList;
    private AdapterWishlist adapterWishlist;

    private RelativeLayout rltvWishListTopBar, rltvWishListTopBarDeleteCancel;
    private TextView txtEditWishlistTopBar;
    private ImageView imgCancelWishlishTopBar;
    private TextView countWishlist;

    private boolean textInWish = true;

    public ArrayList<Object> categoryDetailsModelArrayList = new ArrayList<>();

    private int editClick = 0;

    private UserSession session;
    /*private int last_size;
    private String Mpage = "1";
    private LinearLayoutManager linearlayout;*/

    private ArrayList<String> dealID = new ArrayList<>();

    public Fragment_WishListAcivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_wish_list, container, false);

        session = new UserSession(getContext());

        imgCancelWishlishTopBar = view.findViewById(R.id.imgCancelWishlishTopBar);
        rltvWishListTopBar = view.findViewById(R.id.rltvWishListTopBar);
        rltvWishListTopBarDeleteCancel = view.findViewById(R.id.rltvWishListTopBarDeleteCancel);
        txtEditWishlistTopBar = view.findViewById(R.id.txtEditWishlistTopBar);
        countWishlist = view.findViewById(R.id.countWishlist);

        resFragWishList = view.findViewById(R.id.resFragWishList);

        getWishlist();


        resFragWishList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterWishlist = new AdapterWishlist(getActivity(), categoryDetailsModelArrayList, new AdapterWishlist.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {

                CategoryDetailsModel bean = (CategoryDetailsModel) categoryDetailsModelArrayList.get(item);

                if (editClick == 1) {

                    if (bean.isSelected()) {
                        bean.setSelected(false);
                        dealID.remove(bean.getDeal_id());
                    } else {
                        dealID.add(bean.getDeal_id());
                        bean.setSelected(true);
                    }
                    adapterWishlist.notifyDataSetChanged();

                } else {
                    
                    Bundle bundle = new Bundle();
                    bundle.putString("category_id", bean.getMain_category_id());
                    bundle.putString("subCategoryId", bean.getCategory_id());

                    Intent intent = new Intent(getContext(), Activity_ProductDetails.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

        adapterWishlist.notifyDataSetChanged();

        resFragWishList.setAdapter(adapterWishlist);

       /* resFragWishList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearlayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e("PageStatus",page + "  " + last_size);
                if (page!=last_size){
                    Mpage = String.valueOf(page+1);

                        getWishlist(Mpage);

                }
            }
        });*/



        view.findViewById(R.id.deleteWishlistItems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dealID.size() == 0){
                    Toast.makeText(getContext(), "No Items Selected", Toast.LENGTH_SHORT).show();
                }else {
                    deleteWishlistItems(dealID);
                }
                Toast.makeText(getContext(), dealID.toString(), Toast.LENGTH_SHORT).show();

                editClick = 0;
                rltvWishListTopBar.setVisibility(View.VISIBLE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.GONE);
            }
        });

        // for top bar hidden
        txtEditWishlistTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editClick = 1;
                rltvWishListTopBar.setVisibility(View.GONE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.VISIBLE);
            }
        });

        imgCancelWishlishTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i<categoryDetailsModelArrayList.size(); i++) {
                    adapterWishlist.deSelectAll(i);
                }

                editClick = 0;
                rltvWishListTopBar.setVisibility(View.VISIBLE);
                rltvWishListTopBarDeleteCancel.setVisibility(View.GONE);
            }
        });

        view.findViewById(R.id.txtSelecetAllWishList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i< categoryDetailsModelArrayList.size(); i++){
                    adapterWishlist.seleceAll(i);
                }
            }
        });

        return view;
    }

    private void deleteWishlistItems(ArrayList<String> dealID) {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "remove-wishlist-deals",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {
                            adapterWishlist.notifyDataSetChanged();

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){
                                categoryDetailsModelArrayList.clear();
                                adapterWishlist.notifyDataSetChanged();
                                getWishlist();
                            }

                            else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        } catch (Exception e) {

                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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

                for (int i = 0; i < dealID.size(); i++) {
                    params.put("deal_ids[" + dealID.get(i) + "]", dealID.get(i));
                }
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

    private void getWishlist() {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
        .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-wishlist",
                new Response.Listener<NetworkResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response", jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")) {

                                try {

                                    JSONObject data = jsonObject.getJSONObject("data");

                                    JSONObject jsonObject1 = data.getJSONObject("wishlist");
                                    JSONArray jsonArray = jsonObject1.getJSONArray("data");
                               //     last_size = jsonObject1.getInt("last_page");

                                    int totalItems = jsonObject1.getInt("total");
                                    countWishlist.setText("Wishlist (" + totalItems + ")");


                                    JSONObject recentViews = data.getJSONObject("recently_viewed");
                                    JSONArray jsonArrayRecent = recentViews.getJSONArray("data");


                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();
                                        categoryDetailsModel.setDeal_id(object.getString("deal_id"));
                                        categoryDetailsModel.setDeal_image(object.getString("deal_image"));
                                        categoryDetailsModel.setTitle(object.getString("title"));
                                        categoryDetailsModel.setSell_price(object.getString("sell_price"));
                                        categoryDetailsModel.setBought(object.getString("bought"));
                                        categoryDetailsModel.setMain_category_id(object.getString("main_category_id"));
                                        categoryDetailsModel.setCategory_id(object.getString("category_id"));
                                        categoryDetailsModel.setIs_wishlist(object.getString("is_wishlist"));
                                        categoryDetailsModel.setSelected(false);

                                        categoryDetailsModelArrayList.add(categoryDetailsModel);
                                    }



                                    Log.e("daaaaata", jsonObject1.toString() + "  --");


                                    TitleModel titleModel = new TitleModel();
                                    titleModel.setTitleWishlist("Recently Viewed");

                                    categoryDetailsModelArrayList.add(titleModel);


                                    for (int i = 0; i < jsonArrayRecent.length(); i++) {
                                        JSONObject object = jsonArrayRecent.getJSONObject(i);

                                        RecentViewModel categoryDetailsModelRecent = new RecentViewModel();
                                        categoryDetailsModelRecent.setDeal_image(object.getString("deal_image"));
                                        categoryDetailsModelRecent.setTitle(object.getString("title"));
                                        categoryDetailsModelRecent.setSell_price(object.getString("sell_price"));
                                        categoryDetailsModelRecent.setBought(object.getString("bought"));
                                        categoryDetailsModelRecent.setMain_category_id(object.getString("main_category_id"));
                                        categoryDetailsModelRecent.setCategory_id(object.getString("category_id"));

                                        categoryDetailsModelArrayList.add(categoryDetailsModelRecent);
                                    }


                                    adapterWishlist.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("rsp", e.getMessage() + "--");
                                }
                            }

                            else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        } catch (Exception e) {

                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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

                Log.e("tokenId", session.getAPITOKEN()+"--");
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