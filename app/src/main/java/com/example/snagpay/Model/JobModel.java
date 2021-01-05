package com.example.snagpay.Model;

import java.util.ArrayList;

public class JobModel {

    private String jobMain;
    private ArrayList<DetailJobModel> detailJobModels;

    public String getJobMain() {
        return jobMain;
    }

    public void setJobMain(String jobMain) {
        this.jobMain = jobMain;
    }

    public ArrayList<DetailJobModel> getDetailJobModels() {
        return detailJobModels;
    }

    public void setDetailJobModels(ArrayList<DetailJobModel> detailJobModels) {
        this.detailJobModels = detailJobModels;
    }
}
