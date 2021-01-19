package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterVendorLanguages extends RecyclerView.Adapter<AdapterVendorLanguages.Viewholder> {

    private Context context;
    private ArrayList<String> languages;

    public AdapterVendorLanguages(Context context, ArrayList<String> languages) {
        this.context = context;
        this.languages = languages;
    }

    @NonNull
    @Override
    public AdapterVendorLanguages.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_vendor_languages, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVendorLanguages.Viewholder holder, int position) {
        holder.txtLan.setText(languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView txtLan;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtLan = itemView.findViewById(R.id.txtLan);
        }
    }
}
