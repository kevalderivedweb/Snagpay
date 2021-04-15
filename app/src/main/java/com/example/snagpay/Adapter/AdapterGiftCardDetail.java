package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.GiftDetailModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterGiftCardDetail extends RecyclerView.Adapter<AdapterGiftCardDetail.Viewholder> {

    private Context context;
    private ArrayList<GiftDetailModel> giftDetailModelArrayList;

    public AdapterGiftCardDetail(Context context, ArrayList<GiftDetailModel> giftDetailModelArrayList) {
        this.context = context;
        this.giftDetailModelArrayList = giftDetailModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_gift_card_detail, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.transCode.setText(giftDetailModelArrayList.get(position).getE_gift_card_tran_code());
        holder.amountTrans.setText(giftDetailModelArrayList.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return giftDetailModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView transCode, amountTrans;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            transCode = itemView.findViewById(R.id.transCode);
            amountTrans = itemView.findViewById(R.id.amountTrans);
        }
    }
}
