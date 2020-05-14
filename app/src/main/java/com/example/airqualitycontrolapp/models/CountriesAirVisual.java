package com.example.airqualitycontrolapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CountriesAirVisual implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<Country> countries;

    public CountriesAirVisual(String status, ArrayList<Country> countries) {
        this.status = status;
        this.countries = countries;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
