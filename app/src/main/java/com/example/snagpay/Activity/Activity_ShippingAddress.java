package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.AdapterShippingAddress;
import com.example.snagpay.Model.ShippingAddressModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_ShippingAddress extends AppCompatActivity {

    private RecyclerView resShippingAddress;
    private AdapterShippingAddress adapterShippingAddress;
    private ArrayList<ShippingAddressModel> shippingAddressModelArrayList;

    private LinearLayout btnAddNewAddress;
    private RelativeLayout relativeShipping;
    private Button btnShippingContinue;
    private UserSession session;

    private int newString = 0;

    private int a = 0;
    private KProgressHUD progressDialogForSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_ShippingAddress.this);
        shippingAddressModelArrayList = new ArrayList<>();

        progressDialogForSelect = KProgressHUD.create(Activity_ShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        resShippingAddress = findViewById(R.id.resShippingAddress);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        btnShippingContinue = findViewById(R.id.btnShippingContinue);
        relativeShipping = findViewById(R.id.relativeShipping);

        getShippingAddressList();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString = 0;
            } else {
                newString = extras.getInt("value");
            }
        }

        if (newString != 2){
            relativeShipping.setVisibility(View.GONE);
        }

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resShippingAddress.setLayoutManager(new LinearLayoutManager(this));
        adapterShippingAddress = new AdapterShippingAddress(Activity_ShippingAddress.this, shippingAddressModelArrayList, new AdapterShippingAddress.OnItemClickListener() {
            @Override
            public void onItemClickRemove(int item) {

                removeShippingAddress(item);

            }

            @Override
            public void onItemClickEdit(int item) {
                Intent intent = new Intent(Activity_ShippingAddress.this, Activity_EditShippingAddress.class);
                intent.putExtra("idShippingAddress", item);
                startActivity(intent);
            }

            @Override
            public void onItemClickRadio(int item) {

                selectShippingAdress(item);
            }
        });
        adapterShippingAddress.notifyDataSetChanged();
        resShippingAddress.setAdapter(adapterShippingAddress);

        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ShippingAddress.this, Activity_AddShippingAddress.class);
                startActivity(intent);
            }
        });

        btnShippingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newString == 1) {
                    finish();
                }
                if (newString == 2) {
                    Intent intent = new Intent(Activity_ShippingAddress.this, Activity_ThankYou.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void selectShippingAdress(int id) {

        progressDialogForSelect.show();

        a = 1;
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "set-default-shipping-address",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {
                                    getShippingAddressList();
                                    Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialogForSelect.dismiss();

                        Toast.makeText(Activity_ShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("shipping_address_id", String.valueOf(id));

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
        Volley.newRequestQueue(Activity_ShippingAddress.this).add(volleyMultipartRequest);
    }

    private void removeShippingAddress(int item) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "delete-shipping-address",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {
                                    getShippingAddressList();
                                    Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("shipping_address_id", String.valueOf(item));

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
        Volley.newRequestQueue(Activity_ShippingAddress.this).add(volleyMultipartRequest);
    }

    private void getShippingAddressList() {

        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        if (a == 0) {
            progressDialog.show();
        }
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-shipping-addresses",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        if (a == 1){
                            progressDialogForSelect.dismiss();
                        } else {
                            progressDialog.dismiss();
                        }

                        shippingAddressModelArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        ShippingAddressModel shippingAddressModel = new ShippingAddressModel();

                                        shippingAddressModel.setShipping_address_id(jsonObject1.getString("shipping_address_id"));
                                        shippingAddressModel.setAddress(jsonObject1.getString("address"));
                                        shippingAddressModel.setStreet(jsonObject1.getString("street"));
                                        shippingAddressModel.setCountry_id(jsonObject1.getString("country_id"));
                                        shippingAddressModel.setState_id(jsonObject1.getString("state_id"));
                                        shippingAddressModel.setCity_id(jsonObject1.getString("city_id"));
                                        shippingAddressModel.setZip_code(jsonObject1.getString("zip_code"));
                                        shippingAddressModel.setPhone(jsonObject1.getString("phone"));
                                        shippingAddressModel.setCountry_name(jsonObject1.getString("country_name"));
                                        shippingAddressModel.setState_name(jsonObject1.getString("state_name"));
                                        shippingAddressModel.setCity_name(jsonObject1.getString("city_name"));
                                        shippingAddressModel.setIs_default(jsonObject1.getString("is_default"));

                                        shippingAddressModelArrayList.add(shippingAddressModel);
                                    }

                                    adapterShippingAddress.notifyDataSetChanged();

                                    Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Activity_ShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_ShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_ShippingAddress.this).add(volleyMultipartRequest);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getShippingAddressList();
    }
}