package com.example.mayajain.aadinathtools.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayajain.aadinathtools.Adapters.InvoiceItemAdapter;
import com.example.mayajain.aadinathtools.Adapters.ItemsAdapter;
import com.example.mayajain.aadinathtools.DTO.Invoice;
import com.example.mayajain.aadinathtools.DTO.InvoiceItem;
import com.example.mayajain.aadinathtools.Fragments.FinalInvoice;
import com.example.mayajain.aadinathtools.Fragments.InvoiceDetails;
import com.example.mayajain.aadinathtools.Fragments.ItemDetails;
import com.example.mayajain.aadinathtools.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {


    private Invoice currentInvoice;
    private ArrayList<InvoiceItem> invoiceItems;
    private	FragmentPagerAdapter adapterViewPager;
    private ViewPager vpPager;
    private RelativeLayout relativeLayout;
    private InvoiceItemAdapter itemsAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        currentInvoice = new Invoice();
        invoiceItems = new ArrayList<>();
        vpPager = findViewById(R.id.vpPager);
        relativeLayout = findViewById(R.id.generateBillLayout);
        adapterViewPager = new InvoicePagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        // Attach the page change listener inside the activity
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: // Fragment # 0 - This will show FirstFragment
                        InvoiceActivity.this.setTitle("Invoice Details");
                        break;
                    case 1: // Fragment # 0 - This will show FirstFragment different title
                        InvoiceActivity.this.setTitle("Items Details");
                        break;
                    case 2: // Fragment # 1 - This will show SecondFragment
                        InvoiceActivity.this.setTitle("Invoice Details");
                        break;
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        final ListView listview = findViewById(R.id.invoiceList);

        itemsAdapter = new InvoiceItemAdapter(InvoiceActivity.this,
                invoiceItems);
        listview.setAdapter(itemsAdapter);
    }

    @Override
    public void onBackPressed() {
            if(relativeLayout.getVisibility() == View.VISIBLE){
                InvoiceActivity.this.finish();
            }
            else {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(InvoiceActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Discard Invoice")
                        .setMessage("Are you sure you want to cancel?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                InvoiceActivity.this.finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
    }

    public ArrayList<InvoiceItem> getInvoiceItems(){
        return  invoiceItems;
    }

    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    public void setCurrentInvoice(Invoice currentInvoice) {
        this.currentInvoice = currentInvoice;
    }

    public void GenerateBill(){
        adapterViewPager = null;
        vpPager.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        UpdateClientDetails();
        UpdateInvoiceDetails();
        UpdateTotalDetails();
        invoiceItems.add(new InvoiceItem());
        itemsAdapter.notifyDataSetChanged();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GeneratePDF();
            }
        }, 1000);
    }

    @SuppressLint("SetTextI18n")
    private void UpdateInvoiceDetails() {
        TextView invoiceNum = findViewById(R.id.invoiceEntry);
        TextView invoiceDate = findViewById(R.id.dateEntry);
        invoiceDate.setText("Dated : " + currentInvoice.getDate());
        invoiceNum.setText("Invoice No : " +currentInvoice.getInvoiceNumber());
    }

    private void UpdateClientDetails(){
        TextView clientView = findViewById(R.id.clientDetails);
        String clientDetails = currentInvoice.getClient().getClientName() +"\n" +
                currentInvoice.getClient().getAddress();
        if(!TextUtils.isEmpty(currentInvoice.getClient().getGstin())){
            clientDetails  += "\nGSTIN : " + currentInvoice.getClient().getGstin();
        }
        clientView.setText(clientDetails);
    }

    @SuppressLint("SetTextI18n")
    private void UpdateTotalDetails(){
        TextView totalView = findViewById(R.id.totalView);
        TextView roundView = findViewById(R.id.roundView);
        TextView cgstView = findViewById(R.id.cgstView);
        TextView cgstLabel = findViewById(R.id.cgstLabel);
        TextView sgstView = findViewById(R.id.sgstView);
        TextView sgstLabel = findViewById(R.id.sgstLabel);
        TableRow sgstRow = findViewById(R.id.sgstRow);
        TextView grandView = findViewById(R.id.grandView);

        if(currentInvoice.isInterState()){
            cgstLabel.setText("IGST @"+ currentInvoice.getTaxRate() + "%   :   ");
            cgstView.setText(currentInvoice.getIgst());
            sgstRow.setVisibility(View.GONE);
        } else{
            cgstLabel.setText("CGST @"+currentInvoice.getTaxRate() + "%   :   ");
            sgstLabel.setText("SGST @"+currentInvoice.getTaxRate() + "%   :   ");
            cgstView.setText(currentInvoice.getCgst());
            sgstView.setText(currentInvoice.getSgst());
        }
        totalView.setText(currentInvoice.getTotal());
        roundView.setText(currentInvoice.getRoundOff());
        grandView.setText(currentInvoice.getGrandTotal());

    }


    public static class InvoicePagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_ITEMS = 3;

        public InvoicePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return InvoiceDetails.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ItemDetails.newInstance();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FinalInvoice.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    private void setFontForContainer(ViewGroup contentLayout) {
        for (int i=0; i < contentLayout.getChildCount(); i++) {
            View view = contentLayout.getChildAt(i);
            if (view instanceof TextView)
                ((TextView)view).setTextColor(Color.BLACK);
            else if (view instanceof ViewGroup)
                setFontForContainer((ViewGroup) view);
        }
    }
    public void GeneratePDF(){
        setFontForContainer((ViewGroup) findViewById(R.id.rootLayout));
        layoutToImage(findViewById(R.id.rootLayout));
    }

    public void layoutToImage(View relativeLayout) {
        String name = currentInvoice.getClient().getClientName() + "_" + currentInvoice.getInvoiceNumber();
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + name +".jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageToPDF(name);
    }

    private void ImageToPDF(String name){
        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        Image img = null;
        Rectangle pagesize = null;
        try {
            img = Image.getInstance (dirPath+ File.separator + name + ".jpg");
            img.setBorderWidth(0);
            img.setAbsolutePosition(0, 0);
            img.scaleAbsolute(PageSize.A4);
            pagesize = PageSize.A4;
        } catch (BadElementException | IOException e) {
            e.printStackTrace();
        }

        Document document = new Document(pagesize);
        document.setMargins(5,5,15,15);
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document,new FileOutputStream(dirPath+ File.separator + name + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if (writer != null) {
            writer.open();
        }
        document.open();

        try {

            document.add(img);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        if (writer != null) {
            writer.close();
        }
        Toast.makeText(this, "Invoice - " + name + " generated successfully", Toast.LENGTH_SHORT).show();
    }

}
