package com.example.mayajain.aadinathtools.Dialog;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mayajain.aadinathtools.Activities.InvoiceActivity;
import com.example.mayajain.aadinathtools.DTO.InvoiceItem;
import com.example.mayajain.aadinathtools.R;


public class AddItemDialog extends DialogFragment {
    private OnItemAddedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (OnItemAddedListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement Callback interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_add_item, container,
                false);
        getDialog().setTitle("Insert Item");
        Button button = rootView.findViewById(R.id.submit);
        final TextInputEditText name = rootView.findViewById(R.id.itemName);
        final TextInputEditText quantity =  rootView.findViewById(R.id.quantity);
        final TextInputEditText total =  rootView.findViewById(R.id.total);
        final TextInputEditText rate =  rootView.findViewById(R.id.rate);

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(quantity.getText()) && !TextUtils.isEmpty(rate.getText())){
                    double rateVal = Double.parseDouble(rate.getText().toString());
                    int quantityVal = Integer.parseInt(quantity.getText().toString());
                    total.setText(String.valueOf(rateVal * quantityVal));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(quantity.getText()) && !TextUtils.isEmpty(rate.getText())){
                    double rateVal = Double.parseDouble(rate.getText().toString());
                    int quantityVal = Integer.parseInt(quantity.getText().toString());
                    String foo = String.format("%.2f", (rateVal * quantityVal));
                    total.setText(foo);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        final TextInputEditText units =  rootView.findViewById(R.id.units);

        final TextInputEditText hsn = rootView.findViewById(R.id.hsn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(hsn.getText()) || TextUtils.isEmpty(name.getText())
                || TextUtils.isEmpty(rate.getText()) || TextUtils.isEmpty(quantity.getText()))
                {
                    Snackbar.make(rootView, "Fill all fields", Snackbar.LENGTH_LONG).show();
                    return;
                }

                InvoiceItem item = new InvoiceItem();
                item.setHsn(Integer.parseInt(hsn.getText().toString()));
                item.setDescription(name.getText().toString());
                item.setUnit(units.getText().toString());
                item.setRate(Double.parseDouble(rate.getText().toString()));
                item.setQuantity(Integer.parseInt(quantity.getText().toString()));
                item.setTotal( Double.parseDouble(total.getText().toString()));
                ((InvoiceActivity)getActivity()).getInvoiceItems().add(item);
                mListener.OnItemAdded();
                getDialog().dismiss();
            }
        });
        return rootView;
    }

    public interface OnItemAddedListener {
        void OnItemAdded();
    }
}