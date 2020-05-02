package com.lintasbandung.lintasbandungapps.models.angkot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AngkotScan {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("warna")
    @Expose
    private String warna;
    @SerializedName("trayek")
    @Expose
    private ArrayList<String> trayek = null;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("jarak")
    @Expose
    private String jarak;
    @SerializedName("tarif")
    @Expose
    private String tarif;

    public AngkotScan(String id, String name, String warna, ArrayList<String> trayek, String kode, String from, String to, String jarak, String tarif) {
        this.id = id;
        this.name = name;
        this.warna = warna;
        this.trayek = trayek;
        this.kode = kode;
        this.from = from;
        this.to = to;
        this.jarak = jarak;
        this.tarif = tarif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public ArrayList<String> getTrayek() {
        return trayek;
    }

    public void setTrayek(ArrayList<String> trayek) {
        this.trayek = trayek;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
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

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }
}
