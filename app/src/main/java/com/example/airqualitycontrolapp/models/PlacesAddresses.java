package com.example.airqualitycontrolapp.models;

import java.io.Serializable;

public class PlacesAddresses implements Serializable {

    private String placeType;
    private String address;

    public PlacesAddresses(String placeType, String address) {
        this.placeType = placeType;
        this.address = address;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
