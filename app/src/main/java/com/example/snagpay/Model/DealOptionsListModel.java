package com.example.snagpay.Model;

public class DealOptionsListModel {

    String deal_id;
    String deal_option_id;
    String deal_option_name;
    String sell_price;
    String deal_option_description;
    String deal_option_code;
    String available_stock_qty;

    public String getAvailable_stock_qty() {
        return available_stock_qty;
    }

    public void setAvailable_stock_qty(String available_stock_qty) {
        this.available_stock_qty = available_stock_qty;
    }

    public String getDeal_option_code() {
        return deal_option_code;
    }

    public void setDeal_option_code(String deal_option_code) {
        this.deal_option_code = deal_option_code;
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

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getDeal_option_description() {
        return deal_option_description;
    }

    public void setDeal_option_description(String deal_option_description) {
        this.deal_option_description = deal_option_description;
    }
}
