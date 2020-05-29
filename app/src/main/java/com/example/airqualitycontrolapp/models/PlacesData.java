package com.example.airqualitycontrolapp.models;

import java.io.Serializable;

public class PlacesData implements Serializable {

    private String title;
    private String dataAqi;

    public PlacesData(String title, String dataAqi) {
        this.title = title;
        this.dataAqi = dataAqi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataAqi() {
        return dataAqi;
    }

    public void setDataAqi(String dataAqi) {
        this.dataAqi = dataAqi;
    }

}
