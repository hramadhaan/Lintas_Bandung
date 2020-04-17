package com.lintasbandung.lintasbandungapps.models;

public class DataToast {
    String stringApi;
    String stringMidtrans;

    public DataToast(String stringApi, String stringMidtrans) {
        this.stringApi = stringApi;
        this.stringMidtrans = stringMidtrans;
    }

    public DataToast() {
    }

    public String getStringApi() {
        return stringApi;
    }

    public void setStringApi(String stringApi) {
        this.stringApi = stringApi;
    }

    public String getStringMidtrans() {
        return stringMidtrans;
    }

    public void setStringMidtrans(String stringMidtrans) {
        this.stringMidtrans = stringMidtrans;
    }
}
