package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snagpay.R;

public class Activity_ThanksSeller extends AppCompatActivity {

    private TextView txtSellerThanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_seller);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        txtSellerThanks = findViewById(R.id.txtSellerThanks);

        customTextView(txtSellerThanks);
    }

    private void customTextView(TextView view) {

        SpannableStringBuilder spanTxt = new SpannableStringBuilder("");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, spanTxt.length(), 0);
        spanTxt.append("The SNAGpay Blog ");
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
                    Toast.makeText(Activity_ThanksSeller.this, "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, spanTxt.length() - "The SNAGpay Blog ".length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 17, 0);
        spanTxt.append("Offers News, tips and resources to help you grow and manage your business. It's must read!");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 17, spanTxt.length(), 0);


        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.NORMAL);
    }
}