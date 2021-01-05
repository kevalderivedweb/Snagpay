package com.example.snagpay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Activity.Activity_HomeInner;
import com.example.snagpay.R;

public class AdapterCategoriesItems extends RecyclerView.Adapter<AdapterCategoriesItems.Viewholder> {

    Context mContext;

    private int[] imagesResGridHome = {R.drawable.goods, R.drawable.service, R.drawable.travel, R.drawable.food, R.drawable.ticket, R.drawable.advertise};
    private String[] textResGridHome = {"Goods", "Services", "Travel", "Food", "Event Tickets", "Advertising"};
    private String[] colorResGridHome = {"#e71b1c", "#004ce6", "#00a3d8", "#018055", "#671c9d", "#ffaa01"};

    public AdapterCategoriesItems(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_farg_categories, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.resGridHomeImage.setImageResource(imagesResGridHome[position]);
        holder.resGridHomeText.setText(textResGridHome[position]);
        holder.resGridHomeLinear.setBackgroundColor(Color.parseColor(colorResGridHome[position % colorResGridHome.length]));

        holder.qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Activity_HomeInner.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesResGridHome.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        LinearLayout resGridHomeLinear, qq;
        ImageView resGridHomeImage;
        TextView resGridHomeText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            resGridHomeLinear = itemView.findViewById(R.id.resGridHomeLinear);
            resGridHomeImage = itemView.findViewById(R.id.resGridHomeImage);
            resGridHomeText = itemView.findViewById(R.id.resGridHomeText);
            qq = itemView.findViewById(R.id.qq);
        }
    }
}

