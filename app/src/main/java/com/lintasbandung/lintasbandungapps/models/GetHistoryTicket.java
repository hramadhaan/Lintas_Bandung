package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("paid_at")
    @Expose
    private String paidAt;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;

    public GetHistoryTicket(String id, String jumlahTiket, String harga, String orderId, String status, String keberangkatan, String tujuan, String paidAt, String payment_type) {
        this.id = id;
        this.jumlahTiket = jumlahTiket;
        this.harga = harga;
        this.orderId = orderId;
        this.status = status;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.paidAt = paidAt;
        this.payment_type = payment_type;
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

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
