package com.example.airqualitycontrolapp;

public class Sensor {

    private int id;
    private int stationId;
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
