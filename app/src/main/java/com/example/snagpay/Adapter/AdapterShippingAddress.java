package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.ShippingAddressModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterShippingAddress extends RecyclerView.Adapter<AdapterShippingAddress.Viewholder> {

    private final OnItemClickListener mListener;
    private Context mContext;
    private ArrayList<ShippingAddressModel> shippingAddressModelArrayList;


    public AdapterShippingAddress(Context mContext, ArrayList<ShippingAddressModel> shippingAddressModelArrayList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.shippingAddressModelArrayList =shippingAddressModelArrayList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_shipping_address, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {



        Log.e("radioCheck", shippingAddressModelArrayList.get(position).getIs_default() + "--");

        holder.shippingAddress.setText(shippingAddressModelArrayList.get(position).getAddress() + ", " + shippingAddressModelArrayList.get(position).getStreet()
        + ", " + shippingAddressModelArrayList.get(position).getCity_name() + ", " + shippingAddressModelArrayList.get(position).getState_name() +
                ", " + shippingAddressModelArrayList.get(position).getCountry_name() + ", " + shippingAddressModelArrayList.get(position).getZip_code());

        holder.shippingPhoneNo.setText(shippingAddressModelArrayList.get(position).getPhone());

        holder.removeShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickRemove(Integer.parseInt(shippingAddressModelArrayList.get(position).getShipping_address_id()));
            }
        });

        holder.editShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickEdit(Integer.parseInt(shippingAddressModelArrayList.get(position).getShipping_address_id()));
            }
        });

        holder.radioMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickRadio(Integer.parseInt(shippingAddressModelArrayList.get(position).getShipping_address_id()));

                notifyDataSetChanged();
            }
        });

        if (shippingAddressModelArrayList.get(position).getIs_default().equals("1")){
            holder.radioMyAddress.setChecked(true);
        } else if (shippingAddressModelArrayList.get(position).getIs_default().equals("0")){
            holder.radioMyAddress.setChecked(false);

        }
    }

    @Override
    public int getItemCount() {
        return shippingAddressModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView shippingAddress, shippingPhoneNo, removeShippingAddress, editShippingAddress;
        RadioButton radioMyAddress;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            shippingAddress = itemView.findViewById(R.id.shippingAddress);
            shippingPhoneNo = itemView.findViewById(R.id.shippingPhoneNo);
            radioMyAddress = itemView.findViewById(R.id.radioMyAddress);
            removeShippingAddress = itemView.findViewById(R.id.removeShippingAddress);
            editShippingAddress = itemView.findViewById(R.id.editShippingAddress);

        }
    }

    public interface OnItemClickListener {
        void onItemClickRemove(int item);
        void onItemClickEdit(int item);
        void onItemClickRadio(int item);
    }
}
