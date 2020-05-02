package com.lintasbandung.lintasbandungapps.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistorySaatIni {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jumlah_tiket")
    @Expose
    private String jumlahTiket;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("rute")
    @Expose
    private Rute rute;

    public HistorySaatIni(String id, String jumlahTiket, String harga, String orderId, String type, String status, String paymentType, String tanggalPemesanan, String keberangkatan, String tujuan, Rute rute) {
        this.id = id;
        this.jumlahTiket = jumlahTiket;
        this.harga = harga;
        this.orderId = orderId;
        this.type = type;
        this.status = status;
        this.paymentType = paymentType;
        this.tanggalPemesanan = tanggalPemesanan;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.rute = rute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(String jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Rute getRute() {
        return rute;
    }

    public void setRute(Rute rute) {
        this.rute = rute;
    }
}
