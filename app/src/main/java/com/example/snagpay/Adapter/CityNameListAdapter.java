package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

import java.util.ArrayList;

public class CityNameListAdapter extends RecyclerView.Adapter<CityNameListAdapter.Viewholder> {

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<String> addCityList;

    public CityNameListAdapter(Context context, ArrayList<String> addCityList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.addCityList = addCityList;
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_cityname_list, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.cityNameWishlist.setText(addCityList.get(position));

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
        return addCityList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView cityNameWishlist;
        ImageView removeCity;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            cityNameWishlist = itemView.findViewById(R.id.cityNameWishList);
            removeCity = itemView.findViewById(R.id.removeCity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
