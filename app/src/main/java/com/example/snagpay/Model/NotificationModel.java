package com.example.snagpay.Model;

public class NotificationModel {

    String global_notification_id;
    String image;
    String title;
    String description;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGlobal_notification_id() {
        return global_notification_id;
    }

    public void setGlobal_notification_id(String global_notification_id) {
        this.global_notification_id = global_notification_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
