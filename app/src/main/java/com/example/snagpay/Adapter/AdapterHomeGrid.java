package com.example.snagpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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

public class AdapterHomeGrid extends RecyclerView.Adapter<AdapterHomeGrid.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private ArrayList<CategoryModel> categoryModelArrayList;

    public AdapterHomeGrid(Context mContext, ArrayList<CategoryModel> categoryModelArrayList, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.categoryModelArrayList = categoryModelArrayList;
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_home, null);
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
        Log.e("samplee", categoryModelArrayList.get(0).getCategory_name() + " ds");

      /*  holder.qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.
            }
        });*/
        Picasso.get()
                .load(categoryModelArrayList.get(position).getCategory_image())
                .into(holder.resGridHomeImage);

        holder.resGridHomeText.setText(categoryModelArrayList.get(position).getCategory_name());
        holder.resGridHomeLinear.setBackgroundColor(Color.parseColor(categoryModelArrayList.get(position).getBackround_color()));
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView resGridHomeImage;
        LinearLayout resGridHomeLinear;
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
