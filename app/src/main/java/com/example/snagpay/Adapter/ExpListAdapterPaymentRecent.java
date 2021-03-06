package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.snagpay.Model.PaymentRecent;
import com.example.snagpay.R;

import java.util.ArrayList;

import com.example.snagpay.Model.PaymentModel;

public class ExpListAdapterPaymentRecent extends BaseExpandableListAdapter {

    private Context _context;

    private ArrayList<PaymentRecent> paymentModelArrayListl;

    public ExpListAdapterPaymentRecent(Context context, ArrayList<PaymentRecent> listDataHeader) {
        this._context = context;
        this.paymentModelArrayListl = listDataHeader;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return paymentModelArrayListl.get(groupPosition).getDetailPaymentModels().get(childPosititon).getCardType();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_payment_recent, null);
        }

        TextView txtPaidPayment = (TextView) convertView.findViewById(R.id.txtPaidPayment);
        TextView txtOrderIdPayment = (TextView) convertView.findViewById(R.id.txtOrderIdPayment);

        txtPaidPayment.setText(paymentModelArrayListl.get(groupPosition).getDetailPaymentModels().get(childPosition).getCardType());
        txtOrderIdPayment.setText(paymentModelArrayListl.get(groupPosition).getDetailPaymentModels().get(childPosition).getE_wallet_tran_code());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.paymentModelArrayListl.size();
    }

    @Override
    public int getGroupCount() {
        return paymentModelArrayListl.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_payment_recent, null);
        }

        ImageView ivGroupIndicator = convertView.findViewById(R.id.ivGroupIndicator);

        TextView paidPayment = (TextView) convertView.findViewById(R.id.paidPayment);
        TextView card = (TextView) convertView.findViewById(R.id.card);
        TextView datePayment = (TextView) convertView.findViewById(R.id.datePayment);
        TextView pricePayment = (TextView) convertView.findViewById(R.id.pricePayment);

        paidPayment.setText( paymentModelArrayListl.get(groupPosition).getTransaction_title());
        card.setText(paymentModelArrayListl.get(groupPosition).getTransaction_type());
        datePayment.setText(paymentModelArrayListl.get(groupPosition).getDatetime());

        String first = paymentModelArrayListl.get(groupPosition).getWallet_credit().substring(1,2);

        if (first.equals("-")){
            pricePayment.setTextColor(ContextCompat.getColor(_context, R.color.red));
        } else {
            pricePayment.setTextColor(ContextCompat.getColor(_context, R.color.green));
        }
        pricePayment.setText(paymentModelArrayListl.get(groupPosition).getWallet_credit());

        ivGroupIndicator.setSelected(isExpanded);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
