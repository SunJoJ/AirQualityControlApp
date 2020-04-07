package com.example.airqualitycontrolapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Parameter implements Serializable {

    @SerializedName("paramName")
    private String paramName;
    @SerializedName("paramFormula")
    private String paramFormula;
    @SerializedName("paramCode")
    private String paramCode;
    @SerializedName("idParam")
    private int idParam;

    public Parameter(String paramName, String paramFormula, String paramCode, int idParam) {
        this.paramName = paramName;
        this.paramFormula = paramFormula;
        this.paramCode = paramCode;
        this.idParam = idParam;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public void setParamFormula(String paramFormula) {
        this.paramFormula = paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public int getIdParam() {
        return idParam;
    }

    public void setIdParam(int idParam) {
        this.idParam = idParam;
    }
}
