package com.example.snagpay.Model;

public class DealsOrderModel {

    String deal_id;
    String title;
    String deal_option_id;
    String deal_option_name;
    String deal_image;
    String qty;
    String sell_price;

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeal_option_id() {
        return deal_option_id;
    }

    public void setDeal_option_id(String deal_option_id) {
        this.deal_option_id = deal_option_id;
    }

    public String getDeal_option_name() {
        return deal_option_name;
    }

    public void setDeal_option_name(String deal_option_name) {
        this.deal_option_name = deal_option_name;
    }

    public String getDeal_image() {
        return deal_image;
    }

    public void setDeal_image(String deal_image) {
        this.deal_image = deal_image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }
}
