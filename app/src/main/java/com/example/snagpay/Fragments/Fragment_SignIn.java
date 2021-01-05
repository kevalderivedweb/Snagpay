package com.example.snagpay.Fragments;

import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snagpay.Utils.UserSession;
import com.example.snagpay.Activity_ForgotPassword;
import com.example.snagpay.MainActivity;
import com.example.snagpay.R;

public class Fragment_SignIn extends Fragment {

    private TextView txtPrivacyLogIn;
    private UserSession session;

    public Fragment_SignIn() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_in, container, false);

        session = new UserSession(getContext());

        txtPrivacyLogIn = view.findViewById(R.id.txtPrivacyLogIn);

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
}