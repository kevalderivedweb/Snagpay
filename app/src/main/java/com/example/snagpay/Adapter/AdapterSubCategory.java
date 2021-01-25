package com.example.snagpay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Activity.Activity_HomeInner;
import com.example.snagpay.Model.SubCategoriesModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.Viewholder> {

    private Context context;
    private ArrayList<SubCategoriesModel> adapterSubCategoryArrayList;
    private String mainCategoryId;

    public AdapterSubCategory(Context context, ArrayList<SubCategoriesModel> adapterSubCategoryArrayList, String mainCategoryId) {
        this.context = context;
        this.adapterSubCategoryArrayList = adapterSubCategoryArrayList;
        this.mainCategoryId = mainCategoryId;
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

        String subCategoryId = adapterSubCategoryArrayList.get(position).getSubCategory_id();
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Activity_HomeInner.class);
                intent.putExtra("category_id", mainCategoryId);
                intent.putExtra("subCategoryId", subCategoryId);
                ((Activity)context).startActivityForResult(intent, 899);
            }
        });

        Log.e("daaataa", subCategoryId + " ");
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
