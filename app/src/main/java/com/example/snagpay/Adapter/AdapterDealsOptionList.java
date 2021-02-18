package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.DealOptionsListModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterDealsOptionList extends RecyclerView.Adapter<AdapterDealsOptionList.Viewholder> {

    private final OnItemClickListener listener;
    private Context context;
    private CompoundButton lastCheckedRB = null;
    private ArrayList<DealOptionsListModel> dealOptionsListModelArrayList;

    public AdapterDealsOptionList(Context context, ArrayList<DealOptionsListModel> dealOptionsListModelArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listener = onItemClickListener;
        this.dealOptionsListModelArrayList = dealOptionsListModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_deals_pricelist, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.radioListPrice.setOnCheckedChangeListener(ls);
        holder.radioListPrice.setTag(position);

        holder.dealsTitle.setText(dealOptionsListModelArrayList.get(position).getDeal_option_name());
        holder.dealsOptionPrice.setText("$ " + dealOptionsListModelArrayList.get(position).getSell_price());

        holder.radioListPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

        if (position == 0){
            holder.radioListPrice.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return dealOptionsListModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        RadioButton radioListPrice;
        TextView dealsTitle, dealsOptionPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            radioListPrice = itemView.findViewById(R.id.radioListPrice);
            dealsTitle = itemView.findViewById(R.id.dealsTitle);
            dealsOptionPrice = itemView.findViewById(R.id.dealsOptionPrice);

        }
    }

    private CompoundButton.OnCheckedChangeListener ls = (new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            int tag = (int) buttonView.getTag();

            if (lastCheckedRB == null) {
                lastCheckedRB = buttonView;
            } else if (tag != (int) lastCheckedRB.getTag()) {
                lastCheckedRB.setChecked(false);
                lastCheckedRB = buttonView;
            }

        }
    });

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
