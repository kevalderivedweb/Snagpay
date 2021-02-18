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
import com.example.snagpay.Model.DealsOrderModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDealsOrder extends RecyclerView.Adapter<AdapterDealsOrder.Viewholder> {

    private Context context;
    private ArrayList<DealsOrderModel> orderModelArrayList;

    public AdapterDealsOrder(Context context, ArrayList<DealsOrderModel> orderModelArrayList) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_deals_order, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Picasso.get().load(orderModelArrayList.get(position).getDeal_image()).into(holder.imageDeal);

        holder.titleDeal.setText(orderModelArrayList.get(position).getTitle());
        holder.priceOrder.setText("$" + orderModelArrayList.get(position).getSell_price());
        holder.orderQuantity.setText(orderModelArrayList.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView titleDeal, priceOrder, orderQuantity;
        ImageView imageDeal;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            titleDeal = itemView.findViewById(R.id.titleDeal);
            priceOrder = itemView.findViewById(R.id.priceOrder);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
            imageDeal = itemView.findViewById(R.id.imageDeal);
        }
    }
}
