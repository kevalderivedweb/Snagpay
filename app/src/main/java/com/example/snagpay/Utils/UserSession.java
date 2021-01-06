package com.example.snagpay.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    public String BASEURL = "http://chessmafia.com/php/snagpay/web/api/";

    private static final String PREF_NAME = "UserSessionPref";


    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";
    private static final String POST_CODE = "postCode";
    private static final String CITY_ID = "cityID";

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

    public void setLatitude(String latitude){
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    public String getLatitude() {
        return sharedPreferences.getString(LATITUDE, "");
    }

    public void setLongitude(String longitude){
        editor.putString(LATITUDE, longitude);
        editor.commit();
    }

    public String getLongitude() {
        return sharedPreferences.getString(LONGITUDE, "");
    }

    public void setAddress(String address){
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public String getAddress() {
        return sharedPreferences.getString(ADDRESS, "");
    }

    public void setCity(String city){
        editor.putString(CITY, city);
        editor.commit();
    }

    public String getCity() {
        return sharedPreferences.getString(CITY, "");
    }

    public void setState(String state){
        editor.putString(STATE, state);
        editor.commit();
    }

    public String getState() {
        return sharedPreferences.getString(STATE, "");
    }

    public void setCountry(String country){
        editor.putString(COUNTRY, country);
        editor.commit();
    }

    public String getCountry() {
        return sharedPreferences.getString(COUNTRY, "");
    }

    public void setPostCode(String postCode){
        editor.putString(POST_CODE, postCode);
        editor.commit();
    }

    public String getPostCode() {
        return sharedPreferences.getString(POST_CODE, "");
    }

    public void setCityId(String cityId){
        editor.putString(CITY_ID, cityId);
        editor.commit();
    }

    public String getCityId() {
        return sharedPreferences.getString(CITY_ID, "");
    }
}
