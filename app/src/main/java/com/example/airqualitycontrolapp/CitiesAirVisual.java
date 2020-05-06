package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CitiesAirVisual implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<CityAirVisual> cities;

    public CitiesAirVisual(String status, ArrayList<CityAirVisual> cities) {
        this.status = status;
        this.cities = cities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CityAirVisual> getCities() {
        return cities;
    }

    public void setCities(ArrayList<CityAirVisual> cities) {
        this.cities = cities;
    }
}
