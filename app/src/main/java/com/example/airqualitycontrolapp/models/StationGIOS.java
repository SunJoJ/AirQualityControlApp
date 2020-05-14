package com.example.airqualitycontrolapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StationGIOS implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("stationName")
    private  String name;
    @SerializedName("gegrLat")
    private String latitude;
    @SerializedName("gegrLon")
    private String longitude;
    @SerializedName("city")
    private City city;
    @SerializedName("addressStreet")
    private String addressStreet;

    public StationGIOS(int id, String name, String latitude, String longitude, City city, String addressStreet) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.addressStreet = addressStreet;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    @Override
    public String toString() {
        return "StationGIOS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", city=" + city +
                ", addressStreet='" + addressStreet + '\'' +
                '}';
    }


}
