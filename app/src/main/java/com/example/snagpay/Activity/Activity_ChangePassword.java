package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class Activity_ChangePassword extends AppCompatActivity {

    private EditText editOldPass, editNewPass, editConfirmPass;
    private Button btnChangePassword;
    private UserSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        session = new UserSession(Activity_ChangePassword.this);

        editOldPass = findViewById(R.id.editOldPass);
        editNewPass = findViewById(R.id.editNewPass);
        editConfirmPass = findViewById(R.id.editConfirmPass);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        findViewById(R.id.btnChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editOldPass.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ChangePassword.this, "Enter Old Password", Toast.LENGTH_SHORT).show();
                }
                else if (editNewPass.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                }
                else if (editConfirmPass.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else {
                        if (editNewPass.getText().toString().equals(editConfirmPass.getText().toString())) {
                            changePassword(editOldPass.getText().toString(), editNewPass.getText().toString(), editConfirmPass.getText().toString());
                        }else {
                            Toast.makeText(Activity_ChangePassword.this, "Please make sure your passwords match.", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        findViewById(R.id.backChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changePassword(String oldPass, String newPass, String confirmPass) {

            final KProgressHUD progressDialog = KProgressHUD.create(Activity_ChangePassword.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            //getting the tag from the edittext

            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, session.BASEURL + "change-password",
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            progressDialog.dismiss();

                            Log.e("Response",response.data.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                if (jsonObject.getString("ResponseCode").equals("200")){

                                    finish();

                                }else if(jsonObject.getString("ResponseCode").equals("401")){

                                    session.logout();
                                    Intent intent = new Intent(Activity_ChangePassword.this, Activity_SelectCity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                Toast.makeText(Activity_ChangePassword.this, jsonObject.getString("ResponseMsg"), Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(Activity_ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            Toast.makeText(Activity_ChangePassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("old_password", oldPass);
                    params.put("new_password", newPass);
                    params.put("confirm_password", confirmPass);
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
            Volley.newRequestQueue(Activity_ChangePassword.this).add(volleyMultipartRequest);

    }

    @Override
    public void onStop() {
        super.onStop();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!session.isCheckIn()){
            session.logout();
        }
    }

}