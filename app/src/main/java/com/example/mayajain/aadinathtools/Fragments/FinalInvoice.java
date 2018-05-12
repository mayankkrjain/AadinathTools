package com.example.mayajain.aadinathtools.Fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayajain.aadinathtools.Activities.InvoiceActivity;
import com.example.mayajain.aadinathtools.DTO.Invoice;
import com.example.mayajain.aadinathtools.DTO.InvoiceItem;
import com.example.mayajain.aadinathtools.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinalInvoice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalInvoice extends Fragment {

    private static FinalInvoice fragment;
    private View rootView;

    public FinalInvoice() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FinalInvoice newInstance() {
        if(fragment == null) {
            fragment = new FinalInvoice();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_final_invoice, container, false);
        Button button = rootView.findViewById(R.id.generateBill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        return rootView;
    }

    private void ShowDialog(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Confirm")
                .setMessage("Generate Bill?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((InvoiceActivity)getActivity()).GenerateBill();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @SuppressLint("DefaultLocale")
    public void UpdateData(){
        Invoice currentInvoice =  ((InvoiceActivity)getActivity()).getCurrentInvoice();
        EditText total = rootView.findViewById(R.id.total);
        EditText sgst = rootView.findViewById(R.id.sgst);
        EditText cgst = rootView.findViewById(R.id.cgst);
        EditText igst = rootView.findViewById(R.id.igst);

        EditText roundOff = rootView.findViewById(R.id.roundOff);
        EditText grandTotal = rootView.findViewById(R.id.grandTotal);
        ArrayList<InvoiceItem> itemArrayList =((InvoiceActivity)getActivity()).getInvoiceItems();
        String foo;
        double totalVal = 0;
        for(int i=0; i< itemArrayList.size(); i++){
            totalVal += itemArrayList.get(i).getTotal();
        }
        foo = String.format("%.2f", totalVal);
        total.setText(foo);
        currentInvoice.setTotal(foo);

        double taxVal = 0.0;

        if(currentInvoice.isInterState()){
            cgst.setVisibility(View.GONE);
            sgst.setVisibility(View.GONE);
            igst.setVisibility(View.VISIBLE);
            double taxRate = Double.parseDouble(currentInvoice.getTaxRate()) / 100;
            double igstVal = taxRate * totalVal;
            foo = String.format("%.2f", igstVal);
            igst.setText(foo);
            currentInvoice.setIgst(foo);
            taxVal = igstVal;

        } else{
            double taxRate = Double.parseDouble(currentInvoice.getTaxRate()) / 100;
            double sgstVal = taxRate * totalVal;
            foo = String.format("%.2f", sgstVal);
            sgst.setText(foo);
            igst.setVisibility(View.GONE);
            currentInvoice.setSgst(foo);
            cgst.setText(foo);
            currentInvoice.setCgst(foo);
            taxVal = 2* sgstVal;
        }



        int grandTotalVal = (int) Math.round(totalVal + taxVal);
        double grandTotalDouble = (double)grandTotalVal;
        foo = String.format("%.2f",grandTotalDouble);
        grandTotal.setText(foo);
        currentInvoice.setGrandTotal(foo);

        double roundOffVal = (grandTotalVal - (totalVal + taxVal));
        if(roundOffVal > 0) {
            foo = "(+)" + String.format("%.2f", roundOffVal);
        } else{
            foo = "(-)" + String.format("%.2f", Math.abs(roundOffVal));
        }
        roundOff.setText(foo);
        currentInvoice.setRoundOff(foo);
    }

}
