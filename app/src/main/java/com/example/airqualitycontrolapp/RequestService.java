package com.example.airqualitycontrolapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestService {

    @GET("station/findAll")
    Call<ArrayList<StationGIOS>> getAllStations();

    @GET("station/sensors/{stationId}")
    Call<ArrayList<Sensor>> getSensorsByStationId(@Path("stationId") Integer stationId);

    @GET("data/getData/{sensorId}")
    Call<ArrayList<Measurement>> getMeasurementsDataBySensorId(@Path("sensorId") Integer sensorId);

    @GET("aqindex/getIndex/{stationId}")
    Call<QualityIndex> getQualityIndexByStationId(@Path("stationId") Integer stationId);


}
