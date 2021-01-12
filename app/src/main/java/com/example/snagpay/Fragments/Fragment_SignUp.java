package com.example.snagpay.Fragments;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_ThanksSeller;
import com.example.snagpay.Utils.UserSession;
import com.example.snagpay.Activity.MainActivity;
import com.example.snagpay.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.snagpay.Fragments.Fragment_SignIn.googleApiClient;

public class Fragment_SignUp extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private TextView txtPrivacySignUp, txtSellerPrivacySignUp;
    private UserSession session;
    private String mCityID;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText mEmail, mPassword, mName;
    private CheckBox checkboxEmailDealsSignUp;
    private LinearLayout linearSellerSignUp, linearSignUpUser;

    // for google login
    private static final int RC_SIGN_IN = 1;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userId;
    private KProgressHUD googleDialog;

    // for facebook sign in
    private static final String TAG = "MainActivity";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private LoginButton login_button2;
    private Button btnFacebookLoginSign;

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private Context context;
    private boolean IsFirstTime = true;

    public Fragment_SignUp(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_sign_up, container, false);

        session = new UserSession(getActivity());

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        context.registerReceiver(receiver, filter);

        FacebookSdk.sdkInitialize(getActivity());
        session = new UserSession(getContext());
        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();


        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mName = view.findViewById(R.id.first_name);
        checkboxEmailDealsSignUp = view.findViewById(R.id.checkboxEmailDealsSignUp);
        login_button2 = view.findViewById(R.id.login_button2);
        btnFacebookLoginSign = view.findViewById(R.id.btnFacebookLoginSign);
        linearSignUpUser = view.findViewById(R.id.linearSignUpUser);
        linearSellerSignUp = view.findViewById(R.id.linearSellerSignUp);

        login_button2.setReadPermissions("public_profile", "email" );

        btnFacebookLoginSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    login_button2.performClick();
                }else {
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(R.id.q12), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                    ViewGroup group = (ViewGroup) snackbar.getView();
                    group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        login_button2.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("fiele", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
                Log.e("fiele", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("fiele", "facebook:onError", error);
                // ...
            }
        });

        view.findViewById(R.id.btnGoogleLoginSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkConnected()) {
                    googleDialog = KProgressHUD.create(getActivity())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();

                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                }else {
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(R.id.q12), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                    ViewGroup group = (ViewGroup) snackbar.getView();
                    group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }

            }
        });

        Log.e("locate", session.getLatitude()+"---"+session.getLongitude()+"---"+session.getAddress()+"---"+session.getCity()+"---"+session.getState()+
                "---"+session.getCountry()+"---"+session.getPostCode());

        try {
            mCityID = getArguments().getString("city_id");
            Log.e("mCityID", mCityID + "--" + session.getPostCode());
        } catch (Exception e) {

        }


        txtPrivacySignUp = view.findViewById(R.id.txtPrivacySignUp);
        txtSellerPrivacySignUp = view.findViewById(R.id.txtSellerPrivacySignUp);

        customTextView(txtPrivacySignUp);
        customTextViewForSeller(txtSellerPrivacySignUp);

        view.findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //session.createLoginSession();

                 if (mName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (mEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (!mEmail.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                } else {
                     if (checkboxEmailDealsSignUp.isChecked()) {
                         if (isNetworkConnected()) {
                             SignUp(mName.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString());
                             linearSignUpUser.setVisibility(View.GONE);
                             linearSellerSignUp.setVisibility(View.VISIBLE);
                         }else {
                             Snackbar snackbar = Snackbar
                                     .make(getActivity().findViewById(R.id.q12), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

                             ViewGroup group = (ViewGroup) snackbar.getView();
                             group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                             View sbView = snackbar.getView();
                             TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                             textView.setTextColor(Color.RED);
                             snackbar.show();
                         }
                     }else {
                         Toast.makeText(getActivity(), "Tick the Box First", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

        view.findViewById(R.id.btnSignUpSeller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_ThanksSeller.class);
                getActivity().startActivity(intent);
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
                    Toast.makeText(getActivity(), "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, spanTxt.length() - " Privacy Statement.".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLUE), 64, 83, 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.NORMAL);
    }

    private void customTextViewForSeller(TextView view) {

        SpannableStringBuilder spanTxt = new SpannableStringBuilder("By clicking 'Sign up', I Confirm that i agree to the SNAGpay seller ");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, spanTxt.length(), 0);
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
                    Toast.makeText(getActivity(), "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, spanTxt.length() - "Terms of Use".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.parseColor("#049b12")), 68, 80, 0);
        spanTxt.append(", and have read the");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 80, spanTxt.length(), 0);
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

                    e.printStackTrace();
                }
            }
        }, spanTxt.length() - " Privacy Statement.".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.parseColor("#049b12")), 99, 117, 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.NORMAL);
    }

    private void SignUp(final String Name,final String Email, final String Password) {
        final KProgressHUD progressDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "register",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();


                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());


                            Log.e("Response",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){
                                JSONObject object = jsonObject.getJSONObject("data");

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
                                        object.getString("cost_of_goods"),
                                        object.getString("is_approved"),
                                        object.getString("api_token")
                                );

                               /* Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();*/
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
                params.put("full_name", Name);
                params.put("email", Email);
                params.put("password", Password);
                params.put("latitude", session.getLatitude());
                params.put("longitude", session.getLongitude());
                params.put("address", session.getAddress());
                params.put("city", session.getCity());
                params.put("state", session.getState());
                params.put("country", session.getCountry());
                params.put("postcode", session.getPostCode());
                params.put("city_id", mCityID);

                Log.e("inff", session.getLatitude()+"---"+session.getLongitude()+"---"+session.getAddress()+"---"+session.getCity()+"---"+session.getState()+
                        "---"+session.getCountry()+"---"+session.getPostCode());
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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, "onStart: Someone logged in <3");
        } else {
            Log.i(TAG, "onStart: No one logged in :/");
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



                                //     Toast.makeText(context, "Good! Connected to Internet", Toast.LENGTH_SHORT).show();
                                if (IsFirstTime){
                                    IsFirstTime = false;
                                }else {
                                    Snackbar snackbar = Snackbar
                                            .make(getActivity().findViewById(R.id.q11), "Good! Connected to Internet", Snackbar.LENGTH_SHORT);

                                    ViewGroup group = (ViewGroup) snackbar.getView();
                                    group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
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
                    .make(getActivity().findViewById(R.id.q11), "Sorry! Not connected to internet", Snackbar.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();

            isConnected = false;
            return false;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onPause() {
        super.onPause();
        //context.unregisterReceiver(receiver);
    }

}