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
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.PostCodeRequest;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Activity.Activity_SelectCity;
import com.example.snagpay.Adapter.SelectCitySpinner;
import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.Model.CityModel;
import com.example.snagpay.Utils.NumberTextWatcher;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.snagpay.Fragments.Fragment_SignIn.googleApiClient;

public class Fragment_SignUp extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private TextView txtPrivacySignUp, txtSellerPrivacySignUp;
    private UserSession session;
    private String mCityID;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText mEmail, mPassword, mConfirmPassword, mName, nameSeller, emailSeller, phoneNoSeller, businessNameSeller, streetAddressSeller, zipCodeSeller,
            businessWebsite, avgSalesSeller, citySeller, stateSeller;

    private ImageView icon_password_visible_signup, icon_password_invisible_signup, icon_confi_password_visible_signup,
            icon_confi_password_invisible_signup;

    private Spinner stateSpinner, citySpinner, typeBusinessSpinner, physicalLocationSpinner, howLongSpinner, costOfGoodsSpinner;

    private ArrayList<CityModel> mDataState = new ArrayList<>();
    private ArrayList<CityModel> mDataCity = new ArrayList<>();

    private String state_id;
    private String city_id;
    private String type_of_business;
    private String costOfGoods;
    private String numLocations;
    private String numHowLongYrs;

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

    private String lastChar = " ";

    private String stateFromPost;
    private String cityFromPost;
    private RequestQueue requestQueue;
    private String avgSalesString;

    private ArrayList<CityModel> typeOfBusiness = new ArrayList<>();

    public Fragment_SignUp(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_sign_up, container, false);

        requestQueue = Volley.newRequestQueue(getContext());//Creating the RequestQueue

        session = new UserSession(getActivity());


        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        context.registerReceiver(receiver, filter);

        FacebookSdk.sdkInitialize(getActivity());
        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();


        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mConfirmPassword = view.findViewById(R.id.confirmPassword);
        mName = view.findViewById(R.id.first_name);
        checkboxEmailDealsSignUp = view.findViewById(R.id.checkboxEmailDealsSignUp);
        login_button2 = view.findViewById(R.id.login_button2);
        btnFacebookLoginSign = view.findViewById(R.id.btnFacebookLoginSign);
        linearSignUpUser = view.findViewById(R.id.linearSignUpUser);
        linearSellerSignUp = view.findViewById(R.id.linearSellerSignUp);
        icon_password_visible_signup = view.findViewById(R.id.icon_password_visible_signup);
        icon_password_invisible_signup = view.findViewById(R.id.icon_password_invisible_signup);
        icon_confi_password_visible_signup = view.findViewById(R.id.icon_confi_password_visible_signup);
        icon_confi_password_invisible_signup = view.findViewById(R.id.icon_confi_password_invisible_signup);

        nameSeller = view.findViewById(R.id.nameSeller);
        emailSeller = view.findViewById(R.id.emailSeller);
        phoneNoSeller = view.findViewById(R.id.phoneNoSeller);
        businessNameSeller = view.findViewById(R.id.businessNameSeller);
        streetAddressSeller = view.findViewById(R.id.streetAddressSeller);
        zipCodeSeller = view.findViewById(R.id.zipCodeSeller);
        businessWebsite = view.findViewById(R.id.businessWebsite);
        avgSalesSeller = view.findViewById(R.id.avgSalesSeller);

        stateSeller = view.findViewById(R.id.stateSeller);
        citySeller = view.findViewById(R.id.citySeller);

        typeBusinessSpinner = view.findViewById(R.id.typeBusinessSpinner);
        physicalLocationSpinner = view.findViewById(R.id.physicalLocationSpinner);
        howLongSpinner = view.findViewById(R.id.howLongSpinner);
        costOfGoodsSpinner = view.findViewById(R.id.costOfGoodsSpinner);

        icon_password_visible_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                icon_password_visible_signup.setVisibility(View.GONE);
                icon_password_invisible_signup.setVisibility(View.VISIBLE);


                mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mPassword.setSelection(mPassword.length());

                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        icon_password_invisible_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                mPassword.setSelection(mPassword.length());
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                icon_password_invisible_signup.setVisibility(View.GONE);
                icon_password_visible_signup.setVisibility(View.VISIBLE);
            }
        });


        icon_confi_password_visible_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                icon_confi_password_visible_signup.setVisibility(View.GONE);
                icon_confi_password_invisible_signup.setVisibility(View.VISIBLE);


                mConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mConfirmPassword.setSelection(mConfirmPassword.length());

                mConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        icon_confi_password_invisible_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                mConfirmPassword.setSelection(mConfirmPassword.length());
                mConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                icon_confi_password_invisible_signup.setVisibility(View.GONE);
                icon_confi_password_visible_signup.setVisibility(View.VISIBLE);
            }
        });



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



        phoneNoSeller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = phoneNoSeller.getText().toString().length();
                if (digits > 1)
                    lastChar = phoneNoSeller.getText().toString().substring(digits-1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = phoneNoSeller.getText().toString().length();
                Log.d("LENGTH",""+digits);
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 7) {
                        phoneNoSeller.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        avgSalesSeller.addTextChangedListener(new NumberTextWatcher(avgSalesSeller));




        login_button2.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("fiele", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());


                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
                Log.e("fiele", "facebook:onCancel");
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("fiele", "facebook:onError", error);
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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
                } else if (mConfirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                 } else if (!mConfirmPassword.getText().toString().equals(mPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "Password not Match", Toast.LENGTH_SHORT).show();
                } else {
                     if (checkboxEmailDealsSignUp.isChecked()) {
                         if (isNetworkConnected()) {
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

                         merchantSignUp(mName.getText().toString(), mEmail.getText().toString());
                     }else {
                         Toast.makeText(getActivity(), "Tick the Box First", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

        view.findViewById(R.id.btnSignUpSeller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                }else if (emailSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (!emailSeller.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (phoneNoSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                } else if (businessNameSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your Business name", Toast.LENGTH_SHORT).show();
                } else if (streetAddressSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your Address", Toast.LENGTH_SHORT).show();
                } else if (zipCodeSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter Zipcode", Toast.LENGTH_SHORT).show();
                } else if (stateSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter State", Toast.LENGTH_SHORT).show();
                } else if (citySeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter City", Toast.LENGTH_SHORT).show();
                } else if (type_of_business == null) {
                    Toast.makeText(getActivity(), "Please select Business type", Toast.LENGTH_SHORT).show();
                } else if (costOfGoods == null) {
                    Toast.makeText(getActivity(), "Please select Cost of Goods", Toast.LENGTH_SHORT).show();
                } else if (businessWebsite.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter Website", Toast.LENGTH_SHORT).show();
                } else if (numLocations == null) {
                    Toast.makeText(getActivity(), "Please select Physical locations", Toast.LENGTH_SHORT).show();
                } else if (numHowLongYrs == null) {
                    Toast.makeText(getActivity(), "Please select Business Experience", Toast.LENGTH_SHORT).show();
                } else if (avgSalesSeller.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter Average Sales", Toast.LENGTH_SHORT).show();
                } else {

                        if (isNetworkConnected()) {

                            SignUp();

                            avgSalesString = avgSalesSeller.getText().toString();

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

              //  Toast.makeText(context, avgSalesSeller.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

      //  getState();
/*
        SelectCitySpinner adapterState = new SelectCitySpinner(getContext(),
                android.R.layout.simple_spinner_item,
                mDataState);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_item);
        stateSpinner.setAdapter(adapterState);
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

        SelectCitySpinner adapterCity = new SelectCitySpinner(getContext(),
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
        });*/


        ArrayList<String> typeOfGoodsCostArray = new ArrayList<String>();

        for (int i = 1; i <= 100; i++){
            typeOfGoodsCostArray.add(i + "%");
        }
        typeOfGoodsCostArray.add("");

        final int listsizeCost = typeOfGoodsCostArray.size() - 1;
        ArrayAdapter<String> dataAdapterGoods = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeOfGoodsCostArray) {
            @Override
            public int getCount() {
                return(listsizeCost); // Truncate the list
            }
        };
        dataAdapterGoods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costOfGoodsSpinner.setAdapter(dataAdapterGoods);
        costOfGoodsSpinner.setSelection(listsizeCost); // Hidden item to appear in the spinner
        costOfGoodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 100) {
                    costOfGoods = String.valueOf(position + 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> phyLoc = new ArrayList<String>();
        phyLoc.add("One location");
        phyLoc.add("Two locations");
        phyLoc.add("Three locations");
        phyLoc.add("Five or more locations");
        phyLoc.add("Select one...");
        final int listsizeLoc = phyLoc.size() - 1;
        ArrayAdapter<String> locAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, phyLoc) {
            @Override
            public int getCount() {
                return(listsizeLoc); // Truncate the list
            }
        };
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        physicalLocationSpinner.setAdapter(locAdapter);
        physicalLocationSpinner.setSelection(listsizeLoc); // Hidden item to appear in the spinner
        physicalLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 4) {
                    if (position == 3){
                        numLocations = "5";
                    }else {
                        numLocations = String.valueOf(position + 1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> howLongYrs = new ArrayList<String>();
        howLongYrs.add("One year");
        howLongYrs.add("Two years");
        howLongYrs.add("Three years");
        howLongYrs.add("Five or more years");
        howLongYrs.add("Select one...");
        final int listsizeYrs = howLongYrs.size() - 1;
        ArrayAdapter<String> yrsLongAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, howLongYrs) {
            @Override
            public int getCount() {
                return(listsizeYrs); // Truncate the list
            }
        };
        yrsLongAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howLongSpinner.setAdapter(yrsLongAdapter);
        howLongSpinner.setSelection(listsizeYrs); // Hidden item to appear in the spinner
        howLongSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 4) {
                    if (position == 3){
                        numHowLongYrs = "+5";
                    } else {
                        numHowLongYrs = String.valueOf("+" + (position + 1));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        zipCodeSeller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                int digits = zipCodeSeller.getText().toString().length();
                Log.d("LENGTH",""+digits);
                if (!lastChar.equals("-")) {
                        postCodeCityState(zipCodeSeller.getText().toString());

                }

            }
        });

        getCategories();


        return view;
    }


    private void getState() {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
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
                                    SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
                                            android.R.layout.simple_spinner_item,
                                            mDataState);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    stateSpinner.setAdapter(adapter);
                                    stateSpinner.setSelection(adapter.getCount());

                                    getCityAnother();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){


                            }

                            Toast.makeText(getContext(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    private void getCity() {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
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

                        mDataCity.clear();

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
                                    SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
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

                            Toast.makeText(getContext(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    private void getCityAnother() {
        mDataCity.clear();
        CityModel BatchModel = new CityModel();
        BatchModel.setCityId("");
        BatchModel.setCityname("Please select City");
        mDataCity.add(BatchModel);
        mDataCity.add(BatchModel);
        SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
                android.R.layout.simple_spinner_item,
                mDataCity);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        citySpinner.setAdapter(adapter);
        citySpinner.setSelection(adapter.getCount());
    }


    private void merchantSignUp(String name, String email) {

        nameSeller.setText(name);
        emailSeller.setText(email);
        streetAddressSeller.setText(session.getADDRESS());
    }


    private void getCategories(){
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        //   .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-categories",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();
                        typeOfBusiness.clear();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString() + " dd");
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                    JSONArray jsonArray = jsonObject1.getJSONArray("categories");

                                    for (int i = 0 ; i<jsonArray.length() ; i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        CityModel cityModel = new CityModel();
                                        cityModel.setCityId(object.getString("category_id"));
                                        cityModel.setCityname(object.getString("category_name"));

                                        typeOfBusiness.add(cityModel);
                                    }

                                    CityModel cityModel = new CityModel();
                                    cityModel.setCityId("");
                                    cityModel.setCityname("Select one...");
                                    typeOfBusiness.add(cityModel);

                                    SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
                                            android.R.layout.simple_spinner_item,
                                            typeOfBusiness);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    typeBusinessSpinner.setAdapter(adapter);
                                    typeBusinessSpinner.setSelection(adapter.getCount());


                                    typeBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(position != typeOfBusiness.size()-1){
                                                try {

                                                    type_of_business = typeOfBusiness.get(position).getCityId();

                                                }catch (Exception e){
                                                    //	GetStudnet("0","0");

                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(getActivity(), Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }



    private void postCodeCityState(String postCode){
        final KProgressHUD progressDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
               // .show();
        //getting the tag from the edittext

        PostCodeRequest postCodeRequest = new PostCodeRequest(postCode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("Response",jsonObject.toString());



                    JSONArray jsonArray = jsonObject.getJSONArray("places");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        stateSeller.setText(object.getString("state"));
                        citySeller.setText(object.getString("place name"));

                    }


                   /* for (int i = 0; i < mDataState.size(); i++){

                        if (mDataState.get(i).getCityname().equals(stateFromPost)){

                            state_id = mDataState.get(i).getCityId();
                            String nameState = mDataState.get(i).getCityname();

                            getStateFromCode(state_id, nameState, cityFromPost);
                        }
                    }*/




                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  progressDialog.dismiss();

             //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(postCodeRequest);

       /* //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, "https://api.zippopotam.us/us/" + postCode,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            *//*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * *//*
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("Accept", "application/json");
                // params.put("Authorization", "Bearer " + session.getAPIToken());
                return params;
            }

            *//*
             * Here we are passing image by renaming it with a unique name
             * *//*
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);*/
    }

    private void getStateFromCode(String stateId, String nameState, String cityFromPost) {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
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

                        mDataState.clear();

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
                                    BatchModel.setCityId(stateId);
                                    BatchModel.setCityname(nameState);
                                    mDataState.add(BatchModel);
                                    SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
                                            android.R.layout.simple_spinner_item,
                                            mDataState);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    stateSpinner.setAdapter(adapter);
                                    stateSpinner.setSelection(adapter.getCount());

                                    getCityFromCode(stateId, nameState, cityFromPost);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){


                            }

                            Toast.makeText(getContext(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    private void getCityFromCode(String stateId, String nameState, String cityFromPost) {
        final KProgressHUD progressDialog = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, session.BASEURL + "get-cities?state_id=" + stateId,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();

                        mDataCity.clear();

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

                                    city_id = "";
                                    String nameCityPost = "";

                                    for (int i = 0; i < mDataCity.size(); i++){

                                        if (mDataCity.get(i).getCityname().equals(cityFromPost)){

                                            city_id = mDataCity.get(i).getCityId();
                                            nameCityPost = mDataCity.get(i).getCityname();
                                        }
                                    }


                                    CityModel BatchModel = new CityModel();
                                    BatchModel.setCityId(city_id);
                                    BatchModel.setCityname(nameCityPost);
                                    mDataCity.add(BatchModel);

                                    SelectCitySpinner adapter = new SelectCitySpinner(getContext(),
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

                            Toast.makeText(getContext(), jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
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

    private void SignUp() {
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


                            Log.e("ResponseSignUp",jsonObject.toString());
                            if (jsonObject.getString("ResponseCode").equals("200")){

                                session.stayLoggedIn(true);

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
                                        object.getString("avg_sales_per_month"),
                                        object.getString("how_long_have_you"),
                                        object.getString("no_of_physical_locations"),
                                        object.getString("website_or_page"),
                                        object.getString("cost_of_goods"),
                                        object.getString("is_approved"),
                                        object.getString("api_token")
                                );

                                Intent intent = new Intent(getContext(), MainActivity.class);
                            //    Intent intent = new Intent(getContext(), Activity_ThanksSeller.class);


                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();

                            }

                            else if (jsonObject.getString("ResponseCode").equals("422")){
                                Toast.makeText(context, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
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
                params.put("full_name", nameSeller.getText().toString());
                params.put("email", emailSeller.getText().toString());
                params.put("password", mPassword.getText().toString());
                params.put("phone_no", phoneNoSeller.getText().toString());
                params.put("business_name", businessNameSeller.getText().toString());
                params.put("address", streetAddressSeller.getText().toString());
                params.put("postcode", zipCodeSeller.getText().toString());
                params.put("longitude", session.getLongitude());
                params.put("latitude", session.getLatitude());
                params.put("city", stateSeller.getText().toString());
                params.put("state", citySeller.getText().toString());
                params.put("country_id", "1");
                params.put("type_of_business", type_of_business);
                params.put("website_or_page", businessWebsite.getText().toString());
                params.put("no_of_physical_locations", numLocations);
                params.put("how_long_have_you", numHowLongYrs);
                params.put("avg_sales_per_month", avgSalesString);
                params.put("cost_of_goods", costOfGoods);

                Log.e("inff", nameSeller.getText().toString() + "---" + emailSeller.getText().toString() + "---" +
                        phoneNoSeller.getText().toString() + "---" + businessNameSeller.getText().toString() + "---" + streetAddressSeller.getText().toString() + "---" +
                        session.getCityId() + "---" + session.getSTATEID() + "---" + zipCodeSeller.getText().toString() + "---" +
                        session.getBUSS_TYPE() + "---" + businessWebsite.getText().toString() + "---" + session.getPHYSICAL_LOCATION());

                Log.e("avgSales", avgSalesString + "---");
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

                                session.stayLoggedIn(true);

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
                                        object.getString("avg_sales_per_month"),
                                        object.getString("how_long_have_you"),
                                        object.getString("no_of_physical_locations"),
                                        object.getString("website_or_page"),
                                        object.getString("cost_of_goods"),
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
                            Toast.makeText(context, "Facebook Sign Successfull", Toast.LENGTH_SHORT).show();
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