package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CityModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterAddAnotherCity extends RecyclerView.Adapter<AdapterAddAnotherCity.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private ArrayList<CityModel> mDataCity;
    private ArrayList<CityModel> addCityList;

    public AdapterAddAnotherCity(Context mContext, ArrayList<CityModel> mDataCity, ArrayList<CityModel> addCityList, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.mDataCity = mDataCity;
        this.listener = onItemClickListener;
        this.addCityList = addCityList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_add_another_city, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.cityName.setText(mDataCity.get(position).getCityname());

        holder.checkBoxAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(position);

            }
        });

        for (int i = 0; i < addCityList.size(); i++){
            if (addCityList.get(i).getCityId().equals(mDataCity.get(position).getCityId())){
                
            }
        }

        Log.e("justCheckId", addCityList.get(0).getCityId() + "--");
    }

    @Override
    public int getItemCount() {
        return mDataCity.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView cityName;
        CheckBox checkBoxAddCity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.cityName);
            checkBoxAddCity = itemView.findViewById(R.id.checkBoxAddCity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

}
