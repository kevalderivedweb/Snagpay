package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class AdapterGuide extends RecyclerView.Adapter<AdapterGuide.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContext;
    private String[] guideItems;
    private int selectItem = -1;

    public AdapterGuide(Context mContext, String[] guideItems, OnItemClickListener listener) {
        this.mContext = mContext;
        this.guideItems = guideItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_guide, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.guideText.setText(guideItems[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem = position;
                listener.onItemClick(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return guideItems.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView guideText;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            guideText = itemView.findViewById(R.id.guideText);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
