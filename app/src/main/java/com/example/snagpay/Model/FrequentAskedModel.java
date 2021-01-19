package com.example.snagpay.Model;

import java.util.ArrayList;

public class FrequentAskedModel {

    private String question;
    private ArrayList<DetailFrequentAskedModel> detailFrequentAskedModels;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<DetailFrequentAskedModel> getDetailFrequentAskedModels() {
        return detailFrequentAskedModels;
    }

    public void setDetailFrequentAskedModels(ArrayList<DetailFrequentAskedModel> detailFrequentAskedModels) {
        this.detailFrequentAskedModels = detailFrequentAskedModels;
    }
}
