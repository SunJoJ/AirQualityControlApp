package com.example.airqualitycontrolapp;

import java.util.List;

public class Measurement {

    private String key;
    private List<Value> values;

    public Measurement(String key, List<Value> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }


}
