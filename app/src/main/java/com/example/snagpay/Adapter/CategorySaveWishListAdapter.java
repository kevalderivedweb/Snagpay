package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CityModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class CategorySaveWishListAdapter extends RecyclerView.Adapter<CategorySaveWishListAdapter.Viewholder> {

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<CityModel> categoryArrayList;

    public CategorySaveWishListAdapter(Context context, ArrayList<CityModel> categoryArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_save_category_wishlist, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.categoryWishListTxt.setText(categoryArrayList.get(position).getCityname());

        holder.deleteCategoryWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView categoryWishListTxt;
        ImageView deleteCategoryWishList;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            categoryWishListTxt = itemView.findViewById(R.id.categoryWishListTxt);
            deleteCategoryWishList = itemView.findViewById(R.id.deleteCategoryWishList);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
