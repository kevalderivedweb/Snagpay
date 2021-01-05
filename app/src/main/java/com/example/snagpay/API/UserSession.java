package com.example.snagpay.API;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UserSessionPref";


    private static final String IS_LOGIN = "IsLoggedIn";

    public UserSession (Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession (){
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public boolean logout() {
        return sharedPreferences.edit().clear().commit();
    }
}
