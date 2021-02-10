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
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";
    private static final String POST_CODE = "postCode";


    private final String USER_ID = "User_id";
    private final String FIRSTNAME = "first_name";
    private final String LASTNAME = "last_name";
    private final String EMAIL = "email";
    private final String PHONENO = "phone_no";
    private final String FACEBOOKID = "facebook_id";
    private final String GOOGLEID = "google_id";
    private final String TYPE = "type";
    private final String ADDRESS = "prof_address";
    private final String CITY_ID = "cityID";
    private final String STATEID = "state_id";
    private final String COUNTRYID = "country_id";
    private final String POSTCODE = "postcode";
    private final String EMAILVERIFY = "is_email_verified";
    private final String OTP = "otp";
    private final String LAT = "latitude";
    private final String LONG = "longitude";
    private final String BUSS_NAME = "business_name";
    private final String BUSS_TYPE = "type_of_business";
    private final String CREDIT_REPORT = "can_we_run_credit_report";
    private final String AVG_SALES = "avg_sales_per_month";
    private final String HOW_LONG = "how_long_have_you";
    private final String PHYSICAL_LOCATION = "no_of_physical_locations";
    private final String WEBSITE = "website_or_page";
    private final String GOODS = "cost_of_goods";
    private final String ISAPPROVE = "is_approved";
    private final String APITOKEN = "api_token";

    private final String IMPORTANTNOTIFY = "importat_message_alert";
    private final String SHIPMENTSNOTIFY = "shipments_notifications";
    private final String PERSONALISEDNOTIFY = "personalised_notifications";
    private final String WISHLISTNOTIFY = "wishlist_notifications";


    public UserSession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    public void createLoginSession(String user_id,
                                   String first_name,
                                   String last_name,
                                   String email,
                                   String phone_no,
                                   String facebook_id,
                                   String google_id,
                                   String type,
                                   String address,
                                   String city_id,
                                   String state_id,
                                   String country_id,
                                   String postcode,
                                   String is_email_verified,
                                   String otp,
                                   String latitude,
                                   String longitude,
                                   String business_name,
                                   String type_of_business,
                                   String can_we_run_credit_report,
                                   String avg_sales_per_month,
                                   String how_long_have_you,
                                   String no_of_physical_locations,
                                   String website_or_page,
                                   String cost_of_goods,
                                   String is_approved,
                                   String api_token
    ) {

        editor.putString(USER_ID, user_id);
        editor.putString(FIRSTNAME, first_name);
        editor.putString(LASTNAME, last_name);
        editor.putString(EMAIL, email);
        editor.putString(PHONENO, phone_no);
        editor.putString(FACEBOOKID, facebook_id);
        editor.putString(GOOGLEID, google_id);
        editor.putString(TYPE, type);
        editor.putString(ADDRESS, address);
        editor.putString(CITY_ID, city_id);
        editor.putString(STATEID, state_id);
        editor.putString(COUNTRYID, country_id);
        editor.putString(POSTCODE, postcode);
        editor.putString(EMAILVERIFY, is_email_verified);
        editor.putString(OTP, otp);
        editor.putString(LAT, latitude);
        editor.putString(LONG, longitude);
        editor.putString(BUSS_NAME, business_name);
        editor.putString(BUSS_TYPE, type_of_business);
        editor.putString(CREDIT_REPORT, can_we_run_credit_report);
        editor.putString(AVG_SALES, avg_sales_per_month);
        editor.putString(HOW_LONG, how_long_have_you);
        editor.putString(PHYSICAL_LOCATION, no_of_physical_locations);
        editor.putString(WEBSITE, website_or_page);
        editor.putString(GOODS, cost_of_goods);
        editor.putString(ISAPPROVE, is_approved);
        editor.putString(APITOKEN, api_token);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }



    public boolean logout() {
        return sharedPreferences.edit().clear().commit();
    }

    public void setLatitude(String latitude) {
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    public String getLatitude() {
        return sharedPreferences.getString(LATITUDE, "");
    }

    public void setIMPORTANTNOTIFY(String importantnotify) {
        editor.putString(IMPORTANTNOTIFY, importantnotify);
        editor.commit();
    }

    public String getIMPORTANTNOTIFY() {
        return sharedPreferences.getString(IMPORTANTNOTIFY, "");
    }

    public void setSHIPMENTSNOTIFY(String shipmentsnotify) {
        editor.putString(SHIPMENTSNOTIFY, shipmentsnotify);
        editor.commit();
    }

    public String getSHIPMENTSNOTIFY() {
        return sharedPreferences.getString(SHIPMENTSNOTIFY, "");
    }

    public void setPERSONALISEDNOTIFY(String personalisednotify) {
        editor.putString(PERSONALISEDNOTIFY, personalisednotify);
        editor.commit();
    }

    public String getPERSONALISEDNOTIFY() {
        return sharedPreferences.getString(PERSONALISEDNOTIFY, "");
    }

    public void setWISHLISTNOTIFY(String wishlistnotify) {
        editor.putString(WISHLISTNOTIFY, wishlistnotify);
        editor.commit();
    }

    public String getWISHLISTNOTIFY() {
        return sharedPreferences.getString(WISHLISTNOTIFY, "");
    }

    public void setLongitude(String longitude) {
        editor.putString(LONGITUDE, longitude);
        editor.commit();
    }

    public String getLongitude() {
        return sharedPreferences.getString(LONGITUDE, "");
    }

    public void setAddress(String address) {
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public String getAddress() {
        return sharedPreferences.getString(ADDRESS, "");
    }

    public void setCity(String city) {
        editor.putString(CITY, city);
        editor.commit();
    }

    public String getCity() {
        return sharedPreferences.getString(CITY, "");
    }

    public void setState(String state) {
        editor.putString(STATE, state);
        editor.commit();
    }

    public String getState() {
        return sharedPreferences.getString(STATE, "");
    }

    public void setCountry(String country) {
        editor.putString(COUNTRY, country);
        editor.commit();
    }

    public String getCountry() {
        return sharedPreferences.getString(COUNTRY, "");
    }

    public void setPostCode(String postCode) {
        editor.putString(POST_CODE, postCode);
        editor.commit();
    }

    public String getPostCode() {
        return sharedPreferences.getString(POST_CODE, "");
    }

    public void setCityId(String cityId) {
        editor.putString(CITY_ID, cityId);
        editor.commit();
    }

    public String getCityId() {
        return sharedPreferences.getString(CITY_ID, "");
    }

    public void setUSER_ID(String user_id) {
        editor.putString(USER_ID, user_id);
        editor.commit();
    }

    public String getUSER_ID() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public void setFIRSTNAME(String firstname) {
        editor.putString(FIRSTNAME, firstname);
        editor.commit();
    }

    public String getFIRSTNAME() {
        return sharedPreferences.getString(FIRSTNAME, "");
    }

    public void setLASTNAME(String lastname) {
        editor.putString(LASTNAME, lastname);
        editor.commit();
    }

    public String getLASTNAME() {
        return sharedPreferences.getString(LASTNAME, "");
    }

    public void setEMAIL(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEMAIL() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setFACEBOOKID(String facebookid) {
        editor.putString(FACEBOOKID, facebookid);
        editor.commit();
    }

    public String getFACEBOOKID() {
        return sharedPreferences.getString(FACEBOOKID, "");
    }

    public void setGOOGLEID(String googleid) {
        editor.putString(GOOGLEID, googleid);
        editor.commit();
    }

    public String getGOOGLEID() {
        return sharedPreferences.getString(GOOGLEID, "");
    }

    public void setPHONENO(String phoneno) {
        editor.putString(PHONENO, phoneno);
        editor.commit();
    }

    public String getPHONENO() {
        return sharedPreferences.getString(PHONENO, "");
    }

    public void setTYPE(String type) {
        editor.putString(TYPE, type);
        editor.commit();
    }

    public String getTYPE() {
        return sharedPreferences.getString(TYPE, "");
    }

    public void setADDRESS(String address) {
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public String getADDRESS() {
        return sharedPreferences.getString(ADDRESS, "");
    }

    public String getSTATEID() {
        return sharedPreferences.getString(STATEID, "");
    }

    public void setSTATEID(String stateid) {
        editor.putString(STATEID, stateid);
        editor.commit();
    }

    public void setCOUNTRYID(String countryid) {
        editor.putString(COUNTRYID, countryid);
        editor.commit();
    }

    public String getCOUNTRYID() {
        return sharedPreferences.getString(COUNTRYID, "");
    }

    public void setPOSTCODE(String postcode) {
        editor.putString(POSTCODE, postcode);
        editor.commit();
    }

    public String getPOSTCODE() {
        return sharedPreferences.getString(POSTCODE, "");
    }

    public void setEMAILVERIFY(String emailverify) {
        editor.putString(EMAILVERIFY, emailverify);
        editor.commit();
    }

    public String getEMAILVERIFY() {
        return sharedPreferences.getString(EMAILVERIFY, "");
    }

    public void setOTP(String otp) {
        editor.putString(OTP, otp);
        editor.commit();
    }
    public String getOTP() {
        return sharedPreferences.getString(OTP, "");
    }

    public void setLAT(String lat) {
        editor.putString(LAT, lat);
        editor.commit();
    }
    public String getLAT() {
        return sharedPreferences.getString(LAT, "");
    }

    public void setLONG(String mLong) {
        editor.putString(LONG, mLong);
        editor.commit();
    }
    public String getLONG() {
        return sharedPreferences.getString(LONG, "");
    }

    public void setBUSS_NAME(String bussName) {
        editor.putString(BUSS_NAME, bussName);
        editor.commit();
    }

    public String getBUSS_NAME() {
        return sharedPreferences.getString(BUSS_NAME, "");
    }

    public String getBUSS_TYPE() {
        return sharedPreferences.getString(TYPE, "");
    }

    public void setBUSS_TYPE(String buss_type) {
        editor.putString(BUSS_TYPE, buss_type);
        editor.commit();
    }

    public String getCREDIT_REPORT() {
        return sharedPreferences.getString(CREDIT_REPORT, "");
    }

    public void setCREDIT_REPORT(String credit_report) {
        editor.putString(CREDIT_REPORT, credit_report);
        editor.commit();
    }

    public void setAVG_SALES(String avgSales) {
        editor.putString(AVG_SALES, avgSales);
        editor.commit();
    }

    public String getAVG_SALES() {
        return sharedPreferences.getString(AVG_SALES, "");
    }

    public void setHOW_LONG(String how_long) {
        editor.putString(HOW_LONG, how_long);
        editor.commit();
    }

    public String getHOW_LONG() {
        return sharedPreferences.getString(HOW_LONG, "");
    }

    public void setPHYSICAL_LOCATION(String physical_location) {
        editor.putString(PHYSICAL_LOCATION, physical_location);
        editor.commit();
    }

    public String getPHYSICAL_LOCATION() {
        return sharedPreferences.getString(PHYSICAL_LOCATION, "");
    }

    public void setWEBSITE(String website) {
        editor.putString(WEBSITE, website);
        editor.commit();
    }

    public String getWEBSITE() {
        return sharedPreferences.getString(WEBSITE, "");
    }

    public void setGOODS(String goods) {
        editor.putString(GOODS, goods);
        editor.commit();
    }

    public String getGOODS() {
        return sharedPreferences.getString(GOODS, "");
    }

    public void setISAPPROVE(String isapprove) {
        editor.putString(ISAPPROVE, isapprove);
        editor.commit();
    }

    public String getISAPPROVE() {
        return sharedPreferences.getString(ISAPPROVE, "");
    }

    public void setAPITOKEN(String apitoken) {
        editor.putString(APITOKEN, apitoken);
        editor.commit();
    }

    public String getAPITOKEN() {
        return sharedPreferences.getString(APITOKEN, "");
    }


    public void stayLoggedIn(boolean b) {
        editor.putBoolean(IS_LOGIN, b);
        editor.commit();
    }

    public boolean getStayLogIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
