package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class QualityIndex implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("stCalcDate")
    private String stCalcDate;

    @SerializedName("stIndexLevel")
    private IndexLevel indexLevel;

    public QualityIndex(Integer id, String stCalcDate, IndexLevel indexLevel) {
        this.id = id;
        this.stCalcDate = stCalcDate;
        this.indexLevel = indexLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStCalcDate() {
        return stCalcDate;
    }

    public void setStCalcDate(String stCalcDate) {
        this.stCalcDate = stCalcDate;
    }

    public IndexLevel getIndexLevel() {
        return indexLevel;
    }

    public void setIndexLevel(IndexLevel indexLevel) {
        this.indexLevel = indexLevel;
    }
}
