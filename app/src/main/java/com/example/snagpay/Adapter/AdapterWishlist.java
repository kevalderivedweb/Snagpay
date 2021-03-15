package com.example.snagpay.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.Model.RecentViewModel;
import com.example.snagpay.Model.TitleModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterWishlist extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private ArrayList<Object> categoryDetailsModelArrayList;


    public static final int TITLE = 0;
    public static final int RECENT = 1;
    public static final int WISH = 2;

    public AdapterWishlist(Context mContext, ArrayList<Object> categoryDetailsModelArrayList,  OnItemClickListener listener) {
        this.listener = listener;
        this.mContext = mContext;
        this.categoryDetailsModelArrayList = categoryDetailsModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == TITLE){
            View recipeItem = inflater.inflate(R.layout.textview_wishlist, parent, false);
            return new TitleViewHolder(recipeItem);
        } else if (viewType == RECENT) {
            View nativeAdItem2 = inflater.inflate(R.layout.adapter_frag_recentview, parent, false);
            return new RecentViewHolder(nativeAdItem2);
        } else if (viewType == WISH) {
            View nativeAdItem3 = inflater.inflate(R.layout.adapter_frag_wishlist, parent, false);
            return new WishlistVIewHolder(nativeAdItem3);
        } else {
            return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemType = getItemViewType(position);


        if(itemType == TITLE){
            TitleViewHolder viewHolder = (TitleViewHolder) holder;
            TitleModel beanTitle = (TitleModel) categoryDetailsModelArrayList.get(position);

            viewHolder.ttxxt.setText(beanTitle.getTitle());
        }
        else if (itemType == RECENT){

            RecentViewHolder recentViewHolder = (RecentViewHolder) holder;

            RecentViewModel beanRecent = (RecentViewModel) categoryDetailsModelArrayList.get(position);

            Picasso.get().load(beanRecent.getDeal_image()).into(recentViewHolder.imgRecentlist);

            recentViewHolder.titleRecentlist.setText(beanRecent.getTitle());
            recentViewHolder.boughtRecentlist.setText(beanRecent.getBought() + "+ bought");
            recentViewHolder.priceRecentlist.setText("$" + beanRecent.getSell_price());
        }

        else {

            WishlistVIewHolder viewholder = (WishlistVIewHolder) holder;

            CategoryDetailsModel  bean = (CategoryDetailsModel) categoryDetailsModelArrayList.get(position);

            Picasso.get().load(bean.getDeal_image()).into(viewholder.imgWishlist);

            viewholder.titleWishlist.setText(bean.getTitle());
            viewholder.boughtWishlist.setText(bean.getBought() + "+ bought");
            viewholder.priceWishlist.setText("$" + bean.getSell_price());


            if (bean.isSelected()) {
                viewholder.itemView.setBackgroundColor(Color.parseColor("#CCCBCB"));
            } else {
                viewholder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(position);

                }
            });

        }
    }



    @Override
    public int getItemCount() {
        return categoryDetailsModelArrayList.size();
    }

    public class WishlistVIewHolder extends RecyclerView.ViewHolder {

        ImageView imgWishlist;
        TextView titleWishlist, boughtWishlist, priceWishlist;

        public WishlistVIewHolder(@NonNull View itemView) {
            super(itemView);

            imgWishlist = itemView.findViewById(R.id.imgWishlist);
            titleWishlist = itemView.findViewById(R.id.titleWishlist);
            boughtWishlist = itemView.findViewById(R.id.boughtWishlist);
            priceWishlist = itemView.findViewById(R.id.priceWishlist);

        }
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView ttxxt;
        TitleViewHolder(View itemView) {
            super(itemView);

            ttxxt = (TextView) itemView.findViewById(R.id.ttxxt);

        }
    }

    private class RecentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRecentlist;
        TextView titleRecentlist, boughtRecentlist, priceRecentlist;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRecentlist = itemView.findViewById(R.id.imgRecentlist);
            titleRecentlist = itemView.findViewById(R.id.titleRecentlist);
            boughtRecentlist = itemView.findViewById(R.id.boughtRecentlist);
            priceRecentlist = itemView.findViewById(R.id.priceRecentlist);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    public void seleceAll(int i){
        Object item = categoryDetailsModelArrayList.get(i);
        if (item instanceof CategoryDetailsModel) {
            CategoryDetailsModel bean = (CategoryDetailsModel) categoryDetailsModelArrayList.get(i);
            bean.setSelected(true);
            notifyDataSetChanged();
        }

    }
    public void deSelectAll(int i){
        Object item = categoryDetailsModelArrayList.get(i);
        if (item instanceof CategoryDetailsModel) {
            CategoryDetailsModel bean = (CategoryDetailsModel) categoryDetailsModelArrayList.get(i);
            bean.setSelected(false);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemViewType(int position) {
        Object item = categoryDetailsModelArrayList.get(position);
        if (item instanceof TitleModel) {
            return TITLE;
        } else if(item instanceof RecentViewModel){
            return RECENT;
        }else {
            return WISH;
        }
    }


}
