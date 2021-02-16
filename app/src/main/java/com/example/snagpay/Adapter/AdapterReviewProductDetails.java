package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.ReviewModel;
import com.example.snagpay.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterReviewProductDetails extends RecyclerView.Adapter<AdapterReviewProductDetails.Viewholder> {

    private final ArrayList<ReviewModel> reviewModelArrayList;
    private Context mContext;

    public AdapterReviewProductDetails(Context mContext, ArrayList<ReviewModel> reviewModelArrayList) {
        this.mContext = mContext;
        this.reviewModelArrayList = reviewModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_review_product_details, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.first_letter.setText(reviewModelArrayList.get(position).getFirst_name().substring(0,1).toUpperCase());
        holder.name.setText(reviewModelArrayList.get(position).getFirst_name() + " " +reviewModelArrayList.get(position).getLast_name());
        holder.date.setText(reviewModelArrayList.get(position).getDate());
        holder.desc.setText(reviewModelArrayList.get(position).getReview());

        holder.rating.setStepSize(0.1f);
        holder.rating.setRating(Float.parseFloat(reviewModelArrayList.get(position).getRating()));
        holder.rating.setIsIndicator(true);
    }

    @Override
    public int getItemCount() {
        return reviewModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView first_letter;
        TextView name;
        TextView date;
        RatingBar rating;
        TextView desc;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            first_letter = itemView.findViewById(R.id.first_letter);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
