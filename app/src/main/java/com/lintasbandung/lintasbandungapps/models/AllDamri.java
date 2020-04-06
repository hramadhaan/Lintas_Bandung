package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDamri {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_trayek")
    @Expose
    private String namaTrayek;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("harga")
    @Expose
    private String harga;

    public AllDamri(String id, String namaTrayek, String from, String to, String harga) {
        this.id = id;
        this.namaTrayek = namaTrayek;
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
