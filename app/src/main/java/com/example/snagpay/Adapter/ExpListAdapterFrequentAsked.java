package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snagpay.Model.FrequentAskedModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class ExpListAdapterFrequentAsked extends BaseExpandableListAdapter {

    private Context _context;

    private ArrayList<FrequentAskedModel> frequentAskedModelArrayList;

    public ExpListAdapterFrequentAsked(Context context, ArrayList<FrequentAskedModel> frequentAskedModelArrayList) {
        this._context = context;
        this.frequentAskedModelArrayList = frequentAskedModelArrayList;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return 1;
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
            convertView = infalInflater.inflate(R.layout.list_item_frequent_asked, null);
        }

        TextView askedFrequentListExp = (TextView) convertView.findViewById(R.id.askedFrequentListExp);



        askedFrequentListExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askedFrequentListExp.setClickable(false);
            }
        });

        askedFrequentListExp.setText(frequentAskedModelArrayList.get(groupPosition).getDetailFrequentAskedModels().get(childPosition).getAnswer());

        Log.e("expList", frequentAskedModelArrayList.get(0).getDetailFrequentAskedModels().get(0).getAnswer() + " 111");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return frequentAskedModelArrayList.get(groupPosition).getDetailFrequentAskedModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.frequentAskedModelArrayList.size();
    }

    @Override
    public int getGroupCount() {
        return frequentAskedModelArrayList.size();
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
            convertView = infalInflater.inflate(R.layout.list_group_frequent_asked, null);
        }

        ImageView ivGroupIndicator = convertView.findViewById(R.id.ivJobsIndicator);

        TextView txtListGroupFrequent = (TextView) convertView.findViewById(R.id.txtListGroupFrequent);

        txtListGroupFrequent.setText( frequentAskedModelArrayList.get(groupPosition).getQuestion());

        ivGroupIndicator.setSelected(isExpanded);

        Log.e("expgrooList", frequentAskedModelArrayList.get(groupPosition).getQuestion() + " 111");

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
