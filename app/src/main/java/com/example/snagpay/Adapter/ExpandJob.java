package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.JobModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class ExpandJob extends BaseExpandableListAdapter {

    private Context _context;

    ArrayList<JobModel> jobModelArrayList;

    private RecyclerView jobRes;
    private JobsExpandAdapter jobsExpandAdapter;

    public ExpandJob (Context context, ArrayList<JobModel> jobModelArrayList) {
        this._context = context;
        this.jobModelArrayList = jobModelArrayList;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return jobModelArrayList.get(groupPosition).getDetailJobModels().get(childPosititon).getJobTitle();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_jobs, null);
        }

        jobRes = convertView.findViewById(R.id.jobsRes);

        jobRes.setLayoutManager(new LinearLayoutManager(jobRes.getContext()));
        jobsExpandAdapter = new JobsExpandAdapter(jobRes.getContext(), jobModelArrayList.get(groupPosition).getDetailJobModels());
        jobRes.setAdapter(jobsExpandAdapter);
        jobsExpandAdapter.notifyDataSetChanged();

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.jobModelArrayList.size();
    }

    @Override
    public int getGroupCount() {
        return jobModelArrayList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_jobs, null);
        }

        ImageView ivGroupIndicator = convertView.findViewById(R.id.ivJobsIndicator);

        TextView txtListGroupJobs = (TextView) convertView.findViewById(R.id.txtListGroupJobs);

        txtListGroupJobs.setText( jobModelArrayList.get(groupPosition).getJobMain());

        ivGroupIndicator.setSelected(isExpanded);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
