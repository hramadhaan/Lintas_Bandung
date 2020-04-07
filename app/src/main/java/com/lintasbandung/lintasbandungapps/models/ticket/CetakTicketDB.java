package com.lintasbandung.lintasbandungapps.models.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CetakTicketDB {

    @SerializedName("jumlah_tiket")
    @Expose
    private String jumlahTiket;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("tanggal_pemesanan")
    @Expose
    private String tanggalPemesanan;
    @SerializedName("keberangkatan")
    @Expose
    private String keberangkatan;
    @SerializedName("tujuan")
    @Expose
    private String tujuan;

    public CetakTicketDB(String jumlahTiket, String orderId, String paymentType, String tanggalPemesanan, String keberangkatan, String tujuan) {
        this.jumlahTiket = jumlahTiket;
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.tanggalPemesanan = tanggalPemesanan;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
    }

    public String getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(String jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public void setKeberangkatan(String keberangkatan) {
        this.keberangkatan = keberangkatan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
}
