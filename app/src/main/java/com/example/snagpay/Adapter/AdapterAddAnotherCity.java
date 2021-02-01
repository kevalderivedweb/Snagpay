package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterAddAnotherCity extends RecyclerView.Adapter<AdapterAddAnotherCity.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private String[]cityName;

    public AdapterAddAnotherCity(Context mContext, String[] cityName, AdapterAddAnotherCity.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.cityName = cityName;
        this.listener = onItemClickListener;
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
        holder.cityName.setText(cityName[position]);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityName.length;
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
