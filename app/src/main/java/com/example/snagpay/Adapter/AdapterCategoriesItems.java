package com.example.snagpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Activity.Activity_HomeInner;
import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCategoriesItems extends RecyclerView.Adapter<AdapterCategoriesItems.Viewholder> {

    private Context mContext;
    private final OnItemClickListener listener;
    private ArrayList<CategoryModel> categoryModelArrayList;

    public AdapterCategoriesItems(Context mContext, ArrayList<CategoryModel> categoryModelArrayList, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_farg_categories, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Picasso.get()
                .load(categoryModelArrayList.get(position).getCategory_image())
                .into(holder.resGridHomeImage);

        holder.resGridHomeText.setText(categoryModelArrayList.get(position).getCategory_name());
        holder.resGridHomeLinear.setBackgroundColor(Color.parseColor(categoryModelArrayList.get(position).getBackround_color()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        LinearLayout resGridHomeLinear;
        ImageView resGridHomeImage;
        TextView resGridHomeText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            resGridHomeLinear = itemView.findViewById(R.id.resGridHomeLinear);
            resGridHomeImage = itemView.findViewById(R.id.resGridHomeImage);
            resGridHomeText = itemView.findViewById(R.id.resGridHomeText);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}

