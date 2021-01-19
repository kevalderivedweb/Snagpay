package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.snagpay.Adapter.ExpListAdapterJob;
import com.example.snagpay.Model.DetailJobModel;
import com.example.snagpay.Model.JobModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class Activity_Jobs extends AppCompatActivity {

    private ExpListAdapterJob expandJob;
    private ExpandableListView expListView;
    private ArrayList<JobModel> jobModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        expListView = findViewById(R.id.jobListExpand);
        expListView.setDivider(getResources().getDrawable(R.color.white));
        expListView.setChildDivider(getResources().getDrawable(R.color.light_gray));

        expandJob = new ExpListAdapterJob(Activity_Jobs.this, jobModelArrayList);
        // setting list adapter
        expListView.setAdapter(expandJob);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        setJobData();
    }

    private void setJobData() {

            for (int i = 0; i<8; i++){
                JobModel jobModel = new JobModel();
                jobModel.setJobMain("Sales");

                ArrayList<DetailJobModel> detailJobModelArrayList = new ArrayList<>();

                DetailJobModel detailJobModel = new DetailJobModel();
                detailJobModel.setJobTitle("Lorem ipsum");
                detailJobModel.setJobLocation("Chicago");
                detailJobModel.setJobCategory("Sales & Buying");

                detailJobModelArrayList.add(detailJobModel);

                jobModel.setDetailJobModels(detailJobModelArrayList);

                jobModelArrayList.add(jobModel);
            }
    }
}