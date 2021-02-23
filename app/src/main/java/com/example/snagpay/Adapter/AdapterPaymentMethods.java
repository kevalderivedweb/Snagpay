package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterPaymentMethods extends RecyclerView.Adapter<AdapterPaymentMethods.Viewholder> {

    private final OnItemClickListener mListener;
    private Context mContext;

    public AdapterPaymentMethods(Context mContext, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_payment_methods, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.radioPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       mListener.onItemClickRadio(Integer.parseInt(shippingAddressModelArrayList.get(position).getShipping_address_id()));

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        RadioButton radioPayment;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            radioPayment = itemView.findViewById(R.id.radioPayment);

        }
    }

    public interface OnItemClickListener {

        void onItemClickRadio(int item);
    }
}
