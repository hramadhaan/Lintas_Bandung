package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class From {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_highway")
    @Expose
    private String isHighway;

    public From(String id, String placeName, String latitude, String longitude, String isHighway) {
        this.id = id;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isHighway = isHighway;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIsHighway() {
        return isHighway;
    }

    public void setIsHighway(String isHighway) {
        this.isHighway = isHighway;
    }
}
