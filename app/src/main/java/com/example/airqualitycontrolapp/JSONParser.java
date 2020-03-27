package com.example.airqualitycontrolapp;

import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static List parseStationJsonArray(JSONArray jsonArray) throws JSONException {

        List<Station> stationList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            Integer id = jsonobject.getInt("id");
            String stationName = jsonobject.getString("stationName");
            double gegrLat = Double.parseDouble(jsonobject.getString("gegrLat"));
            double gegrLon = Double.parseDouble(jsonobject.getString("gegrLon"));
            String addressStreet = jsonobject.getString("addressStreet");

            JSONObject objCity = jsonobject.getJSONObject("city");
            Integer cityId = objCity.getInt("id");
            String cityName = objCity.getString("name");

            JSONObject objCommune = objCity.getJSONObject("commune");
            String communeName = objCommune.getString("communeName");
            String districtName = objCommune.getString("districtName");
            String provinceName = objCommune.getString("provinceName");

            Commune commune = new Commune(communeName, districtName, provinceName);
            City city = new City(cityId, cityName, commune);
            Station station = new Station(id, stationName, gegrLat, gegrLon, city, addressStreet);
            stationList.add(station);

        }
        return stationList;
    }

    public static List parseSensorsJsonArray(JSONArray jsonArray) {
        List<Sensor> sensorList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int stationId = jsonObject.getInt("stationId");
                JSONObject param = jsonObject.getJSONObject("param");
                String paramName = param.getString("paramName");
                String paramFormula = param.getString("paramFormula");
                String paramCode = param.getString("paramCode");
                int idParam = param.getInt("idParam");

                Parameter parameter = new Parameter(paramName, paramFormula, paramCode, idParam);
                Sensor sensor = new Sensor(id, stationId, parameter);

                sensorList.add(sensor);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return  sensorList;
    }

}