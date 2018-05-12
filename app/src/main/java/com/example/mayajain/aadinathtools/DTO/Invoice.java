package com.example.mayajain.aadinathtools.DTO;

import java.util.List;

public class Invoice {
    private String invoiceNumber;
    private String date;
    private Client client;
    private String sgst;
    private String cgst;
    private String igst;
    private String roundOff;
    private String total;
    private String grandTotal;
    private String taxRate = "9.00";
    private  boolean isInterState = false;

    private List<InvoiceItem> itemList;

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getDate() {
        return date;
    }

    public Client getClient() {
        return client;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRoundOff() {
        return roundOff;
    }

    public void setRoundOff(String roundOff) {
        this.roundOff = roundOff;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isInterState() {
        return isInterState;
    }

    public void setInterState(boolean interState) {
        isInterState = interState;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }
}
