package com.example.snagpay.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class AdapterHomeInner extends RecyclerView.Adapter<AdapterHomeInner.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList;

    public AdapterHomeInner(Context mContext, ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList, OnItemClickListener listener) {
        this.listener = listener;
        this.categoryDetailsModelArrayList = categoryDetailsModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_home_inner, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

        Picasso.get()
                .load(categoryDetailsModelArrayList.get(position).getDeal_image())
                .into(holder.imgCatHomeInner);

       /* Picasso.get().load(categoryDetailsModelArrayList.get(position).getDeal_image())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imgCatHomeInner);*/

       /* */

       // Log.e("imagee", categoryDetailsModelArrayList.get(position).getDeal_image() + " ");

        holder.titleHome.setText(categoryDetailsModelArrayList.get(position).getTitle());
        holder.titleHome.setEllipsize(TextUtils.TruncateAt.END);

        holder.cityName.setText(categoryDetailsModelArrayList.get(position).getCity_name());
        holder.totalRating.setText("(" + categoryDetailsModelArrayList.get(position).getTotal_rating() + " Rating)");
        holder.priceRegular.setText("$" +categoryDetailsModelArrayList.get(position).getSell_price());
        holder.itemsBought.setText(categoryDetailsModelArrayList.get(position).getBought() + "+ bought");

        holder.ratingBarInnerHome.setStepSize(0.1f);
        holder.ratingBarInnerHome.setRating(Float.parseFloat(categoryDetailsModelArrayList.get(position).getAvg_rating()));
        holder.ratingBarInnerHome.setIsIndicator(true);

       /* Glide.with(mContext)
                .load(categoryDetailsModelArrayList.get(position).getDeal_image())
                .into(imgCatHomeInner);*/

    }

    @Override
    public int getItemCount() {
        return categoryDetailsModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView imgCatHomeInner;
        TextView titleHome, cityName, totalRating, priceRegular, itemsBought;
        RatingBar ratingBarInnerHome;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imgCatHomeInner = itemView.findViewById(R.id.imgCatHomeInner);
            titleHome = itemView.findViewById(R.id.titleHome);
            cityName = itemView.findViewById(R.id.cityName);
            totalRating = itemView.findViewById(R.id.totalRating);
            priceRegular = itemView.findViewById(R.id.priceRegular);
            itemsBought = itemView.findViewById(R.id.itemsBought);
            ratingBarInnerHome = itemView.findViewById(R.id.ratingBarInnerHome);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
