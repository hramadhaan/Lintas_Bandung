package com.lintasbandung.lintasbandungapps.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rute {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_trayek")
    @Expose
    private String namaTrayek;
    @SerializedName("trayek")
    @Expose
    private ArrayList<String> trayek = null;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("harga")
    @Expose
    private String harga;

    public Rute(String id, String namaTrayek, ArrayList<String> trayek, String from, String to, String harga) {
        this.id = id;
        this.namaTrayek = namaTrayek;
        this.trayek = trayek;
        this.from = from;
        this.to = to;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaTrayek() {
        return namaTrayek;
    }

    public void setNamaTrayek(String namaTrayek) {
        this.namaTrayek = namaTrayek;
    }

    public ArrayList<String> getTrayek() {
        return trayek;
    }

    public void setTrayek(ArrayList<String> trayek) {
        this.trayek = trayek;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
