package com.example.snagpay.Model;

public class GiftDetailModel {

    String e_gift_card_transaction_id;
    String e_gift_card_id;
    String e_gift_card_tran_code;
    String amount;
    String is_paid_additional_amount;

    public String getE_gift_card_transaction_id() {
        return e_gift_card_transaction_id;
    }

    public void setE_gift_card_transaction_id(String e_gift_card_transaction_id) {
        this.e_gift_card_transaction_id = e_gift_card_transaction_id;
    }

    public String getE_gift_card_id() {
        return e_gift_card_id;
    }

    public void setE_gift_card_id(String e_gift_card_id) {
        this.e_gift_card_id = e_gift_card_id;
    }

    public String getE_gift_card_tran_code() {
        return e_gift_card_tran_code;
    }

    public void setE_gift_card_tran_code(String e_gift_card_tran_code) {
        this.e_gift_card_tran_code = e_gift_card_tran_code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIs_paid_additional_amount() {
        return is_paid_additional_amount;
    }

    public void setIs_paid_additional_amount(String is_paid_additional_amount) {
        this.is_paid_additional_amount = is_paid_additional_amount;
    }
}
