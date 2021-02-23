package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.ExpListAdapterPaymentRecent;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_SnagpayWallet extends AppCompatActivity {

    private ExpListAdapterPaymentRecent listAdapterPayment;
    private ExpandableListView expListViewPayment;
    private ArrayList<PaymentModel> paymentModelArrayList = new ArrayList<>();

    private RelativeLayout rltvRequestStatement;
    private UserSession session;
    private TextView dollerWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snagpay_wallet);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_SnagpayWallet.this);


        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.rltvRequestStatement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_SnagpayWallet.this, Activity_Statement.class));
            }
        });

         findViewById(R.id.rltvAddMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SnagpayWallet.this, Activity_AddMoneyWallet.class);
                intent.putExtra("price",dollerWallet.getText().toString());
                startActivity(intent);
            }
        });


        dollerWallet = findViewById(R.id.dollerWallet);
        expListViewPayment = findViewById(R.id.lvExpWallet);
        expListViewPayment.setChildDivider(getResources().getDrawable(R.color.white));


        // preparing list data


        listAdapterPayment = new ExpListAdapterPaymentRecent(Activity_SnagpayWallet.this, paymentModelArrayList);
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

              //  setListViewHeight(parent, groupPosition);

                return false;
            }
        });



      //  prepareListData();

        // Listview on child click listener
        expListViewPayment.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });

    //    getWalletDetails();

    }


    private void prepareListData() {

        for (int i=0; i<8; i++){
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setStatusPayment("Paid on SNAGpay");
            paymentModel.setDate("18 Sept 2020, 10:04 AM");
            paymentModel.setPrice("$120");
            paymentModel.setCard("Visa card");

            ArrayList<DetailPaymentModel> detailPaymentModels = new ArrayList<>();

            DetailPaymentModel detailPaymentModel = new DetailPaymentModel();
            detailPaymentModel.setCardType("Credit Card");
            detailPaymentModel.setOrderId("123-12345645-4565");

            detailPaymentModels.add(detailPaymentModel);

            paymentModel.setDetailPaymentModels(detailPaymentModels);
            paymentModelArrayList.add(paymentModel);
        }
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    public void getWalletDetails(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_SnagpayWallet.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-snagpay-wallet", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                paymentModelArrayList.clear();
                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));
                    Log.e("Response",jsonObject.toString());
                    if (jsonObject.getString("ResponseCode").equals("200")){

                        try {

                            JSONObject data = jsonObject.getJSONObject("data");

                            String Wallet_txt = data.getString("trade_credit");
                            dollerWallet.setText(Wallet_txt);
                            JSONArray sub_transaction = data.getJSONArray("recent_transactions");


                            for (int i = 0 ; i<sub_transaction.length() ; i++){
                                JSONObject object = sub_transaction.getJSONObject(i);

                                PaymentModel paymentModel = new PaymentModel();
                                paymentModel.setStatusPayment(object.getString("transaction_title"));
                                paymentModel.setDate(object.getString("created_at"));
                                paymentModel.setPrice("$ "+object.getString("trade_credit"));
                                paymentModel.setCard(object.getString("transaction_type"));


                                ArrayList<DetailPaymentModel> detailPaymentModels = new ArrayList<>();

                                DetailPaymentModel detailPaymentModel = new DetailPaymentModel();
                                detailPaymentModel.setCardType("Credit Card");
                                detailPaymentModel.setOrderId("123-12345645-4565");

                                detailPaymentModels.add(detailPaymentModel);

                                paymentModel.setDetailPaymentModels(detailPaymentModels);
                                paymentModelArrayList.add(paymentModel);

                            }

                            listAdapterPayment.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_SnagpayWallet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(jsonObject.getString("ResponseCode").equals("401")){

                        session.logout();
                        Intent intent = new Intent(Activity_SnagpayWallet.this, Activity_SelectCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                      Toast.makeText(Activity_SnagpayWallet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Activity_SnagpayWallet.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_SnagpayWallet.this).add(volleyMultipartRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getWalletDetails();
    }
}