package com.example.airqualitycontrolapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityAirVisual implements Serializable {

    @SerializedName("city")
    private String cityName;

    public CityAirVisual(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
