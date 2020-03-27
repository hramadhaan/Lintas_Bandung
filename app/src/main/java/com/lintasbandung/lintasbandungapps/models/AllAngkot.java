package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAngkot {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("warna")
    @Expose
    private String warna;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("jarak")
    @Expose
    private String jarak;
    @SerializedName("tarif")
    @Expose
    private String tarif;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("to")
    @Expose
    private To to;

    public AllAngkot(String id, String name, String warna, String kode, String img, String jarak, String tarif, From from, To to) {
        this.id = id;
        this.name = name;
        this.warna = warna;
        this.kode = kode;
        this.img = img;
        this.jarak = jarak;
        this.tarif = tarif;
        this.from = from;
        this.to = to;
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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }
}
