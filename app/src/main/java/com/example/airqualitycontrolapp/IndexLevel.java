package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class IndexLevel implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("indexLevelName")
    private String indexLevelName;


    public IndexLevel(Integer id, String indexLevelName) {
        this.id = id;
        this.indexLevelName = indexLevelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }

    public void setIndexLevelName(String indexLevelName) {
        this.indexLevelName = indexLevelName;
    }

}
