package com.example.snagpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.snagpay.Adapter.AdapterAddAnotherCity;
import com.example.snagpay.Adapter.CityNameListAdapter;

import java.util.ArrayList;

public class ManageMyWishListActivity extends AppCompatActivity {

    private RecyclerView recAddAnotherCity;
    private Button btnAddAnotherCity, btnAddNewCategory;
    private String[] cityName = {"Phoenix", "Tuscon", "Mesa", "Boston", "Scottsdale", "Gilbert", "Glendale", "Tampe"};
    private ArrayList<String> addCityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_wish_list);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        recAddAnotherCity = findViewById(R.id.recAddAnotherCity);
        btnAddAnotherCity = findViewById(R.id.btnAddAnotherCity);
        btnAddNewCategory = findViewById(R.id.btnAddNewCategory);

        recAddAnotherCity.setLayoutManager(new LinearLayoutManager(ManageMyWishListActivity.this));
        CityNameListAdapter cityNameListAdapter = new CityNameListAdapter(this, addCityList, new CityNameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item) {
                addCityList.remove(item);
            }
        });

        recAddAnotherCity.setAdapter(cityNameListAdapter);

        findViewById(R.id.backToFragMyStuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddAnotherCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ManageMyWishListActivity.this);
                dialog.setContentView(R.layout.custom_dialog_city);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                RecyclerView recyclerViewCity = dialog.findViewById(R.id.dialogCityList);
                recyclerViewCity.setLayoutManager(new LinearLayoutManager(dialog.getContext()));

                AdapterAddAnotherCity adapterAddAnotherCity1 = new AdapterAddAnotherCity(ManageMyWishListActivity.this,cityName, new AdapterAddAnotherCity.OnItemClickListener() {
                    @Override
                    public void onItemClick(int item) {
                        addCityList.add(cityName[item]);
                    }
                });
                recyclerViewCity.setAdapter(adapterAddAnotherCity1);

                dialog.findViewById(R.id.dialogCityClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.btnDialogAddCity).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityNameListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ManageMyWishListActivity.this);
                dialog.setContentView(R.layout.custom_dialog_category);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                String[] Home = {"Home"};
                String[] Service = {"Service"};
                String[] Gardener = {"Gardener"};

                Spinner spinnerService = dialog.findViewById(R.id.spinnerService);
                Spinner spinnerHome = dialog.findViewById(R.id.spinnerHome);
                Spinner spinnerGardener = dialog.findViewById(R.id.spinnerGardener);

                ArrayAdapter<String> dataAdapterService = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Service);
                dataAdapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerService.setAdapter(dataAdapterService);

                ArrayAdapter<String> dataAdapterHome = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Home);
                dataAdapterHome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHome.setAdapter(dataAdapterHome);

                ArrayAdapter<String> dataAdapterGardener = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, Gardener);
                dataAdapterGardener.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGardener.setAdapter(dataAdapterGardener);

                dialog.findViewById(R.id.dialogCategoryClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnDialogAddCategory).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}