package com.example.snagpay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snagpay.Adapter.ExpListAdapterFilterSort;
import com.example.snagpay.R;
import com.example.snagpay.Utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_FilterSortBy extends AppCompatActivity {

    private ExpListAdapterFilterSort listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ArrayList<String> listDataSubHeader;
    private String newString;

    private List<String> CategoryId;
    private String idCategory = "0";
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_sort_by);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        session = new UserSession(Activity_FilterSortBy.this);

        findViewById(R.id.backToCategoriesInner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString = null;
            } else {
                newString = extras.getString("category");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("category");
        }



        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        try {
            prepareListData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listAdapter = new ExpListAdapterFilterSort(this, listDataHeader,listDataSubHeader, listDataChild, new ExpListAdapterFilterSort.OnItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition) {

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition))
                        .get(childPosition), Toast.LENGTH_SHORT)
                        .show();

                if (groupPosition == 1){
                    idCategory = CategoryId.get(childPosition);

                }
                listAdapter.filterList(groupPosition, childPosition);

                Log.e("position", groupPosition + "---" + childPosition);


            }
        });

        // setting list adapter
        expListView.setAdapter(listAdapter);


        findViewById(R.id.btnFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0 ; i < listDataSubHeader.size() ; i++){

                    Log.e("listDataSubHeader",listDataSubHeader.get(i));

                }

                Intent intent = new Intent();
                intent.putExtra("listShort", listDataSubHeader.get(0));
                intent.putExtra("listCategory", idCategory);
                intent.putExtra("listPrice", listDataSubHeader.get(2));
                setResult(2,intent);
                finish();
            }
        });



    }


    private void prepareListData() throws JSONException {
        listDataHeader = new ArrayList<String>();
        listDataSubHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Sort");
        listDataHeader.add("Category");
        listDataHeader.add("Price");



        // Adding child data
        List<String> mShort = new ArrayList<String>();
        mShort.add("Price : Low to High");
        mShort.add("Price : High to Low");
        mShort.add("Rating : High to Low");
        mShort.add("Rating : Low to High");


        List<String> Category = new ArrayList<String>();
        CategoryId = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray(newString);

        listDataSubHeader.add("Price : Low to High");
        listDataSubHeader.add(jsonArray.getJSONObject(0).getString("category_name"));
        listDataSubHeader.add("$0.0 - $50.0");

        for (int i = 0 ; i<jsonArray.length() ; i++){
            JSONObject object = jsonArray.getJSONObject(i);
            Category.add(object.getString("category_name"));
            CategoryId.add(object.getString("category_id"));
        }

        List<String> Price = new ArrayList<String>();
        Price.add("$0.0 - $50.0");
        Price.add("$50.00 - $100.0");
        Price.add("$100.0 - $150.0");
        Price.add("$150.0 - $200.0");

        listDataChild.put(listDataHeader.get(0), mShort); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Category);
        listDataChild.put(listDataHeader.get(2), Price);
    }


}