package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.snagpay.Adapter.AdapterMyCart;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.Database;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Cart extends AppCompatActivity {

    private RecyclerView recMyCart;
    private AdapterMyCart adapterMyCart;
    private UserSession session;
    private ArrayList<CategoryDetailsModel> detailsModelArrayList = new ArrayList<>();
    private Database dbHelper;
    private RelativeLayout cartEmpty;
    private Button proceedCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);         //  set status text dark
        session = new UserSession(Activity_Cart.this);

        dbHelper = new Database(Activity_Cart.this);

        detailsModelArrayList = dbHelper.getAllUser();


        for (int h = 0; h < detailsModelArrayList.size(); h++) {
            Log.e("dataArrayCart", detailsModelArrayList.get(h).getShow_deal_option_id() + "--");

        }

        recMyCart = findViewById(R.id.recMyCart);
        cartEmpty = findViewById(R.id.cartEmpty);
        proceedCheck = findViewById(R.id.proceedCheck);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recMyCart.setLayoutManager(new LinearLayoutManager(Activity_Cart.this));
        adapterMyCart = new AdapterMyCart(Activity_Cart.this, detailsModelArrayList, new AdapterMyCart.OnItemClickListener() {
            @Override
            public void onItemClickPlus(int position, String quantity) {
                dbHelper.Update(detailsModelArrayList.get(position).getShow_deal_option_id(), quantity);
            }

            @Override
            public void onItemClickMinus(int position, String quantity) {
                dbHelper.Update(detailsModelArrayList.get(position).getShow_deal_option_id(), quantity);
            }

            @Override
            public void onItemDelete(String dealId, int pos) {
                dbHelper.removeCart(dealId);
                detailsModelArrayList.remove(pos);
                adapterMyCart.notifyDataSetChanged();

                if (detailsModelArrayList.isEmpty()){
                    cartEmpty.setVisibility(View.VISIBLE);
                    proceedCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClickSaveLater(String dealId, int pos) {

                addWishList(dealId);
                dbHelper.removeCart(dealId);
                detailsModelArrayList.remove(pos);
                adapterMyCart.notifyDataSetChanged();

                if (detailsModelArrayList.isEmpty()){
                    cartEmpty.setVisibility(View.VISIBLE);
                    proceedCheck.setVisibility(View.GONE);
                }
            }


        });
        recMyCart.setAdapter(adapterMyCart);


        if (detailsModelArrayList.isEmpty()){
            cartEmpty.setVisibility(View.VISIBLE);
            proceedCheck.setVisibility(View.GONE);
        }

        findViewById(R.id.proceedCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Cart.this, Activity_ReviewOrder.class);
                intent.putExtra("valueForOrder", "fromCart");
                startActivity(intent);

            }
        });


    }


    private void addWishList(String dealId) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_Cart.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //.show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "add-to-wishlist",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString());

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                Toast.makeText(Activity_Cart.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();


                            } else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_Cart.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }


                        } catch (Exception e) {
                            //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Activity_Cart.this, "No Data", Toast.LENGTH_SHORT).show();

                    /*session.logout();
                    Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_Cart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("deal_id", dealId);

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
        Volley.newRequestQueue(Activity_Cart.this).add(volleyMultipartRequest);
    }



}