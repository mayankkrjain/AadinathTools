package com.example.mayajain.aadinathtools.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mayajain.aadinathtools.DTO.Client;
import com.example.mayajain.aadinathtools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static class DataProvider {

        static final String NAME = "name";
        static final String ADDRESS = "address";
        static final String GSTIN = "gstin";

        public static final List<Client> clients = new ArrayList<>();

        public static void LoadData(Context context) {
            String json= null;

            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "clients.json");
            try {
                if (file.exists()) {
                    FileInputStream inputStream;
                    FileInputStream fis = new FileInputStream(file);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    StringBuilder jsonBuilder = null;
                    while ((strLine = br.readLine()) != null) {
                        if(jsonBuilder == null){
                            jsonBuilder = new StringBuilder(strLine);
                        } else {
                            jsonBuilder.append(strLine);
                        }
                    }
                    if (jsonBuilder != null) {
                        json = jsonBuilder.toString();
                    }
                    in.close();

                } else {
                    InputStream is = context.getAssets().open("clients.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    //noinspection ResultOfMethodCallIgnored
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");
                }
                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DataProvider.clients.clear();
                if (jsonarray != null) {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        Client client = new Client();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        client.setClientName(jsonobject.getString("name"));
                        client.setAddress(jsonobject.getString("address"));
                        client.setGstin(jsonobject.getString("gstin"));
                        DataProvider.clients.add(client);
                    }
                }
            }catch (Exception ignored){

            }

        }

        public static void SaveData(Context context) {
            JSONArray ClientArray = new JSONArray();
            for (int i = 0; i < clients.size(); i++) {
                try {
                    JSONObject obj = new JSONObject();

                    obj.put(NAME, clients.get(i).getClientName());
                    obj.put(ADDRESS, clients.get(i).getAddress());
                    obj.put(GSTIN, clients.get(i).getGstin());
                    ClientArray.put(obj);
                } catch (org.json.JSONException ex) {

                }
            }

            FileOutputStream outputStream;
            try {
                File path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, "clients.json");
                if(file.exists()){
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
                outputStream = new FileOutputStream(file);
                outputStream.write(ClientArray.toString().getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataProvider.LoadData(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.create_invoice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), InvoiceActivity.class);
                startActivity(intent);
            }
        });

        Button clientButton = findViewById(R.id.manage_client);
        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ClientActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
