package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snagpay.Model.MonthlyView;
import com.example.snagpay.R;

import java.util.ArrayList;

public class AdapterMonthlyViewPayment extends RecyclerView.Adapter<AdapterMonthlyViewPayment.Viewholder> {

    private Context context;
    private ArrayList<MonthlyView> monthlyViewArrayList;

    public AdapterMonthlyViewPayment(Context context, ArrayList<MonthlyView> monthlyViewArrayList) {
        this.context = context;
        this.monthlyViewArrayList = monthlyViewArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.monthly_view_payment, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.transaction_title.setText(monthlyViewArrayList.get(position).getTransaction_title());
        holder.transaction_type.setText(monthlyViewArrayList.get(position).getTransaction_type());
        holder.balance.setText("Balance :- $" + monthlyViewArrayList.get(position).getBalance());
        holder.e_wallet_tran_code.setText("Order Id :- " + monthlyViewArrayList.get(position).getE_wallet_tran_code());
        holder.wallet_credit.setText("$" + monthlyViewArrayList.get(position).getWallet_credit());

        String first = monthlyViewArrayList.get(position).getWallet_credit().substring(0,1);

        if (first.equals("-")){
            holder.wallet_credit.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.wallet_credit.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return monthlyViewArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView transaction_title, transaction_type, balance, e_wallet_tran_code, wallet_credit;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            transaction_title = itemView.findViewById(R.id.transaction_title);
            transaction_type = itemView.findViewById(R.id.transaction_type);
            balance = itemView.findViewById(R.id.balance);
            e_wallet_tran_code = itemView.findViewById(R.id.e_wallet_tran_code);
            wallet_credit = itemView.findViewById(R.id.wallet_credit);

        }
    }
}
