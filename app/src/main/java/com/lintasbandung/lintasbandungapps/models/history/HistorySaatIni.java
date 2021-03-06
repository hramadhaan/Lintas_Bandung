package com.lintasbandung.lintasbandungapps.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lintasbandung.lintasbandungapps.models.GetHistoryTicket;

import java.util.ArrayList;

public class HistorySaatIni {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private ArrayList<GetHistoryTicket> data;

    public HistorySaatIni(String status, ArrayList<GetHistoryTicket> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<GetHistoryTicket> getData() {
        return data;
    }

    public void setData(ArrayList<GetHistoryTicket> data) {
        this.data = data;
    }
}
