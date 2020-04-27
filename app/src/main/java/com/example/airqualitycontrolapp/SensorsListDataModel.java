package com.example.airqualitycontrolapp;

import java.io.Serializable;

public class SensorsListDataModel implements Serializable {

    private QualityIndex qualityIndex;
    private String address;
    private String stationId;

    public SensorsListDataModel(QualityIndex qualityIndex, String address, String stationId) {
        this.qualityIndex = qualityIndex;
        this.address = address;
        this.stationId = stationId;
    }

    public QualityIndex getQualityIndex() {
        return qualityIndex;
    }

    public String getAddress() {
        return address;
    }

    public String getStationId() {
        return stationId;
    }

    public void setQualityIndex(QualityIndex qualityIndex) {
        this.qualityIndex = qualityIndex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
