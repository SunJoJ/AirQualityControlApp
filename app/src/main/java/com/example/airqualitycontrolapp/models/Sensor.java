package com.example.airqualitycontrolapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sensor implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("stationId")
    private int stationId;
    @SerializedName("param")
    private Parameter parameter;

    public Sensor(int id, int stationId, Parameter parameter) {
        this.id = id;
        this.stationId = stationId;
        this.parameter = parameter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
