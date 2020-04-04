package com.example.airqualitycontrolapp;

public class Value {

    private double value;
    private String date;

    public Value(Double value, String date) {
        this.value = value;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
