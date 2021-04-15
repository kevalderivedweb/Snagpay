package com.example.snagpay.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.snagpay.Activity.Activity_OrderDetails;
import com.example.snagpay.Activity.Activity_ReviewOrder;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.AdapterOrderCurrent;
import com.example.snagpay.Model.OrderModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.EndlessRecyclerViewScrollListener;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_OrderCurrent extends Fragment {

    private RecyclerView resOrderCurrent;
    private AdapterOrderCurrent adapterOrderCurrent;
    private UserSession session;
    private ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();

    private int last_size;
    private String Mpage = "1";
    private LinearLayoutManager linearlayout;

    public Fragment_OrderCurrent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_order_current, container, false);

        session = new UserSession(getContext());


        resOrderCurrent = view.findViewById(R.id.resOrderCurrent);

        linearlayout = new LinearLayoutManager(getContext());
        resOrderCurrent.setLayoutManager(linearlayout);
        adapterOrderCurrent = new AdapterOrderCurrent(getContext(), orderModelArrayList, new AdapterOrderCurrent.OnItemClickListener() {
            @Override
            public void onItemClickDetails(String orderId) {
                Intent intent = new Intent(getContext(), Activity_OrderDetails.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
        resOrderCurrent.setAdapter(adapterOrderCurrent);

        resOrderCurrent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearlayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e("PageStatus",page + "  " + last_size);
                if (page!=last_size){
                    Mpage = String.valueOf(page+1);

                    getCurrentOrder(Mpage);

                }
            }
        });



        getCurrentOrder("1");

        return view;
    }


    public void getCurrentOrder(String Mpage){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "current-order-history" + "?page=" + Mpage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                orderModelArrayList.clear();
                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());

                    if (jsonObject.getString("ResponseCode").equals("200")){

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        last_size = jsonObject1.getInt("last_page");

                        JSONArray jsonArray = jsonObject1.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);

                            OrderModel orderModel = new OrderModel();
                            orderModel.setDeal_image(jsonObject11.getString("deal_image"));
                            orderModel.setOrder_id(jsonObject11.getString("e_gift_card_id"));
                            orderModel.setBought(jsonObject11.getString("bought"));
                            orderModel.setSell_price(jsonObject11.getString("sell_price"));
                            orderModel.setTitle(jsonObject11.getString("title"));

                            orderModelArrayList.add(orderModel);
                        }

                        adapterOrderCurrent.notifyDataSetChanged();

                        Toast.makeText(getContext(),jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (Exception e) {
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
                        progressDialog.dismiss();

                        Log.e("dssdsd", error.getMessage() + "--");

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentOrder("1");
    }
}