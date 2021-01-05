package com.example.snagpay.Model;

import java.util.ArrayList;

public class PaymentModel {

    private String statusPayment;
    private String card;
    private String date;
    private String price;
    ArrayList<DetailPaymentModel> detailPaymentModels;

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<DetailPaymentModel> getDetailPaymentModels() {
        return detailPaymentModels;
    }

    public void setDetailPaymentModels(ArrayList<DetailPaymentModel> detailPaymentModels) {
        this.detailPaymentModels = detailPaymentModels;
    }
}
