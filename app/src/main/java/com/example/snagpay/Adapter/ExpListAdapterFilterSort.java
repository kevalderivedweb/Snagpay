package com.example.snagpay.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.snagpay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpListAdapterFilterSort extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	private List<String> _listDataSubHeader; // Subheader titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private final OnItemClickListener listener;
	private int mGroupPosition;
	private int mChildPosition;

	public ExpListAdapterFilterSort(Context context, List<String> listDataHeader,
									ArrayList<String> listDataSubHeader, HashMap<String, List<String>> listChildData, OnItemClickListener onItemClickListener) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataSubHeader = listDataSubHeader;
		this._listDataChild = listChildData;
		this.listener = onItemClickListener;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item_filter_sort, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio);

		txtListChild.setText(childText);

		radioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(groupPosition,childPosition);
			}
		});

		if(mGroupPosition==groupPosition){

			if(mChildPosition==childPosition){
				_listDataSubHeader.set(mGroupPosition,childText);
				radioButton.setChecked(true);
			}else {
				radioButton.setChecked(false);
			}
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group_filter_sort, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		TextView subHeader = (TextView) convertView.findViewById(R.id.subHeader);
		subHeader.setTypeface(null, Typeface.BOLD);
		subHeader.setText(_listDataSubHeader.get(groupPosition));


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

	public void filterList(int groupPosition, int childPosition) {
		mGroupPosition = groupPosition;
		mChildPosition = childPosition;
		notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(int groupPosition, int childPosition);
	}

}
