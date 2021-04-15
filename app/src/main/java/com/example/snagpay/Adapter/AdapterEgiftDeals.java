package com.example.snagpay.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.EGiftCardModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterEgiftDeals extends RecyclerView.Adapter<AdapterEgiftDeals.Viewholder> {

    private Context context;
    private ArrayList<EGiftCardModel> eGiftCardModelArrayList;
    private final OnItemClickListener listener;

    public AdapterEgiftDeals(Context context, ArrayList<EGiftCardModel> eGiftCardModelArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.eGiftCardModelArrayList = eGiftCardModelArrayList;
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_egift_deals, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.titleDeals.setText("Title");
        holder.quantityDeals.setText("Quantity: " + eGiftCardModelArrayList.get(position).getQty());
        holder.priceDeals.setText(eGiftCardModelArrayList.get(position).getAmount());

        holder.dealsGiftCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                listener.onChecked(position, isChecked);
            }
        });


        holder.priceDeals.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                listener.onItemTextChange(position, holder.priceDeals.getText().toString());

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(eGiftCardModelArrayList.get(position).getE_gift_card_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return eGiftCardModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView imagDealsGift;
        EditText priceDeals;
        TextView titleDeals, quantityDeals;
        CheckBox dealsGiftCheck;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imagDealsGift = itemView.findViewById(R.id.imagDealsGift);
            titleDeals = itemView.findViewById(R.id.titleDeals);
            quantityDeals = itemView.findViewById(R.id.quantityDeals);
            priceDeals = itemView.findViewById(R.id.priceDeals);
            dealsGiftCheck = itemView.findViewById(R.id.dealsGiftCheck);
        }
    }

    public interface OnItemClickListener {
        void onChecked(int item, boolean isChecked);
        void onItemTextChange(int item, String amount);
        void onItemClick(String giftID);
    }
}
