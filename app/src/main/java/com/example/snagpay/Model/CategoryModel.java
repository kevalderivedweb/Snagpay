package com.example.snagpay.Model;

import java.util.ArrayList;

public class CategoryModel {

    String category_id;
    String category_name;
    String slug;
    String backround_color;
    String category_image;
    String parent_id;
    String parent_level;
    public ArrayList<SubCategoriesModel> subCategoriesModelArrayList;

    public ArrayList<SubCategoriesModel> getSubCategoriesModelArrayList() {
        return subCategoriesModelArrayList;
    }

    public void setSubCategoriesModelArrayList(ArrayList<SubCategoriesModel> subCategoriesModelArrayList) {
        this.subCategoriesModelArrayList = subCategoriesModelArrayList;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getBackround_color() {
        return backround_color;
    }

    public void setBackround_color(String backround_color) {
        this.backround_color = backround_color;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_level() {
        return parent_level;
    }

    public void setParent_level(String parent_level) {
        this.parent_level = parent_level;
    }
}
