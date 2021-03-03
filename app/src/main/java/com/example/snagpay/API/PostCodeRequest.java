package com.example.snagpay.API;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostCodeRequest extends StringRequest {

private Map<String, String> parameters;

public PostCodeRequest(String postCode, Response.Listener<String> listener, Response.ErrorListener errorListener) {
    super(Method.GET, "https://api.zippopotam.us/us/" + postCode, listener, errorListener);
    parameters = new HashMap<>();
    // parameters.put("OldPassword ", OldPassword );

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

    return parameters;
    }

}