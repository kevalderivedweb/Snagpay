package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

public class PressAdapter extends RecyclerView.Adapter<PressAdapter.Viewholder> {

    private final OnItemClickListener listener;
    private Context mContect;
    private int selectedItem = -1;

    public PressAdapter(Context mContect, OnItemClickListener listener) {
        this.mContect = mContect;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContect);
        View view = inflater.inflate(R.layout.adapter_press, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                listener.onItemClick(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
