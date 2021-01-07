package com.example.snagpay.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.snagpay.Utils.UserSession;
import com.example.snagpay.Activity.Activity_ForgotPassword;
import com.example.snagpay.Activity.MainActivity;
import com.example.snagpay.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_SignIn extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private TextView txtPrivacyLogIn;
    private UserSession session;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText mEmail,mPassword;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userId;
    private KProgressHUD googleDialog;

    public Fragment_SignIn() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_in, container, false);

        session = new UserSession(getContext());

        txtPrivacyLogIn = view.findViewById(R.id.txtPrivacyLogIn);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);

        customTextView(txtPrivacyLogIn);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        Log.e("Data",session.getLatitude());

        view.findViewById(R.id.txtForgotPasswordLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_ForgotPassword.class));
            }
        });

        view.findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mEmail.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                }else if (!mEmail.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getActivity(),"Invalid email address",Toast.LENGTH_SHORT).show();
                }else if(mPassword.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }else {
                    SignIn(mEmail.getText().toString(),mPassword.getText().toString());
                }
            }
        });

        view.findViewById(R.id.btnGoogleLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 googleDialog = KProgressHUD.create(getActivity())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);

            }
        });

        return view;
    }

    private void customTextView(TextView view) {

        SpannableStringBuilder spanTxt = new SpannableStringBuilder("By clicking below, I agree to the ");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_gray)), 0, spanTxt.length(), 0);
        spanTxt.append("Terms of Use");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);    // this remove the underline
            }
            @Override
            public void onClick(View widget) {

                try {
                    Uri webpage = Uri.parse("https://www.google.com");
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, spanTxt.length() - "Terms of Use".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLUE), 34, 46, 0);
        spanTxt.append(" and have read the");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_gray)), 46, spanTxt.length(), 0);
        spanTxt.append(" Privacy Statement.");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);    // this remove the underline
            }
            @Override
            public void onClick(View widget) {

                try {
                    Uri webpage = Uri.parse("https://www.facebook.com");
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, spanTxt.length() - " Privacy Statement.".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLUE), 64, 83, 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.NORMAL);
    }


    private void googleLogIn(String firstName, String lastName, String email, String userId) {
        final KProgressHUD progressDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "login-with-google",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            JSONObject object = jsonObject.getJSONObject("data");

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                session.createLoginSession(object.getString("user_id"),
                                        object.getString("first_name"),
                                        object.getString("last_name"),
                                        object.getString("email"),
                                        object.getString("phone_no"),
                                        object.getString("facebook_id"),
                                        object.getString("google_id"),
                                        object.getString("type"),
                                        object.getString("address"),
                                        object.getString("city_id"),
                                        object.getString("state_id"),
                                        object.getString("country_id"),
                                        object.getString("postcode"),
                                        object.getString("is_email_verified"),
                                        object.getString("is_email_verified"),
                                        object.getString("otp"),
                                        object.getString("latitude"),
                                        object.getString("longitude"),
                                        object.getString("business_name"),
                                        object.getString("type_of_business"),
                                        object.getString("can_we_run_credit_report"),
                                        object.getString("avg_sales_per_month"),
                                        object.getString("how_long_have_you"),
                                        object.getString("no_of_physical_locations"),
                                        object.getString("website_or_page"),
                                        object.getString("is_approved"),
                                        object.getString("api_token")
                                );

                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                            Toast.makeText(getActivity(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email", email);
                params.put("google_id", userId);
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
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    private void SignIn(final String Email, final String Password) {
        final KProgressHUD progressDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "login",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        Log.e("Response",response.data.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            JSONObject object = jsonObject.getJSONObject("data");

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                session.createLoginSession(object.getString("user_id"),
                                        object.getString("first_name"),
                                        object.getString("last_name"),
                                        object.getString("email"),
                                        object.getString("phone_no"),
                                        object.getString("facebook_id"),
                                        object.getString("google_id"),
                                        object.getString("type"),
                                        object.getString("address"),
                                        object.getString("city_id"),
                                        object.getString("state_id"),
                                        object.getString("country_id"),
                                        object.getString("postcode"),
                                        object.getString("is_email_verified"),
                                        object.getString("is_email_verified"),
                                        object.getString("otp"),
                                        object.getString("latitude"),
                                        object.getString("longitude"),
                                        object.getString("business_name"),
                                        object.getString("type_of_business"),
                                        object.getString("can_we_run_credit_report"),
                                        object.getString("avg_sales_per_month"),
                                        object.getString("how_long_have_you"),
                                        object.getString("no_of_physical_locations"),
                                        object.getString("website_or_page"),
                                        object.getString("is_approved"),
                                        object.getString("api_token")
                                        );

                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            
                            Toast.makeText(getActivity(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("password", Password);
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
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){

            googleDialog.dismiss();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            String name = account.getDisplayName();

            int firstSpace = name.indexOf(" ");
            firstName = name.substring(0, firstSpace);
            lastName = name.substring(firstSpace).trim();

            userEmail = account.getEmail();
            userId = account.getId();

            Log.e("googleAccInfo", firstName + "--"+ lastName+ "--" + userEmail + "--" + userId);
            googleLogIn(firstName, lastName, userEmail, userId);


        }else{
            Toast.makeText(getContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

}