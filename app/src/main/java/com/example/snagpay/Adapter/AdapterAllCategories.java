package com.example.snagpay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.CategoryModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterAllCategories extends RecyclerView.Adapter<AdapterAllCategories.Viewholder> {

    private Context mContext;
    private ArrayList<CategoryModel> allCategoryArrayList;
    private AdapterSubCategory adapterSubCategory;

    public AdapterAllCategories(Context mContext, ArrayList<CategoryModel> allCategoryArrayList) {
        this.mContext = mContext;
        this.allCategoryArrayList = allCategoryArrayList;
    }

    @NonNull
    @Override
    public AdapterAllCategories.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_all_categories, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllCategories.Viewholder holder, int position) {

        holder.headerNameCat.setText(allCategoryArrayList.get(position).getCategory_name());

        holder.resSubAllCategories.setLayoutManager(new LinearLayoutManager(mContext));
        adapterSubCategory = new AdapterSubCategory(mContext, allCategoryArrayList.get(position).getSubCategoriesModelArrayList(),
                allCategoryArrayList.get(position).getCategory_id());

        holder.resSubAllCategories.setAdapter(adapterSubCategory);

        Log.e("sizee", allCategoryArrayList.get(position).getSubCategoriesModelArrayList().size() + " ");
    }

    @Override
    public int getItemCount() {
        return allCategoryArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView headerNameCat;
        RecyclerView resSubAllCategories;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            headerNameCat = itemView.findViewById(R.id.headerNameCat);
            resSubAllCategories = itemView.findViewById(R.id.resSubAllCategories);
        }
    }
}
