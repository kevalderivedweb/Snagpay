package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Activity_GiveAsGift extends AppCompatActivity {

    private RadioGroup radioGroupGift;
    private LinearLayout lnrGiftEmail, lnrGiftText;
    private UserSession session;

    private static TextView dateEmail, dateText;
    private EditText emailRecipient, nameRecipient, fromEmailGift, messageEmail;
    private EditText phoneGift, messageText;

    private String dealOptionIdCart, shipping_address_id, priceCart, bucksInWallet;

    private String giftType = "email";

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String lastChar = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_as_gift);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_GiveAsGift.this);

        radioGroupGift = findViewById(R.id.radioGroupGift);
        lnrGiftEmail = findViewById(R.id.lnrGiftEmail);
        lnrGiftText = findViewById(R.id.lnrGiftText);
        dateEmail = findViewById(R.id.dateEmail);
        dateText = findViewById(R.id.dateText);
        emailRecipient = findViewById(R.id.emailRecipient);
        nameRecipient = findViewById(R.id.nameRecipient);
        fromEmailGift = findViewById(R.id.fromEmailGift);
        messageEmail = findViewById(R.id.messageEmail);
        phoneGift = findViewById(R.id.phoneGift);
        messageText = findViewById(R.id.messageText);

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnGiveGift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (giftType.equals("email")){
                    if (emailRecipient.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter email", Toast.LENGTH_SHORT).show();
                    } else if (!emailRecipient.getText().toString().trim().matches(emailPattern)){
                        Toast.makeText(Activity_GiveAsGift.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    } else if (nameRecipient.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter name", Toast.LENGTH_SHORT).show();
                    } else if (fromEmailGift.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter From field", Toast.LENGTH_SHORT).show();
                    } else if (messageEmail.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter message", Toast.LENGTH_SHORT).show();
                    } else if (dateEmail.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Select Date", Toast.LENGTH_SHORT).show();
                    } else {
                        CreateOrder();
                    }

                } else if (giftType.equals("text")) {
                    if (phoneGift.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                    } else if (messageText.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Enter message", Toast.LENGTH_SHORT).show();
                    } else if (dateText.getText().toString().isEmpty()){
                        Toast.makeText(Activity_GiveAsGift.this, "Select Date", Toast.LENGTH_SHORT).show();}
                     else {
                        CreateOrder();
                    }

                }
            }
        });

        phoneGift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = phoneGift.getText().toString().length();
                if (digits > 1)
                    lastChar = phoneGift.getText().toString().substring(digits-1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = phoneGift.getText().toString().length();
                Log.d("LENGTH",""+digits);
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 7) {
                        phoneGift.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroupGift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioEmail:

                        lnrGiftEmail.setVisibility(View.VISIBLE);
                        lnrGiftText.setVisibility(View.GONE);

                        giftType = "email";


                        /*phoneGift.setText("");
                        messageText.setText("");
                        dateText.setText("");*/
                        break;

                    case R.id.radioText:

                        lnrGiftEmail.setVisibility(View.GONE);
                        lnrGiftText.setVisibility(View.VISIBLE);

                        giftType = "text";

                       /* emailRecipient.setText("");
                        nameRecipient.setText("");
                        fromEmailGift.setText("");
                        messageGift.setText("");
                        dateEmail.setText("");*/
                        break;

                }
            }
        });

        dateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DateEmailPickerFragment();
                newFragment.show(getSupportFragmentManager(), "dateEmailPicker");

            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DateTextPickerFragment();
                newFragment.show(getSupportFragmentManager(), "dateTextPicker");

            }
        });

        dealOptionIdCart = getIntent().getStringExtra("dealOptionIdCart");
        shipping_address_id = getIntent().getStringExtra("shipping_address_id");
        priceCart = getIntent().getStringExtra("priceCart");
        bucksInWallet = getIntent().getStringExtra("bucksInWallet");



    }


    public void CreateOrder(){
        final KProgressHUD progressDialog = KProgressHUD.create(Activity_GiveAsGift.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //getting the tag from the edittext

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "create-order",
                new Response.Listener<NetworkResponse>() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onResponse(NetworkResponse response) {


                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Log.e("Response",jsonObject.toString());

                            if (jsonObject.getString("ResponseCode").equals("200")){

                                Intent intent = new Intent(Activity_GiveAsGift.this, Activity_ThankYou.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            else if(jsonObject.getString("ResponseCode").equals("422")){

                                Toast.makeText(Activity_GiveAsGift.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();
                            }

                            else if(jsonObject.getString("ResponseCode").equals("401")){

                                session.logout();
                                Intent intent = new Intent(Activity_GiveAsGift.this, Activity_SelectCity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e) {
                            //  Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            Toast.makeText(Activity_GiveAsGift.this, "No Data", Toast.LENGTH_SHORT).show();

                   /* session.logout();
                    Intent intent = new Intent(Activity_ProductDetails.this, Activity_SelectCity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("dsdsdsd", error.getMessage());
                        progressDialog.dismiss();
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


                params.put("deal_option_ids", dealOptionIdCart);
                params.put("shipping_address_id", shipping_address_id);
                params.put("total_price", priceCart);
                params.put("discount", "0");
                params.put("snagpay_bucks", "0");
                params.put("give_as_a_gift", "1");
                params.put("gift_type", giftType);

                if (giftType.equals("email")) {
                    params.put("recipient_email", emailRecipient.getText().toString());
                    params.put("recipient_name", nameRecipient.getText().toString());
                    params.put("from_name", fromEmailGift.getText().toString());
                    params.put("message", messageEmail.getText().toString());
                    params.put("send_gift_date", dateEmail.getText().toString());
                }

                else if (giftType.equals("text")) {
                    params.put("phone_number", phoneGift.getText().toString());
                    params.put("message", messageText.getText().toString());
                    params.put("send_gift_date", dateText.getText().toString());
                }

                Log.e("allDataCreate", dealOptionIdCart + "---" + shipping_address_id + "---" + priceCart + bucksInWallet + "---" + giftType + "---" +
                        emailRecipient.getText().toString() + "---" + nameRecipient.getText().toString() + "---" + fromEmailGift.getText().toString() + "---" +
                        messageEmail.getText().toString() + "---" + dateEmail.getText().toString() +"---" +
                        phoneGift.getText().toString() + "---" + messageText.getText().toString() + "---" + dateText.getText().toString());
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
        Volley.newRequestQueue(Activity_GiveAsGift.this).add(volleyMultipartRequest);
    }


    public static class DateEmailPickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            /*dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.DAY_OF_MONTH,2);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());*/
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = dateFormat.format(calendar.getTime());
            /*	try {
             *//*if (dateFormat.parse(formattedDate_abc).after(dateFormat.parse(dateString))) {
						Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
						return;
					}*//*

				    Date oldDate = null;
					oldDate = dateFormat.parse(dateString);
					Date currentDate = new Date();
					String newDate1 = dateFormat.format(currentDate);
					Date newDate = dateFormat.parse(newDate1);

					long diff =  oldDate.getTime() - newDate.getTime();
					long diffInHours = TimeUnit.MILLISECONDS.toDays(diff);

					if(diffInHours>2){
						Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
						return;
					}


			} catch (ParseException e) {
				e.printStackTrace();
			}*/
            dateEmail.setText(dateString);


        }
    }

    public static class DateTextPickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            /*dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.DAY_OF_MONTH,2);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());*/
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = dateFormat.format(calendar.getTime());
            /*	try {
             *//*if (dateFormat.parse(formattedDate_abc).after(dateFormat.parse(dateString))) {
						Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
						return;
					}*//*

				    Date oldDate = null;
					oldDate = dateFormat.parse(dateString);
					Date currentDate = new Date();
					String newDate1 = dateFormat.format(currentDate);
					Date newDate = dateFormat.parse(newDate1);

					long diff =  oldDate.getTime() - newDate.getTime();
					long diffInHours = TimeUnit.MILLISECONDS.toDays(diff);

					if(diffInHours>2){
						Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
						return;
					}


			} catch (ParseException e) {
				e.printStackTrace();
			}*/
            dateText.setText(dateString);


        }
    }



}