package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.SubCategoriesModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.Viewholder> {

    private Context context;
    private ArrayList<SubCategoriesModel> adapterSubCategoryArrayList;

    public AdapterSubCategory(Context context, ArrayList<SubCategoriesModel> adapterSubCategoryArrayList) {
        this.context = context;
        this.adapterSubCategoryArrayList = adapterSubCategoryArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_sub_category, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.txtSubCategoryName.setText(adapterSubCategoryArrayList.get(position).getSubCategory_name());

        Log.e("daaataa", adapterSubCategoryArrayList.get(position).getSubCategory_name() + " ");
    }

    @Override
    public int getItemCount() {
        return adapterSubCategoryArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        
        TextView txtSubCategoryName;
        
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtSubCategoryName = itemView.findViewById(R.id.txtSubCategoryName);
        }
    }
}
