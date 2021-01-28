package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterWishlistRecent extends RecyclerView.Adapter<AdapterWishlistRecent.Viewholder> {

    private Context mContext;
    private ArrayList<CategoryDetailsModel> categoryDetailsModelArrayRecent;

    public AdapterWishlistRecent(Context mContext, ArrayList<CategoryDetailsModel> categoryDetailsModelArrayRecent) {
        this.mContext = mContext;
        this.categoryDetailsModelArrayRecent = categoryDetailsModelArrayRecent;
    }

    @NonNull
    @Override
    public AdapterWishlistRecent.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_wishlist_recentviewed, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWishlistRecent.Viewholder holder, int position) {

        Picasso.get().load(categoryDetailsModelArrayRecent.get(position).getDeal_image()).into(holder.imgWishlistRecent);

        holder.titleWishlistRecent.setText(categoryDetailsModelArrayRecent.get(position).getTitle());
        holder.boughtWishlistRecent.setText(categoryDetailsModelArrayRecent.get(position).getBought() + "+ bought");
        holder.priceWishlistRecent.setText("$" + categoryDetailsModelArrayRecent.get(position).getSell_price());

    }

    @Override
    public int getItemCount() {
        return categoryDetailsModelArrayRecent.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView imgWishlistRecent;
        TextView titleWishlistRecent, boughtWishlistRecent, priceWishlistRecent;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imgWishlistRecent = itemView.findViewById(R.id.imgWishlistRecent);
            titleWishlistRecent = itemView.findViewById(R.id.titleWishlistRecent);
            boughtWishlistRecent = itemView.findViewById(R.id.boughtWishlistRecent);
            priceWishlistRecent = itemView.findViewById(R.id.priceWishlistRecent);
        }
    }
}
