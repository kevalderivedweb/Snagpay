package com.example.snagpay.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snagpay.Adapter.ExpandableListAdapterFilterSort;
import com.example.snagpay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_FilterSortBy extends AppCompatActivity {

    private Button btnFilter;
    private ImageView backToCategoriesInner;

    ExpandableListAdapterFilterSort listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataSubHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_sort_by);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToCategoriesInner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setChildDivider(getResources().getDrawable(R.color.white));

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapterFilterSort(this, listDataHeader, listDataSubHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
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

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataSubHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Short");
        listDataHeader.add("Category");
        listDataHeader.add("Price");

        listDataSubHeader.add("Relevance");
        listDataSubHeader.add("Service");
        listDataSubHeader.add("$0.0 - $50.0");

        // Adding child data
        List<String> mShort = new ArrayList<String>();
        mShort.add("Relevance");
        mShort.add("Price:Low to High");
        mShort.add("Price:High to Low");
        mShort.add("Rating:High to Low");
        mShort.add("Rating:Low to High");

        List<String> Category = new ArrayList<String>();
        Category.add("Service");
        Category.add("Service");
        Category.add("Service");
        Category.add("Service");
        Category.add("Service");

        List<String> Price = new ArrayList<String>();
        Price.add("$0.0 - $50.0");
        Price.add("$0.0 - $50.0");
        Price.add("$0.0 - $50.0");
        Price.add("$0.0 - $50.0");

        listDataChild.put(listDataHeader.get(0), mShort); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Category);
        listDataChild.put(listDataHeader.get(2), Price);
    }

}