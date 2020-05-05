package com.lintasbandung.lintasbandungapps.models.angkot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Angkot {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private DataAngkot data;

    public Angkot(String status, DataAngkot data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataAngkot getData() {
        return data;
    }

    public void setData(DataAngkot data) {
        this.data = data;
    }
}
