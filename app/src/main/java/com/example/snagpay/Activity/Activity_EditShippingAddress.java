package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Adapter.SelectCitySpinner;
import com.example.snagpay.Model.CityModel;
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

public class Activity_EditShippingAddress extends AppCompatActivity {

    private UserSession session;

    private EditText nameInAddress, addressInAddress, streetInAddress, postCodeInAddress, phoneInAddress;
    private Spinner stateSpinner, citySpinner;

    private ArrayList<CityModel> mDataState = new ArrayList<>();
    private ArrayList<CityModel> mDataCity = new ArrayList<>();

    private int idShippingAddress;
    private String state_id;
    private String city_id;

    private String nameStateSpinnerFirst;
    private String nameCitySpinnerFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipping_address);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_EditShippingAddress.this);

        nameInAddress = findViewById(R.id.nameInAddress);
        addressInAddress = findViewById(R.id.addressInAddress);
        streetInAddress = findViewById(R.id.streetInAddress);
        postCodeInAddress = findViewById(R.id.postCodeInAddress);
        phoneInAddress = findViewById(R.id.phoneInAddress);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);

        idShippingAddress = getIntent().getIntExtra("idShippingAddress", 0);

        findViewById(R.id.backToPaymentInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnEditShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.e("EdiitAddress", nameInAddress.getText().toString() + "---" + addressInAddress.getText().toString() + "---" +
                        streetInAddress.getText().toString() + "---" +
                        postCodeInAddress.getText().toString() + "---" +
                        phoneInAddress.getText().toString() + "---" + state_id + "---" + city_id);*/

                if (nameInAddress.getText().toString().isEmpty()){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }else if (addressInAddress.getText().toString().isEmpty()) {
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }else if (streetInAddress.getText().toString().isEmpty()){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }else if (state_id == null){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter State", Toast.LENGTH_SHORT).show();
                }else if (city_id == null){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter City", Toast.LENGTH_SHORT).show();
                }else if (postCodeInAddress.getText().toString().isEmpty()){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter Postcode", Toast.LENGTH_SHORT).show();
                }else if (phoneInAddress.getText().toString().isEmpty()){
                    Toast.makeText(Activity_EditShippingAddress.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else {

                    editShippingAddress(nameInAddress.getText().toString(), addressInAddress.getText().toString(), streetInAddress.getText().toString(), state_id, city_id,
                            postCodeInAddress.getText().toString(), phoneInAddress.getText().toString());

                }
            }
        });

        SelectCitySpinner adapterState = new SelectCitySpinner(Activity_EditShippingAddress.this,
                android.R.layout.simple_spinner_item,
                mDataState);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_item);
        stateSpinner.setAdapter(adapterState);
         int abc  =  adapterState.getCount();
        stateSpinner.setSelection(adapterState.getCount());

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != mDataState.size()-1){
                    try {
                        state_id = mDataState.get(position).getCityId();
                        getCity();
                    }catch (Exception e){
                        //	GetStudnet("0","0");

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SelectCitySpinner adapterCity = new SelectCitySpinner(Activity_EditShippingAddress.this,
                android.R.layout.simple_spinner_item,
                mDataCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpinner.setAdapter(adapterCity);
        citySpinner.setSelection(adapterCity.getCount());

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != mDataCity.size()-1){
                    try {
                        city_id = mDataCity.get(position).getCityId();

                    }catch (Exception e){
                        //	GetStudnet("0","0");

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getState();
        getCity();

        getEditShippingAddress(idShippingAddress);
    }

    private void editShippingAddress(String name, String address1, String address2, String state_id, String city_id, String postCode, String phone) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_EditShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "edit-shipping-address",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    finish();
                                    Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_EditShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("name", name);
                params.put("address", address1);
                params.put("street", address2);
                params.put("country_id", "1");
                params.put("state_id", state_id);
                params.put("city_id", city_id);
                params.put("zip_code", postCode);
                params.put("phone", phone);
                params.put("shipping_address_id", String.valueOf(idShippingAddress));

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
        Volley.newRequestQueue(Activity_EditShippingAddress.this).add(volleyMultipartRequest);
    }

    private void getEditShippingAddress(int idShippingAddress) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_EditShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-shipping-address?shipping_address_id="
                + idShippingAddress,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                    nameInAddress.setText(jsonObject1.getString("name"));
                                    addressInAddress.setText(jsonObject1.getString("address"));
                                    streetInAddress.setText(jsonObject1.getString("street"));
                                    state_id = jsonObject1.getString("state_id");
                                    city_id = jsonObject1.getString("city_id");
                                    postCodeInAddress.setText(jsonObject1.getString("zip_code"));
                                    phoneInAddress.setText(jsonObject1.getString("phone"));
                                    nameCitySpinnerFirst = jsonObject1.getString("city_name");
                                    nameStateSpinnerFirst = jsonObject1.getString("state_name");

                                    Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_EditShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_EditShippingAddress.this).add(volleyMultipartRequest);
    }

    private void getState() {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_EditShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-states?country_id=1",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        CityModel BatchModel = new CityModel();
                                        BatchModel.setCityname(object.getString("state_name"));
                                        BatchModel.setCityId(object.getString("state_id"));
                                        mDataState.add(BatchModel);
                                    }

                                    CityModel BatchModel = new CityModel();
                                    BatchModel.setCityId("");
                                    BatchModel.setCityname("Select state");
                                    mDataState.add(BatchModel);
                                    SelectCitySpinner adapter = new SelectCitySpinner(Activity_EditShippingAddress.this,
                                            android.R.layout.simple_spinner_item,
                                            mDataState);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    stateSpinner.setAdapter(adapter);
                                    stateSpinner.setSelection(adapter.getCount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){


                            }

                            Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_EditShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                // params.put("Authorization", "Bearer " + session.getAPIToken());
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
        volleyMultipartRequest.setShouldRetryServerErrors(true);

        Volley.newRequestQueue(Activity_EditShippingAddress.this).add(volleyMultipartRequest);
    }

    private void getCity() {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_EditShippingAddress.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-cities?state_id=" + state_id,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        CityModel BatchModel = new CityModel();
                                        BatchModel.setCityname(object.getString("city_name"));
                                        BatchModel.setCityId(object.getString("city_id"));
                                        mDataCity.add(BatchModel);
                                    }

                                    CityModel BatchModel = new CityModel();
                                    BatchModel.setCityId("");
                                    BatchModel.setCityname("Select city");
                                    mDataCity.add(BatchModel);
                                    SelectCitySpinner adapter = new SelectCitySpinner(Activity_EditShippingAddress.this,
                                            android.R.layout.simple_spinner_item,
                                            mDataCity);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    citySpinner.setAdapter(adapter);
                                    citySpinner.setSelection(adapter.getCount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                               /* session.logout();
                                Intent intent = new Intent(Activity_SelectCity.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();*/
                            }

                            Toast.makeText(Activity_EditShippingAddress.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_EditShippingAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_EditShippingAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                // params.put("Authorization", "Bearer " + session.getAPIToken());
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
        volleyMultipartRequest.setShouldRetryServerErrors(true);

        Volley.newRequestQueue(Activity_EditShippingAddress.this).add(volleyMultipartRequest);
    }

}