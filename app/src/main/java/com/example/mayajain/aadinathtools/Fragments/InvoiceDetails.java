package com.example.mayajain.aadinathtools.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import com.example.mayajain.aadinathtools.Activities.InvoiceActivity;
import com.example.mayajain.aadinathtools.Activities.MainActivity;
import com.example.mayajain.aadinathtools.DTO.Client;
import com.example.mayajain.aadinathtools.DTO.Invoice;
import com.example.mayajain.aadinathtools.R;

public class InvoiceDetails extends Fragment {

    private static InvoiceDetails fragment;

    public InvoiceDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InvoiceDetails newInstance() {
        if(fragment == null ) {
            fragment = new InvoiceDetails();
        } else if(fragment.getActivity() == null){
            fragment = new InvoiceDetails();
        }
        return fragment;
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invoice_details, container, false);
        AutoCompleteTextView tv = rootView.findViewById(R.id.clientName);
        final Invoice currentInvoice = ((InvoiceActivity)getActivity()).getCurrentInvoice();
        ArrayAdapter<Client> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, MainActivity.DataProvider.clients);
        tv.setAdapter(adapter);
        tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                currentInvoice.setClient((Client) arg0.getAdapter().getItem(arg2));
                ((InvoiceActivity)getActivity()).setCurrentInvoice(currentInvoice);
            }
        });

        final TextInputEditText invoiceNum = rootView.findViewById(R.id.invoiceNum);
        invoiceNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentInvoice.setInvoiceNumber(invoiceNum.getText().toString());
                ((InvoiceActivity)getActivity()).setCurrentInvoice(currentInvoice);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

         RadioGroup radioGroup = rootView.findViewById(R.id.gstType);
         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 currentInvoice.setInterState(i == R.id.igstRadio);
             }
         });

         final TextInputEditText taxRate = rootView.findViewById(R.id.taxRate);
         taxRate.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 currentInvoice.setTaxRate(taxRate.getText().toString());
                 ((InvoiceActivity)getActivity()).setCurrentInvoice(currentInvoice);
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });

        final TextInputEditText date = rootView.findViewById(R.id.date);
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentInvoice.setDate(date.getText().toString());
                ((InvoiceActivity)getActivity()).setCurrentInvoice(currentInvoice);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootView;
    }
}
