package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StatesAirVisual implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<StateAirVisual> states;

    public StatesAirVisual(String status, ArrayList<StateAirVisual> states) {
        this.status = status;
        this.states = states;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<StateAirVisual> getStates() {
        return states;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStates(ArrayList<StateAirVisual> states) {
        this.states = states;
    }


}
