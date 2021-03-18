package com.example.snagpay.Model;

public class MonthlyView {

    String e_wallet_id;
    String e_wallet_tran_code;
    String wallet_credit;
    String datetime;
    String transaction_title;
    String transaction_type;
    String balance;

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getE_wallet_id() {
        return e_wallet_id;
    }

    public void setE_wallet_id(String e_wallet_id) {
        this.e_wallet_id = e_wallet_id;
    }

    public String getE_wallet_tran_code() {
        return e_wallet_tran_code;
    }

    public void setE_wallet_tran_code(String e_wallet_tran_code) {
        this.e_wallet_tran_code = e_wallet_tran_code;
    }

    public String getWallet_credit() {
        return wallet_credit;
    }

    public void setWallet_credit(String wallet_credit) {
        this.wallet_credit = wallet_credit;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTransaction_title() {
        return transaction_title;
    }

    public void setTransaction_title(String transaction_title) {
        this.transaction_title = transaction_title;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
