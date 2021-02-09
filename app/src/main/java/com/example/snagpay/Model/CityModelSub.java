package com.example.snagpay.Model;

import java.util.ArrayList;

public class CityModelSub {

    String categoryName;
    String categoryId;
    ArrayList<CityModelSubSub> cityModelSubSubArrayList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ArrayList<CityModelSubSub> getCityModelSubSubArrayList() {
        return cityModelSubSubArrayList;
    }

    public void setCityModelSubSubArrayList(ArrayList<CityModelSubSub> cityModelSubSubArrayList) {
        this.cityModelSubSubArrayList = cityModelSubSubArrayList;
    }
}
