package com.example.airqualitycontrolapp;

import java.io.Serializable;

public class RatingListDataModel implements Serializable {

    private String country;
    private String state;
    private String city;
    private Integer aqiIndex;

    public RatingListDataModel(String country, String state, String city, Integer aqiIndex) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.aqiIndex = aqiIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAqiIndex() {
        return aqiIndex;
    }

    public void setAqiIndex(Integer aqiIndex) {
        this.aqiIndex = aqiIndex;
    }
}
