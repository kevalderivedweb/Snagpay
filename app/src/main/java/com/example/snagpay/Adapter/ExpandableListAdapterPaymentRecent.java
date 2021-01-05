package com.example.snagpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snagpay.R;

import java.util.ArrayList;

import com.example.snagpay.Model.PaymentModel;

public class ExpandableListAdapterPaymentRecent extends BaseExpandableListAdapter {

    private Context _context;

    ArrayList<PaymentModel> paymentModelArrayListl;

    public ExpandableListAdapterPaymentRecent(Context context, ArrayList<PaymentModel> listDataHeader) {
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
        txtOrderIdPayment.setText(paymentModelArrayListl.get(groupPosition).getDetailPaymentModels().get(childPosition).getOrderId());
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

        paidPayment.setText( paymentModelArrayListl.get(groupPosition).getStatusPayment());
        card.setText(paymentModelArrayListl.get(groupPosition).getCard());
        datePayment.setText(paymentModelArrayListl.get(groupPosition).getDate());
        pricePayment.setText(paymentModelArrayListl.get(groupPosition).getPrice());

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
