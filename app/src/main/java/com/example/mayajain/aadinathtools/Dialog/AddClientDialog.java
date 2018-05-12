package com.example.mayajain.aadinathtools.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayajain.aadinathtools.Activities.MainActivity;
import com.example.mayajain.aadinathtools.DTO.Client;
import com.example.mayajain.aadinathtools.R;


public class AddClientDialog extends DialogFragment {

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String blockCharacterSet = "/\\";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_client, container,
                false);
        getDialog().setTitle("Insert Client");
        Button button = rootView.findViewById(R.id.submit);
        final EditText name = rootView.findViewById(R.id.clientName);
        name.setFilters(new InputFilter[] { filter });
        final EditText gstin = rootView.findViewById(R.id.clientGSTIN);
        final EditText address = rootView.findViewById(R.id.clientAddress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client = new Client();
                client.setGstin(gstin.getText().toString());
                client.setAddress(address.getText().toString());
                client.setClientName(name.getText().toString());
                MainActivity.DataProvider.clients.add(client);
                    MainActivity.DataProvider.SaveData(getContext());
                getDialog().dismiss();
            }
        });
        return rootView;
    }
}
