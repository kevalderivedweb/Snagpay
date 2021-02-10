package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.snagpay.API.VolleyMultipartRequest;
import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_NotificationSettings extends AppCompatActivity {

    private UserSession session;
    private SwitchCompat accountNotify, shipmentsNotify, recommendNotify, wishlistNotify;

    private String importat_message_alert, shipments_notifications, personalised_notifications, wishlist_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_NotificationSettings.this);

        accountNotify = findViewById(R.id.accountNotify);
        shipmentsNotify = findViewById(R.id.shipmentsNotify);
        recommendNotify = findViewById(R.id.recommendNotify);
        wishlistNotify = findViewById(R.id.wishlistNotify);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        accountNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (importat_message_alert.equals("1")) {
                    importat_message_alert = "0";

                } else {
                    importat_message_alert = "1";

                }
               /* Toast.makeText(Activity_NotificationSettings.this, importat_message_alert + "-" + shipments_notifications + "-" +
                        personalised_notifications + "-" + wishlist_notifications, Toast.LENGTH_SHORT).show();*/
            }
        });

        shipmentsNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shipments_notifications.equals("1")) {
                    shipments_notifications = "0";

                } else {
                    shipments_notifications = "1";

                }
               /* Toast.makeText(Activity_NotificationSettings.this, importat_message_alert + "-" + shipments_notifications + "-" +
                        personalised_notifications + "-" + wishlist_notifications, Toast.LENGTH_SHORT).show();*/
            }
        });

        recommendNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personalised_notifications.equals("1")) {
                    personalised_notifications = "0";

                } else {
                    personalised_notifications = "1";
                }
             /*   Toast.makeText(Activity_NotificationSettings.this, importat_message_alert + "-" + shipments_notifications + "-" +
                        personalised_notifications + "-" + wishlist_notifications, Toast.LENGTH_SHORT).show();*/
            }
        });

        wishlistNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wishlist_notifications.equals("1")) {
                    wishlist_notifications = "0";

                } else {
                    wishlist_notifications = "1";
                }
                /*Toast.makeText(Activity_NotificationSettings.this, importat_message_alert + "-" + shipments_notifications + "-" +
                        personalised_notifications + "-" + wishlist_notifications, Toast.LENGTH_SHORT).show();*/

            }
        });


        getNotificationStatus();

    }

    private void sendNotificationStatus(String imp, String ship, String per, String wish){
       /* final KProgressHUD progressDialog = KProgressHUD.create(Activity_NotificationSettings.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();*/
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "change-notification-settings",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                     //   progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("ResponseNotifSend",jsonObject.toString());

                            if (jsonObject.getString("ResponseCode").equals("200")){


                            } else if(jsonObject.getString("ResponseCode").equals("401")){
                                session.logout();
                                Intent intent = new Intent(Activity_NotificationSettings.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }

                        } catch (Exception e) {
                            Toast.makeText(Activity_NotificationSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                           /* session.logout();
                            Intent intent = new Intent(Activity_NotificationSettings.this, Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();*/

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                 //       progressDialog.dismiss();

                        Toast.makeText(Activity_NotificationSettings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("importat_message_alert", imp);
                params.put("shipments_notifications", ship);
                params.put("personalised_notifications", per);
                params.put("wishlist_notifications", wish);

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
        Volley.newRequestQueue(Activity_NotificationSettings.this).add(volleyMultipartRequest);
    }


    private void getNotificationStatus(){
       /* final KProgressHUD progressDialog = KProgressHUD.create(Activity_NotificationSettings.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();*/
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "get-notification-settings",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                       // progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("ResponseNotif",jsonObject.toString());

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                try {

                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                    importat_message_alert = jsonObject1.getString("importat_message_alert");
                                    shipments_notifications = jsonObject1.getString("shipments_notifications");
                                    personalised_notifications = jsonObject1.getString("personalised_notifications");
                                    wishlist_notifications = jsonObject1.getString("wishlist_notifications");

                                    if (importat_message_alert.equals("1")){
                                        accountNotify.setChecked(true);
                                    } else {
                                        accountNotify.setChecked(false);
                                    }

                                    if (shipments_notifications.equals("1")){
                                        shipmentsNotify.setChecked(true);
                                    } else {
                                        shipmentsNotify.setChecked(false);
                                    }

                                    if (personalised_notifications.equals("1")){
                                        recommendNotify.setChecked(true);
                                    } else {
                                        recommendNotify.setChecked(false);
                                    }

                                    if (wishlist_notifications.equals("1")){
                                        wishlistNotify.setChecked(true);
                                    } else {
                                        wishlistNotify.setChecked(false);
                                    }

                                    Log.e("checkNotify", session.getIMPORTANTNOTIFY() + "---" + session.getSHIPMENTSNOTIFY() + "---" +
                                            session.getPERSONALISEDNOTIFY() + "---" + session.getWISHLISTNOTIFY());


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Activity_NotificationSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_NotificationSettings.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }

                        } catch (Exception e) {
                            Toast.makeText(Activity_NotificationSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            /*session.logout();
                            Intent intent = new Intent(Activity_NotificationSettings.this, Activity_SelectCity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();*/

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   progressDialog.dismiss();

                        Toast.makeText(Activity_NotificationSettings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Activity_NotificationSettings.this).add(volleyMultipartRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        session.setIMPORTANTNOTIFY(importat_message_alert);
        session.setSHIPMENTSNOTIFY(shipments_notifications);
        session.setPERSONALISEDNOTIFY(personalised_notifications);
        session.setWISHLISTNOTIFY(wishlist_notifications);

       /* Toast.makeText(Activity_NotificationSettings.this, session.getIMPORTANTNOTIFY() + "-" + session.getSHIPMENTSNOTIFY() + "-" +
                session.getPERSONALISEDNOTIFY() + "-" + session.getWISHLISTNOTIFY(), Toast.LENGTH_SHORT).show();*/

        sendNotificationStatus(importat_message_alert, shipments_notifications, personalised_notifications, wishlist_notifications);
    }
}