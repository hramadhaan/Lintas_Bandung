package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lintasbandung.lintasbandungapps.models.history.Rute;

public class GetHistoryTicket {
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("keberangkatan")
    @Expose
    private String keberangkatan;
    @SerializedName("tujuan")
    @Expose
    private String tujuan;
    @SerializedName("tanggal_pemesanan")
    @Expose
    private String tanggal_pemesanan;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("rute")
    @Expose
    private Rute rute;
    @SerializedName("type")
    @Expose
    private String type;

    public GetHistoryTicket(String id, String jumlahTiket, String harga, String orderId, String status, String keberangkatan, String tujuan, String tanggal_pemesanan, String payment_type, Rute rute, String type) {
        this.id = id;
        this.jumlahTiket = jumlahTiket;
        this.harga = harga;
        this.orderId = orderId;
        this.status = status;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.tanggal_pemesanan = tanggal_pemesanan;
        this.payment_type = payment_type;
        this.rute = rute;
        this.type = type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTanggal_pemesanan() {
        return tanggal_pemesanan;
    }

    public void setTanggal_pemesanan(String tanggal_pemesanan) {
        this.tanggal_pemesanan = tanggal_pemesanan;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public Rute getRute() {
        return rute;
    }

    public void setRute(Rute rute) {
        this.rute = rute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
