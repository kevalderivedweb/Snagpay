package com.example.snagpay.Fragments;

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
import com.example.snagpay.Activity_ForgotPassword;
import com.example.snagpay.MainActivity;
import com.example.snagpay.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_SignIn extends Fragment {

    private TextView txtPrivacyLogIn;
    private UserSession session;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText mEmail,mPassword;

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


        view.findViewById(R.id.txtForgotPasswordLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_ForgotPassword.class));
            }
        });

        view.findViewById(R.id.btnLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.createLoginSession();

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        view.findViewById(R.id.btnGoogleLogin).setOnClickListener(new View.OnClickListener() {
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

}