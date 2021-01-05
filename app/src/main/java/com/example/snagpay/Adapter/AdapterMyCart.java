package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterMyCart extends RecyclerView.Adapter<AdapterMyCart.Viewholder> {

    private final OnItemClickListener mListener;
    private Context mContext;
    private int items = 0;

    public AdapterMyCart( Context mContext, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_my_cart, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        items = Integer.parseInt(holder.txtItemProducts.getText().toString());

        holder.btnMinusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (items >= 1) {
                    holder.txtItemProducts.setText(String.valueOf(items - 1));
                    items = Integer.parseInt(holder.txtItemProducts.getText().toString());
                    mListener.onItemClickMinus(position, holder.txtItemProducts.getText().toString());
                }

            }
        });

        holder.btnPlusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtItemProducts.setText(String.valueOf(items + 1));
                items = Integer.parseInt(holder.txtItemProducts.getText().toString());
                mListener.onItemClickPlus(position, holder.txtItemProducts.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txtItemProducts;
        ImageView btnMinusProduct, btnPlusProduct;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtItemProducts = itemView.findViewById(R.id.txtItemProducts);
            btnMinusProduct = itemView.findViewById(R.id.btnMinusProduct);
            btnPlusProduct = itemView.findViewById(R.id.btnPlusProduct);
        }
    }

    public interface OnItemClickListener {
        void onItemClickPlus(int item, String s);
        void onItemClickMinus(int item, String s);
    }
}
