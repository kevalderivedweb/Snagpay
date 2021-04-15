package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Activity.Activity_SnagpayWallet;
import com.example.snagpay.Adapter.AdapterMonthlyViewPayment;
import com.example.snagpay.Adapter.ExpListAdapterPaymentRecent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.MonthlyView;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.Model.PaymentRecent;
import com.example.snagpay.R;
import com.example.snagpay.Utils.EndlessRecyclerViewScrollListener;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_PaymentRecent extends Fragment {

  //  private ExpListAdapterPaymentRecent listAdapterPayment;
  //  private ExpandableListView expListViewPayment;
    private ArrayList<MonthlyView> paymentModelArrayList = new ArrayList<>();
    private UserSession session;

    private AdapterMonthlyViewPayment adapterMonthlyViewPayment;

    private RecyclerView recRecent;

    private int last_size;
    private String Mpage = "1";
    private LinearLayoutManager linearlayout;


    public Fragment_PaymentRecent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_fragment_payment_recent, container, false);
        session = new UserSession(getContext());

        recRecent = view.findViewById(R.id.recRecent);

        linearlayout = new LinearLayoutManager(getContext());
        recRecent.setLayoutManager(linearlayout);
        adapterMonthlyViewPayment = new AdapterMonthlyViewPayment(getContext(), paymentModelArrayList);
        recRecent.setAdapter(adapterMonthlyViewPayment);

        recRecent.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearlayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e("PageStatus",page + "  " + last_size);

                if (page!=last_size){
                    Mpage = String.valueOf(page+1);

                    getRecentDetails(Mpage);
                }

            }
        });

       /* expListViewPayment = (ExpandableListView) view.findViewById(R.id.lvExpPayment);
        expListViewPayment.setChildDivider(getResources().getDrawable(R.color.white));

        // preparing list data


        listAdapterPayment = new ExpListAdapterPaymentRecent(getContext(), paymentModelArrayList);
        // setting list adapter
        expListViewPayment.setAdapter(listAdapterPayment);
        // Listview Group click listener



        expListViewPayment.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        // Listview on child click listener
        expListViewPayment.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });*/

        getRecentDetails("1");


        return view;

    }

    public void getRecentDetails(String mPage){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "recent-payment-history?page=" + mPage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");

                            last_size = data.getInt("last_page");

                            JSONArray jsonArray = data.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject11 = jsonArray.getJSONObject(i);

                                MonthlyView monthlyView = new MonthlyView();
                                monthlyView.setE_wallet_id(jsonObject11.getString("e_wallet_id"));
                                monthlyView.setE_wallet_tran_code(jsonObject11.getString("e_wallet_tran_code"));
                                monthlyView.setWallet_credit(jsonObject11.getString("wallet_credit"));
                                monthlyView.setBalance(jsonObject11.getString("balance"));
                                monthlyView.setTransaction_title(jsonObject11.getString("transaction_title"));
                                monthlyView.setDatetime(jsonObject11.getString("datetime"));
                                monthlyView.setTransaction_type(jsonObject11.getString("transaction_type"));

                                paymentModelArrayList.add(monthlyView);
                            }


                           /* for (int i = 0 ; i<jsonArray.length() ; i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                PaymentRecent paymentRecent = new PaymentRecent();
                                paymentRecent.setTransaction_title(object.getString("transaction_title"));
                                paymentRecent.setTransaction_type(object.getString("transaction_type"));
                                paymentRecent.setDatetime(object.getString("datetime"));
                                paymentRecent.setWallet_credit("$"+ object.getString("wallet_credit"));


                                ArrayList<DetailPaymentModel> detailPaymentModels = new ArrayList<>();

                                DetailPaymentModel detailPaymentModel = new DetailPaymentModel();
                                detailPaymentModel.setCardType("Credit Card");
                                detailPaymentModel.setE_wallet_tran_code(object.getString("e_wallet_tran_code"));

                                detailPaymentModels.add(detailPaymentModel);

                                paymentRecent.setDetailPaymentModels(detailPaymentModels);
                                paymentModelArrayList.add(paymentRecent);

                            }*/



                            adapterMonthlyViewPayment.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(getContext(), Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Activity_SnagpayWallet.this, "No Data", Toast.LENGTH_SHORT).show();

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

}