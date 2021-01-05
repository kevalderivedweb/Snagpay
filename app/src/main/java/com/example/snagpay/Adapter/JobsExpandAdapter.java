package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.DetailJobModel;
import com.example.snagpay.Model.JobModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class JobsExpandAdapter extends RecyclerView.Adapter<JobsExpandAdapter.Viewholder> {

    private Context mContext;
    private ArrayList<DetailJobModel> jobModelArrayList;

    public JobsExpandAdapter(Context mContext, ArrayList<DetailJobModel> jobModelArrayList) {
        this.mContext = mContext;
        this.jobModelArrayList = jobModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_expand_jobs, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.jobTitle.setText(jobModelArrayList.get(0).getJobTitle());
        holder.jobLocation.setText(jobModelArrayList.get(0).getJobLocation());
        holder.jobCategory.setText(jobModelArrayList.get(0).getJobCategory());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView jobTitle, jobLocation, jobCategory;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobCategory = itemView.findViewById(R.id.jobCategory);
        }
    }
}
