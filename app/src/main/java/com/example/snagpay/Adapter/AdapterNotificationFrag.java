package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.NotificationModel;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterNotificationFrag extends RecyclerView.Adapter<AdapterNotificationFrag.Viewholder> {

    private Context mContext;
    private ArrayList<NotificationModel> notificationModelArrayList;

    public AdapterNotificationFrag(Context mContext, ArrayList<NotificationModel> notificationModelArrayList) {
        this.mContext = mContext;
        this.notificationModelArrayList = notificationModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_frag_notification, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.dateNotify.setText(notificationModelArrayList.get(position).getDate());
        holder.descripNotify.setText(notificationModelArrayList.get(position).getDescription());
        holder.titleNotify.setText(notificationModelArrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return notificationModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView dateNotify, descripNotify, titleNotify;
        ImageView imgNotifiy;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            dateNotify = itemView.findViewById(R.id.dateNotify);
            descripNotify = itemView.findViewById(R.id.descripNotify);
            titleNotify = itemView.findViewById(R.id.titleNotify);
            imgNotifiy = itemView.findViewById(R.id.imgNotifiy);

        }
    }
}
