package com.example.snagpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Activity.Activity_OrderDetails;
import com.example.snagpay.R;

public class AdapterOrderCurrent extends RecyclerView.Adapter<AdapterOrderCurrent.Viewholder> {

    private Context mContext;

    public AdapterOrderCurrent(Context mContext) {
        this.mContext = mContext;
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

        holder.clickAdapterCurrentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Activity_OrderDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        LinearLayout clickAdapterCurrentOrder;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            clickAdapterCurrentOrder = itemView.findViewById(R.id.clickAdapterCurrentOrder);
        }
    }
}
