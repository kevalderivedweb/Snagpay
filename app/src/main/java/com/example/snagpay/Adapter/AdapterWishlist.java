package com.example.snagpay.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList;
    private CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();

    public AdapterWishlist(Context mContext, ArrayList<CategoryDetailsModel> categoryDetailsModelArrayList,  OnItemClickListener listener) {
        this.listener = listener;
        this.mContext = mContext;
        this.categoryDetailsModelArrayList = categoryDetailsModelArrayList;
    }

    @NonNull
    @Override
    public AdapterWishlist.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_wishlist, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWishlist.Viewholder holder, int position) {

        Picasso.get().load(categoryDetailsModelArrayList.get(position).getDeal_image()).into(holder.imgWishlist);

        holder.titleWishlist.setText(categoryDetailsModelArrayList.get(position).getTitle());
        holder.boughtWishlist.setText(categoryDetailsModelArrayList.get(position).getBought() + "+ bought");
        holder.priceWishlist.setText("$" + categoryDetailsModelArrayList.get(position).getSell_price());

        Log.e("logoo", categoryDetailsModelArrayList.get(position).getTitle() + "--" + categoryDetailsModelArrayList.get(position).getBought() +
                "--" + categoryDetailsModelArrayList.get(position).getSell_price());

        if (categoryDetailsModelArrayList.get(position).isSelected()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#CCCBCB"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryDetailsModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView imgWishlist;
        TextView titleWishlist, boughtWishlist, priceWishlist;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imgWishlist = itemView.findViewById(R.id.imgWishlist);
            titleWishlist = itemView.findViewById(R.id.titleWishlist);
            boughtWishlist = itemView.findViewById(R.id.boughtWishlist);
            priceWishlist = itemView.findViewById(R.id.priceWishlist);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    public void seleceAll(int i){
        categoryDetailsModelArrayList.get(i).setSelected(true);
        notifyDataSetChanged();
    }
    public void deSelectAll(int i){
        categoryDetailsModelArrayList.get(i).setSelected(false);
        notifyDataSetChanged();
    }
}
