package com.example.snagpay.Model;

public class EGiftCardModel {

    String e_gift_card_id;
    String e_gift_card_code;
    String user_id;
    String order_id;
    String amount;
    String qty;
    String qrcode;
    String status;
    String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getE_gift_card_id() {
        return e_gift_card_id;
    }

    public void setE_gift_card_id(String e_gift_card_id) {
        this.e_gift_card_id = e_gift_card_id;
    }

    public String getE_gift_card_code() {
        return e_gift_card_code;
    }

    public void setE_gift_card_code(String e_gift_card_code) {
        this.e_gift_card_code = e_gift_card_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
