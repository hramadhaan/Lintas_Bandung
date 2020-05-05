package com.lintasbandung.lintasbandungapps.models.angkot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lintasbandung.lintasbandungapps.models.DataUser;

import java.util.ArrayList;

public class DataAngkot {
    @SerializedName("supir")
    @Expose
    private DataUser supir;
    @SerializedName("trayek")
    @Expose
    private AngkotScan trayek;
    @SerializedName("plat")
    @Expose
    private String plat;


    public DataAngkot(DataUser supir, AngkotScan trayek, String plat) {
        this.supir = supir;
        this.trayek = trayek;
        this.plat = plat;
    }

    public DataUser getSupir() {
        return supir;
    }

    public void setSupir(DataUser supir) {
        this.supir = supir;
    }

    public AngkotScan getTrayek() {
        return trayek;
    }

    public void setTrayek(AngkotScan trayek) {
        this.trayek = trayek;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }
}
