package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Pollution implements Serializable {

    @SerializedName("ts")
    private String timeStamp;

    @SerializedName("aqius")
    private Integer aqius;

    @SerializedName("mainus")
    private String mainus;

    @SerializedName("aqicn")
    private Integer aqicn;

    @SerializedName("maincn")
    private String maincn;

    public Pollution(String timeStamp, Integer aqius, String mainus, Integer aqicn, String maincn) {
        this.timeStamp = timeStamp;
        this.aqius = aqius;
        this.mainus = mainus;
        this.aqicn = aqicn;
        this.maincn = maincn;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getAqius() {
        return aqius;
    }

    public void setAqius(Integer aqius) {
        this.aqius = aqius;
    }

    public String getMainus() {
        return mainus;
    }

    public void setMainus(String mainus) {
        this.mainus = mainus;
    }

    public Integer getAqicn() {
        return aqicn;
    }

    public void setAqicn(Integer aqicn) {
        this.aqicn = aqicn;
    }

    public String getMaincn() {
        return maincn;
    }

    public void setMaincn(String maincn) {
        this.maincn = maincn;
    }
}
