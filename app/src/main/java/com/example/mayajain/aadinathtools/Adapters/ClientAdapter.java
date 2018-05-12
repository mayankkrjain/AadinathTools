package com.example.mayajain.aadinathtools.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mayajain.aadinathtools.DTO.Client;
import com.example.mayajain.aadinathtools.R;

import java.util.List;


public class ClientAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private final List<Client> mDataSource;

    public ClientAdapter(Context context, List<Client> clients){
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.layout_client, viewGroup, false);
        TextView gstView = rowView.findViewById(R.id.gstin);
        TextView clientView = rowView.findViewById(R.id.clientName);
        Client client = (Client) getItem(i);
        gstView.setText(client.getGstin());
        clientView.setText(client.getClientName());
        return rowView;    }
}
