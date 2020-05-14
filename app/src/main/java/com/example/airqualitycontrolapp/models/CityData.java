package com.example.airqualitycontrolapp.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityData implements Serializable {

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    @SerializedName("location")
    private CityLocation location;

    @SerializedName("current")
    private CurrentPollutionData current;

    public CityData(String city, String state, String country, CityLocation location, CurrentPollutionData current) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.location = location;
        this.current = current;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CityLocation getLocation() {
        return location;
    }

    public void setLocation(CityLocation location) {
        this.location = location;
    }

    public CurrentPollutionData getCurrent() {
        return current;
    }

    public void setCurrent(CurrentPollutionData current) {
        this.current = current;
    }
}
