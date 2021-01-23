package com.example.snagpay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.snagpay.Adapter.ExpListAdapterFrequentAsked;
import com.example.snagpay.Adapter.ExpListAdapterPaymentRecent;
import com.example.snagpay.Model.DetailFrequentAskedModel;
import com.example.snagpay.Model.DetailPaymentModel;
import com.example.snagpay.Model.FrequentAskedModel;
import com.example.snagpay.Model.PaymentModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class Activity_Faq extends AppCompatActivity {

    private ExpListAdapterFrequentAsked expListAdapterFrequentAsked;
    private ExpandableListView expandableListView;
    private ArrayList<FrequentAskedModel> frequentAskedModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        findViewById(R.id.backToPaymentInfo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandableListView = findViewById(R.id.lvExpQuestions);
        expandableListView.setChildDivider(getResources().getDrawable(R.color.white));

        // preparing list data


        expListAdapterFrequentAsked = new ExpListAdapterFrequentAsked(Activity_Faq.this, frequentAskedModelArrayList);
        // setting list adapter
        expandableListView.setAdapter(expListAdapterFrequentAsked);
        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        prepareListData();

        expListAdapterFrequentAsked.notifyDataSetChanged();

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });

    }

    private void prepareListData() {

        for (int i=0; i<8; i++){
            FrequentAskedModel frequentAskedModel = new FrequentAskedModel();
            frequentAskedModel.setQuestion("Lorem ipsum is simply dummy text of the printing and typesetting industry?");

            ArrayList<DetailFrequentAskedModel> detailFrequentAskedModelArrayList = new ArrayList<>();

            DetailFrequentAskedModel detailFrequentAskedModel = new DetailFrequentAskedModel();
            detailFrequentAskedModel.setAnswer("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

            detailFrequentAskedModelArrayList.add(detailFrequentAskedModel);

            frequentAskedModel.setDetailFrequentAskedModels(detailFrequentAskedModelArrayList);
            frequentAskedModelArrayList.add(frequentAskedModel);

            Log.e("exppp", frequentAskedModelArrayList.get(0).getDetailFrequentAskedModels().get(0).getAnswer() + " 22");
        }
    }
}