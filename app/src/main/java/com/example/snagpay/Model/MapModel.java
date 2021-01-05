package com.example.snagpay.Model;

public class MapModel {

    private String name;
    private double lat;
    private double lng;
    private String place;
    private String rating;
    private String price;
    private String offer;
    private String bought;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getOffer() {
        return offer;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public String getBought() {
        return bought;
    }

}
