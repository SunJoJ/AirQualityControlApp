package com.example.airqualitycontrolapp;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RequestService {

    @GET("station/findAll")
    Call<ArrayList<StationGIOS>> getAllStations();

    @GET("station/sensors/{stationId}")
    Call<ArrayList<Sensor>> getSensorsByStationId(@Path("stationId") Integer stationId);

    @GET("data/getData/{sensorId}")
    Observable<Measurement> getObsMeasurementsDataBySensorId(@Path("sensorId") Integer sensorId);

    @GET("data/getData/{sensorId}")
    Call<Measurement> getMeasurementsDataBySensorId(@Path("sensorId") Integer sensorId);

    @GET("aqindex/getIndex/{stationId}")
    Call<QualityIndex> getQualityIndexByStationId(@Path("stationId") Integer stationId);

    @GET("nearest_city?key=9d1d487e-78d7-45f2-9133-95c6b4d08844")
    Call<DataAirVisual> getAirVisualNearestCityData(@Query("LATITUDE") String LATITUDE, @Query("LONGITUDE") String LONGITUDE);

    @GET("countries?key=9d1d487e-78d7-45f2-9133-95c6b4d08844")
    Call<CountriesAirVisual> getAirVisualSupportedCountries();

    @GET("states?key=9d1d487e-78d7-45f2-9133-95c6b4d08844")
    Call<StatesAirVisual> getAirVisualSupportedStates(@Query("COUNTRY_NAME") String COUNTRY_NAME);

    @GET
    Call<StatesAirVisual> getAirVisualStates(@Url String url);

    @GET("cities?key=9d1d487e-78d7-45f2-9133-95c6b4d08844")
    Call<CitiesAirVisual> getAirVisualSupportedCities(@Query("STATE_NAME") String STATE_NAME, @Query("COUNTRY_NAME") String COUNTRY_NAME);

    @GET
    Call<CitiesAirVisual> getAirVisualCities(@Url String url);

    @GET("city?key=9d1d487e-78d7-45f2-9133-95c6b4d08844")
    Call<DataAirVisual> getAirVisualCityData(@Query("CITY_NAME") String CITY_NAME, @Query("STATE_NAME") String STATE_NAME, @Query("COUNTRY_NAME") String COUNTRY_NAME);

    @GET
    Call<DataAirVisual> getAirVisualSupportedCityData(@Url String url);
}
