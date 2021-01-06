package com.example.snagpay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.R;
import com.example.snagpay.Utils.GetAddressIntentService;
import com.example.snagpay.Utils.UserSession;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_SelectCity extends AppCompatActivity {

    private Spinner mCity;
    private String mCityName;
    private Button btnUseMyLocation;
    private UserSession session;

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private LocationAddressResultReceiver addressResultReceiver;

    private TextView currentAddTv;

    private Location currentLocation;

    private LocationCallback locationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        mCity = findViewById(R.id.spinnerSelectCity);
        session = new UserSession(Activity_SelectCity.this);
        btnUseMyLocation = findViewById(R.id.btnUseMyLocation);


        btnUseMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_SignInSignUp.class));
            }
        });

        getCity();

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        currentAddTv = findViewById(R.id.txtSelectCity);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        startLocationUpdates();
    }

    private void getCity() {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_SelectCity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-cities",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");

                                    String[] City = new String[jsonObject1.length()];
                                    String[] cityId = new String[jsonObject1.length()];

                                    for (int i = 0; i < jsonObject1.length(); i++) {
                                        JSONObject object = jsonObject1.getJSONObject(i);
                                        City[i] = object.getString("city_name");
                                        cityId[i] = object.getString("city_id");
                                    }


                                    ArrayAdapter<String> adapter_age = new ArrayAdapter<String>(Activity_SelectCity.this,
                                            android.R.layout.simple_spinner_item, City);
                                    mCity.setAdapter(adapter_age);
                                    mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long id) {
                                            mCityName = (String) parent.getItemAtPosition(position);
                                            Log.v("item", (String) parent.getItemAtPosition(position));
                                            Log.e("mCityName", mCityName);
                                            Toast.makeText(Activity_SelectCity.this, cityId[position], Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            // TODO Auto-generated method stub
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            Toast.makeText(Activity_SelectCity.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_SelectCity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_SelectCity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_SelectCity.this).add(volleyMultipartRequest);
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {

            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {

                }
                return;
            }

        }
    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(Activity_SelectCity.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            Log.e("location", session.getLatitude() + " " + session.getLongitude() + " " + session.getAddress() + " " + session.getCity() + " " +
                    session.getState() + " " + session.getCountry() + " " + session.getPostCode());
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}