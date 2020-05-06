package com.example.airqualitycontrolapp;

import java.io.Serializable;

public class RatingListDataModel implements Serializable {

    private String address;
    private Integer aqiIndex;

    public RatingListDataModel(String address, Integer aqiIndex) {
        this.address = address;
        this.aqiIndex = aqiIndex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAqiIndex() {
        return aqiIndex;
    }

    public void setAqiIndex(Integer aqiIndex) {
        this.aqiIndex = aqiIndex;
    }
}
