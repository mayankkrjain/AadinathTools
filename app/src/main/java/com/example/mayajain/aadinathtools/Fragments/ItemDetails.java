package com.example.mayajain.aadinathtools.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mayajain.aadinathtools.Dialog.AddItemDialog;
import com.example.mayajain.aadinathtools.Activities.InvoiceActivity;
import com.example.mayajain.aadinathtools.Adapters.ItemsAdapter;
import com.example.mayajain.aadinathtools.R;


public class ItemDetails extends Fragment implements AddItemDialog.OnItemAddedListener{

    private ItemsAdapter itemsAdapter;
    private static ItemDetails fragment;

    public ItemDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemDetails newInstance() {
        if(fragment == null) {
            fragment = new ItemDetails();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_item_details, container, false);
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        final ListView listview = rootView.findViewById(R.id.itemsList);

        itemsAdapter = new ItemsAdapter(getContext(),
                ((InvoiceActivity)getActivity()).getInvoiceItems());
        listview.setAdapter(itemsAdapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                return showAlert(i);
            }
        });

        return rootView;
    }

    private void ShowDialog(){
        // Create and show the dialog.
        DialogFragment newFragment = new AddItemDialog();
        newFragment.show(ItemDetails.this.getChildFragmentManager(),"Dialog");
    }

    private boolean showAlert(final int i){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((InvoiceActivity)getActivity()).getInvoiceItems().remove(i);
                        itemsAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return false;
    }

    @Override
    public void OnItemAdded() {
        FinalInvoice.newInstance().UpdateData();
        itemsAdapter.notifyDataSetChanged();
    }
}
