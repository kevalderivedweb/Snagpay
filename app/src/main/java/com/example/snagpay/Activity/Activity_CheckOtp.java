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
import android.text.Editable;
import android.text.TextWatcher;
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

public class Activity_CheckOtp extends AppCompatActivity {

    private EditText et1,et2,et3,et4;
    private String OTP ="";
    private UserSession session;
    private String mEmail;

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private boolean IsFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        session = new UserSession(Activity_CheckOtp.this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        et4 = findViewById(R.id.editText4);

        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));

        mEmail = getIntent().getStringExtra("emailForgott");
        Log.e("eMAill", mEmail + " 1");

        findViewById(R.id.btnOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et1.getText().toString().isEmpty()){
                    Toast.makeText(Activity_CheckOtp.this,"Please enter OTP",Toast.LENGTH_SHORT).show();

                }else if(et2.getText().toString().isEmpty()){
                    Toast.makeText(Activity_CheckOtp.this,"Please enter OTP",Toast.LENGTH_SHORT).show();

                }else if(et3.getText().toString().isEmpty()){
                    Toast.makeText(Activity_CheckOtp.this,"Please enter OTP",Toast.LENGTH_SHORT).show();

                }else if(et4.getText().toString().isEmpty()){
                    Toast.makeText(Activity_CheckOtp.this,"Please enter OTP",Toast.LENGTH_SHORT).show();

                }else {
                    OTP = et1.getText().toString()+
                            et2.getText().toString()+
                            et3.getText().toString()+
                            et4.getText().toString();

                    if (isNetworkConnected()) {
                        Check_Email(OTP, mEmail);
                    }else {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.q34), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                        ViewGroup group = (ViewGroup) snackbar.getView();
                        group.setBackgroundColor(ContextCompat.getColor(Activity_CheckOtp.this, R.color.white));
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    }
                }
               // startActivity(new Intent(getApplicationContext(), Activity_ResetPassword.class));
            }
        });

        findViewById(R.id.backCheckSentEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Check_Email(String otp, String mEmail) {
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_CheckOtp.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "check-otp",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                Intent intent = new Intent(Activity_CheckOtp.this, Activity_NewPassword.class);
                                intent.putExtra("emailPass", mEmail);
                                startActivity(intent);
                                finish();

                            }

                            Toast.makeText(Activity_CheckOtp.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Activity_CheckOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Activity_CheckOtp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("email", mEmail);
                params.put("otp", otp);
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
        Volley.newRequestQueue(Activity_CheckOtp.this).add(volleyMultipartRequest);
    }

    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.editText1:
                    if(text.length()==1)
                        et2.requestFocus();
                    break;
                case R.id.editText2:
                    if(text.length()==1)
                        et3.requestFocus();
                    else if(text.length()==0)
                        et1.requestFocus();
                    break;
                case R.id.editText3:
                    if(text.length()==1)
                        et4.requestFocus();
                    else if(text.length()==0)
                        et2.requestFocus();
                    break;
                case R.id.editText4:
                    if(text.length()==0)
                        et3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
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
                                            .make(findViewById(R.id.q34), "Good! Connected to Internet", Snackbar.LENGTH_SHORT);

                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                    group.setBackgroundColor(ContextCompat.getColor(Activity_CheckOtp.this, R.color.white));
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
                    .make(findViewById(R.id.q34), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(Activity_CheckOtp.this, R.color.white));
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