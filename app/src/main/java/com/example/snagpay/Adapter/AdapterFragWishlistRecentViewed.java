package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterFragWishlistRecentViewed extends RecyclerView.Adapter<AdapterFragWishlistRecentViewed.Viewholder> {

    private Context mContext;

    public AdapterFragWishlistRecentViewed(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterFragWishlistRecentViewed.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_wishlist_recentviewed, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFragWishlistRecentViewed.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
