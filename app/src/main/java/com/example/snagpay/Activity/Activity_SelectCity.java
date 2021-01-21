package com.example.snagpay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.snagpay.Adapter.SelectCitySpinner;
import com.example.snagpay.Model.CityModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Activity_SelectCity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private Spinner mCity;
    private Spinner mState;
    private String mCityName;
    private Button btnUseMyLocation;
    private UserSession session;

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;
    private ArrayList<CityModel> mDataState = new ArrayList<>();
    private ArrayList<CityModel> mDataCity = new ArrayList<>();

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private boolean IsFirstTime = true;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        mCity = findViewById(R.id.spinnerSelectCity);
        session = new UserSession(Activity_SelectCity.this);
        btnUseMyLocation = findViewById(R.id.btnUseMyLocation);

        getCity();

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(Activity_SelectCity.this)
                .enableAutoManage(Activity_SelectCity.this,Activity_SelectCity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


       /* Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });*/

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        startLocationUpdates();

        btnUseMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Activity_SelectCity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_SelectCity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)){


                        // You can show your dialog message here but instead I am
                        // showing the grant permission dialog box
                        ActivityCompat.requestPermissions(Activity_SelectCity.this, new String[] {
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION },
                                2);

                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                currentLocation = locationResult.getLocations().get(0);
                                getAddress();
                            };
                        };

                    }
                    else{

                        //Requesting permission
                        ActivityCompat.requestPermissions(Activity_SelectCity.this, new String[] {
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION },
                                2);

                        Toast.makeText(Activity_SelectCity.this, "Please allow permission from Setting > App Manager > Snagpay > Permissions", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    if (isNetworkConnected()) {
                        Intent intent = new Intent(Activity_SelectCity.this, Activity_SignInSignUp.class);
                        intent.putExtra("city_id", " ");
                        startActivity(intent);
                        finish();
                    }else {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.layoutt), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                        ViewGroup group = (ViewGroup) snackbar.getView();
                        group.setBackgroundColor(ContextCompat.getColor(Activity_SelectCity.this, R.color.white));
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    }
                }

                Log.e("location", session.getLatitude() + " " + session.getLongitude() + " " + session.getAddress() + " " + session.getCity() + " " +
                        session.getState() + " " + session.getCountry() + " " + session.getPostCode());
            }
        });


        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i!=mDataCity.size()-1){
                    try {

                        if (isNetworkConnected()) {
                            Intent intent = new Intent(Activity_SelectCity.this, Activity_SignInSignUp.class);
                            intent.putExtra("city_id", mDataCity.get(i).getCityId());
                            startActivity(intent);
                            finish();
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.layoutt), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                            ViewGroup group = (ViewGroup) snackbar.getView();
                            group.setBackgroundColor(ContextCompat.getColor(Activity_SelectCity.this, R.color.white));
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                            textView.setTextColor(Color.RED);
                            snackbar.show();
                        }
                    }catch (Exception e){
                        //	GetStudnet("0","0");

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-cities?state_id=1",
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
                                    BatchModel.setCityname("Please select City");
                                    mDataCity.add(BatchModel);
                                    SelectCitySpinner adapter = new SelectCitySpinner(Activity_SelectCity.this,
                                            android.R.layout.simple_spinner_item,
                                            mDataCity);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    mCity.setAdapter(adapter);
                                    mCity.setSelection(adapter.getCount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_SelectCity.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
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
        volleyMultipartRequest.setShouldRetryServerErrors(true);

        Volley.newRequestQueue(Activity_SelectCity.this).add(volleyMultipartRequest);
    }

    private void getState() {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_SelectCity.this)
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
                                    BatchModel.setCityname("Please select State");
                                    mDataState.add(BatchModel);
                                    SelectCitySpinner adapter = new SelectCitySpinner(Activity_SelectCity.this,
                                            android.R.layout.simple_spinner_item,
                                            mDataState);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    mState.setAdapter(adapter);
                                    mState.setSelection(adapter.getCount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_SelectCity.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
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
        volleyMultipartRequest.setShouldRetryServerErrors(true);

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

        Location location = currentLocation;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size() == 0) {
        } else {
            Address address = addresses.get(0);

            session.setLatitude(String.valueOf(address.getLatitude()));
            session.setLongitude(String.valueOf(address.getLongitude()));
            try {

                if (address.getThoroughfare().equals("null")) {
                    session.setAddress(address.getFeatureName() + ", " + address.getLocality());
                } else if (!address.getThoroughfare().equals("null")) {
                    session.setAddress(address.getFeatureName() + ", " + address.getThoroughfare() + ", " + address.getLocality());
                }
            } catch (Exception e) {

            }

            session.setCity(address.getSubAdminArea());
            session.setState(address.getAdminArea());
            session.setCountry(address.getCountryName());
            session.setPostCode(address.getPostalCode());

            Log.e("LocationDetails", "---"+address.getLongitude()+"---"+
                    session.getLatitude()+"---"+
                    session.getAddress()+"---"+
                    session.getCity()+"---"+
                    session.getState()+"---"+
                    session.getCountry()+"---"+
                    session.getPostCode()+"---"
            );

        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Activity_SelectCity.this, "Sign out Falied", Toast.LENGTH_SHORT).show();

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

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    //
    // for check connection and also for snackbar
    //

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);

        }


        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if(!isConnected){
                                Log.v(LOG_TAG, "Now you are connected to Internet!");


                                if(IsFirstTime){
                                    IsFirstTime = false;
                                }else {
                                    //     Toast.makeText(context, "Good! Connected to Internet", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.layoutt), "Good! Connected to Internet", Snackbar.LENGTH_SHORT);

                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                    group.setBackgroundColor(ContextCompat.getColor(Activity_SelectCity.this, R.color.white));
                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                                    textView.setTextColor(Color.GREEN);
                                    snackbar.show();
                                }
                                //getCity();

                                isConnected = true;
                                //do your processing here ---
                                //if you need to post any data to the server or get status
                                //update from the server
                            }
                            return true;
                        }
                    }
                }
            }

            Log.v(LOG_TAG, "You are not connected to Internet!");
         //   Toast.makeText(context, "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.layoutt), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(Activity_SelectCity.this, R.color.white));
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();

            isConnected = false;
            return false;
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}