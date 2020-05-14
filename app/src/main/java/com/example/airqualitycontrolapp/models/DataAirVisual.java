package com.example.airqualitycontrolapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataAirVisual implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private CityData cityData;

    public DataAirVisual(String status, CityData cityData) {
        this.status = status;
        this.cityData = cityData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CityData getCityData() {
        return cityData;
    }

    public void setCityData(CityData cityData) {
        this.cityData = cityData;
    }

}
