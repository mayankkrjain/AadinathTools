package com.example.mayajain.aadinathtools.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mayajain.aadinathtools.DTO.InvoiceItem;
import com.example.mayajain.aadinathtools.R;

import java.util.List;

public class InvoiceItemAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private final List<InvoiceItem> mDataSource;

    public InvoiceItemAdapter(Context context, List<InvoiceItem> clients) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDataSource = clients;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.layout_items_entry, viewGroup, false);
        TextView sNo = rowView.findViewById(R.id.sNo);
        TextView itemDesc = rowView.findViewById(R.id.item_desc);
        TextView hsn = rowView.findViewById(R.id.hsn);
        TextView quantity = rowView.findViewById(R.id.quantity);
        TextView rate = rowView.findViewById(R.id.rate);
        TextView total = rowView.findViewById(R.id.amount);
        TextView pieces = rowView.findViewById(R.id.units);

        if (i == 0) {
            sNo.setTypeface(null, Typeface.BOLD);
            itemDesc.setTypeface(null, Typeface.BOLD);
            hsn.setTypeface(null, Typeface.BOLD);
            quantity.setTypeface(null, Typeface.BOLD);
            rate.setTypeface(null, Typeface.BOLD);
            total.setTypeface(null, Typeface.BOLD);
            sNo.setText(R.string.s_no);
            itemDesc.setText(R.string.goods_desc);
            hsn.setText(R.string.hsn);
            quantity.setText(R.string.quantity);
            rate.setText(R.string.rate);
            total.setText(R.string.amount);
        } else {
            InvoiceItem item = mDataSource.get(i - 1);
            sNo.setTypeface(null, Typeface.NORMAL);
            itemDesc.setTypeface(null, Typeface.NORMAL);
            hsn.setTypeface(null, Typeface.NORMAL);
            quantity.setTypeface(null, Typeface.NORMAL);
            rate.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            rate.setTypeface(null, Typeface.NORMAL);
            total.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            total.setTypeface(null, Typeface.NORMAL);
            pieces.setTypeface(null, Typeface.NORMAL);
            sNo.setText(String.valueOf(i));
            itemDesc.setText(item.getDescription());
            pieces.setText(item.getUnit());
            hsn.setText(String.valueOf(item.getHsn()));
            quantity.setText(String.valueOf(item.getQuantity()));
            rate.setText(String.format("%.2f", item.getRate()));
            total.setText(String.format("%.2f", item.getTotal()));

        }


        return rowView;
    }
}

