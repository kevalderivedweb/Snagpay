package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.example.snagpay.Utils.UserSession;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_ForgotPassword extends AppCompatActivity {
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText mEmail;
    private UserSession session;

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private boolean IsFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue));


        session = new UserSession(Activity_ForgotPassword.this);
        mEmail = findViewById(R.id.email);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        findViewById(R.id.btnSentEmailForgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mEmail.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                }else if (!mEmail.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(Activity_ForgotPassword.this,"Invalid email address",Toast.LENGTH_SHORT).show();
                }else{
                    if (isNetworkConnected()){
                        ForgotPassword(mEmail.getText().toString());
                    }else {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.q23), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                        ViewGroup group = (ViewGroup) snackbar.getView();
                        group.setBackgroundColor(ContextCompat.getColor(Activity_ForgotPassword.this, R.color.white));
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    }

                }
            }
        });

        findViewById(R.id.backForgotPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ForgotPassword(final String Email) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_ForgotPassword.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "forgot-password",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));


                            if (jsonObject.getString("ResponseCode").equals("200")){

                                Intent intent = new Intent(Activity_ForgotPassword.this, Activity_CheckOtp.class);
                                intent.putExtra("emailForgott", mEmail.getText().toString());
                                startActivity(intent);
                                
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_ForgotPassword.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            Toast.makeText(Activity_ForgotPassword.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_ForgotPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("email", Email);
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
        Volley.newRequestQueue(Activity_ForgotPassword.this).add(volleyMultipartRequest);
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
                                            .make(findViewById(R.id.q23), "Good! Connected to Internet", Snackbar.LENGTH_SHORT);

                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                    group.setBackgroundColor(ContextCompat.getColor(Activity_ForgotPassword.this, R.color.white));
                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                                    textView.setTextColor(Color.GREEN);
                                    snackbar.show();
                                }

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
                    .make(findViewById(R.id.q23), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(Activity_ForgotPassword.this, R.color.white));
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