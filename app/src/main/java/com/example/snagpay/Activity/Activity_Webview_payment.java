package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.snagpay.R;

public class Activity_Webview_payment extends AppCompatActivity {

    private WebView webview;
    private TextView yearMonth;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_payment);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark


        findViewById(R.id.backToProductDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String urlPdf = getIntent().getStringExtra("urlPdf");
        String month = getIntent().getStringExtra("monthWord");
        String year = getIntent().getStringExtra("yearTrans");

        webview = findViewById(R.id.webview_payment);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
   //     webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setWebViewClient(new HelloWebViewClient());
        webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+ "http://chessmafia.com/php/snagpay/web/public/statement/11-order.pdf");

        yearMonth = findViewById(R.id.yearMonth);

        yearMonth.setText(month + " " + year);

        Log.e("textxttx", urlPdf + "--");

      /*  webview_payment.getSettings().setJavaScriptEnabled(true);

        webview_payment.loadUrl(urlPdf);*/

    }





}