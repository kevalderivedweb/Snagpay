package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterFragWishlist extends RecyclerView.Adapter<AdapterFragWishlist.Viewholder> {

    private Context mContext;

    public AdapterFragWishlist(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterFragWishlist.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_wishlist, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFragWishlist.Viewholder holder, int position) {

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
