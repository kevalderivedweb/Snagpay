package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CategoryDetailsModel;
import com.example.snagpay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMyCart extends RecyclerView.Adapter<AdapterMyCart.Viewholder> {

    private final OnItemClickListener mListener;
    private Context mContext;
    private ArrayList<CategoryDetailsModel> categoryDetailsModels;
    private int items = 0;
    private int quantity = 1;

    public AdapterMyCart( Context mContext, ArrayList<CategoryDetailsModel> categoryDetailsModels, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mListener = listener;
        this.categoryDetailsModels = categoryDetailsModels;
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

        holder.txtCountProducts.setText(categoryDetailsModels.get(position).getQuantity());

        quantity = Integer.parseInt(categoryDetailsModels.get(position).getQuantity());

        items = Integer.parseInt(holder.txtCountProducts.getText().toString());

        Log.e("ssfdff", categoryDetailsModels.get(position).getShow_deal_option_id() + "--");

        holder.btnMinusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                decrement(holder);
                mListener.onItemClickMinus(position, holder.txtCountProducts.getText().toString());

            }
        });

        holder.btnPlusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increment(holder);
                mListener.onItemClickPlus(position, holder.txtCountProducts.getText().toString());

            }
        });



        holder.cartTitle.setText(categoryDetailsModels.get(position).getTitle());
        holder.cartPrice.setText("$" + categoryDetailsModels.get(position).getSell_price());
        holder.cartBought.setText(categoryDetailsModels.get(position).getBought() + "+ bought");
        holder.txtCountProducts.setText(categoryDetailsModels.get(position).getQuantity());

        Picasso.get().load(categoryDetailsModels.get(position).getDeal_image()).into(holder.cartImage);

        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemDelete(categoryDetailsModels.get(position).getDeal_id(), position);

            }
        });

        holder.saveFromLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickSaveLater(categoryDetailsModels.get(position).getDeal_id());
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryDetailsModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txtCountProducts, cartTitle, cartBought, cartPrice;
        ImageView btnMinusProduct, btnPlusProduct, cartImage;
        Button deleteCartItem, saveFromLater;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtCountProducts = itemView.findViewById(R.id.txtCountProducts);
            btnMinusProduct = itemView.findViewById(R.id.btnMinusProduct);
            btnPlusProduct = itemView.findViewById(R.id.btnPlusProduct);

            cartTitle = itemView.findViewById(R.id.cartTitle);
            cartBought = itemView.findViewById(R.id.cartBought);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartImage = itemView.findViewById(R.id.cartImage);
            deleteCartItem = itemView.findViewById(R.id.deleteCartItem);
            saveFromLater = itemView.findViewById(R.id.saveFromLater);

        }
    }

    public interface OnItemClickListener {
        void onItemClickPlus(int position, String quantity);
        void onItemClickMinus(int position, String quantity);
        void onItemDelete(String s, int pos);
        void onItemClickSaveLater(String dealId);
    }

    public void increment(Viewholder myViewHolder) {
        quantity = Integer.parseInt(myViewHolder.txtCountProducts.getText().toString());
        quantity++;
        myViewHolder.txtCountProducts.setText(String.valueOf(quantity));

    }

    public void decrement(Viewholder myViewHolder) {
        if(!myViewHolder.txtCountProducts.getText().toString().equals("1")){
            quantity = Integer.parseInt(myViewHolder.txtCountProducts.getText().toString());
            quantity--;
            myViewHolder.txtCountProducts.setText(String.valueOf(quantity));
        }

    }
}
