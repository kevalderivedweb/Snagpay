package com.example.snagpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Activity.Activity_OrderDetails;
import com.example.snagpay.Model.OrderModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterOrderCurrent extends RecyclerView.Adapter<AdapterOrderCurrent.Viewholder> {

    private Context mContext;
    private ArrayList<OrderModel> orderModelArrayList;
    private final OnItemClickListener mListener;

    public AdapterOrderCurrent(Context mContext, ArrayList<OrderModel> orderModelArrayList, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.orderModelArrayList = orderModelArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_order_current, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.priceCurrent.setText("$" + orderModelArrayList.get(position).getSell_price());
        holder.boughtCurrent.setText(orderModelArrayList.get(position).getBought() + "+ bought");
        holder.titleCurrent.setText(orderModelArrayList.get(position).getTitle());

        Picasso.get().load(orderModelArrayList.get(position).getDeal_image()).into(holder.imagCurrentOrder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickDetails(orderModelArrayList.get(position).getOrder_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView priceCurrent, boughtCurrent, titleCurrent;
        ImageView imagCurrentOrder;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imagCurrentOrder = itemView.findViewById(R.id.imagCurrentOrder);
            priceCurrent = itemView.findViewById(R.id.priceCurrent);
            boughtCurrent = itemView.findViewById(R.id.boughtCurrent);
            titleCurrent = itemView.findViewById(R.id.titleCurrent);

        }
    }

    public interface OnItemClickListener {
        void onItemClickDetails(String orderId);
    }


}
