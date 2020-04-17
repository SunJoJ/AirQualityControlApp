package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Weather implements Serializable {

    @SerializedName("ts")
    private String timeStamp;

    @SerializedName("tp")
    private Integer temperature;

    @SerializedName("pr")
    private Integer atmPressure;

    @SerializedName("hu")
    private Integer humidity;

    @SerializedName("ws")
    private double windSpeed;

    @SerializedName("wd")
    private Integer windDirection;

    @SerializedName("ic")
    private String ic;

    public Weather(String timeStamp, Integer temperature, Integer atmPressure, Integer humidity, double windSpeed, Integer windDirection, String ic) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.atmPressure = atmPressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.ic = ic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getAtmPressure() {
        return atmPressure;
    }

    public void setAtmPressure(Integer atmPressure) {
        this.atmPressure = atmPressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }
}
