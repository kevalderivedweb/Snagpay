package com.example.snagpay.Model;

import java.util.ArrayList;

public class PaymentRecent {

    String wallet_credit;
    String transaction_title;
    String datetime;
    String e_wallet_id;
    String transaction_type;
    ArrayList<DetailPaymentModel> detailPaymentModels;

    public ArrayList<DetailPaymentModel> getDetailPaymentModels() {
        return detailPaymentModels;
    }

    public void setDetailPaymentModels(ArrayList<DetailPaymentModel> detailPaymentModels) {
        this.detailPaymentModels = detailPaymentModels;
    }



    public String getWallet_credit() {
        return wallet_credit;
    }

    public void setWallet_credit(String wallet_credit) {
        this.wallet_credit = wallet_credit;
    }

    public String getTransaction_title() {
        return transaction_title;
    }

    public void setTransaction_title(String transaction_title) {
        this.transaction_title = transaction_title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getE_wallet_id() {
        return e_wallet_id;
    }

    public void setE_wallet_id(String e_wallet_id) {
        this.e_wallet_id = e_wallet_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
