package com.example.mayajain.aadinathtools.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mayajain.aadinathtools.DTO.InvoiceItem;
import com.example.mayajain.aadinathtools.R;

import java.util.ArrayList;



public class ItemsAdapter extends BaseAdapter{
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ArrayList<InvoiceItem> mDataSource;

    public ItemsAdapter(Context context, ArrayList<InvoiceItem> invoiceItems){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDataSource = invoiceItems;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.layout_items, viewGroup, false);
        TextView hsnView = rowView.findViewById(R.id.hsn);
        TextView descView = rowView.findViewById(R.id.desc);
        TextView quantityView = rowView.findViewById(R.id.quantity);
        TextView totalView = rowView.findViewById(R.id.total);
        InvoiceItem item = (InvoiceItem) getItem(i);
        hsnView.setText(String.valueOf(item.getHsn()));
        descView.setText(item.getDescription());
        quantityView.setText(item.getQuantity() + " X " + item.getRate());
        totalView.setText(String.valueOf(item.getTotal()));
        return rowView;    }
}
