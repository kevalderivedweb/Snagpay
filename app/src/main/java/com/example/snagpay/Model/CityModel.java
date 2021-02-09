package com.example.snagpay.Model;

import java.util.ArrayList;

public class CityModel {

    String Cityname;
    String CityId;
    boolean isChecked;
    ArrayList<CityModelSub> cityModelSubArrayList;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCityname() {
        return Cityname;
    }

    public void setCityname(String cityname) {
        Cityname = cityname;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public ArrayList<CityModelSub> getCityModelSubArrayList() {
        return cityModelSubArrayList;
    }

    public void setCityModelSubArrayList(ArrayList<CityModelSub> cityModelSubArrayList) {
        this.cityModelSubArrayList = cityModelSubArrayList;
    }
}
