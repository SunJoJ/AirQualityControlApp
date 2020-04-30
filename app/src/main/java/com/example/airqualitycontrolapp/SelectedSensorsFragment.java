package com.example.airqualitycontrolapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedSensorsFragment extends Fragment {

    private ArrayList<SensorsListDataModel> sensorsListDataModels;
    private static SensorsListAdapter sensorsListAdapter;
    private ListView listView;
    private ArrayList<Sensor> sensorArrayList;
    private ArrayList<Measurement> measurementArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_selected_sensors, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        listView = rootView.findViewById(R.id.listOfSelectedSensors);

        Map<String, String> sensorsMap = new HashMap<>();
        String jsonString = new Gson().toJson(sensorsMap);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SELECTED_SENSORS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains("MAP_OF_SENSORS")) {
            String mapJson = sharedPreferences.getString("MAP_OF_SENSORS", jsonString);
            TypeToken<HashMap<String,String>> token = new TypeToken<HashMap<String, String>>() {};
            sensorsMap = new Gson().fromJson(mapJson, token.getType());
        }

        sensorsListDataModels = new ArrayList<>();

        for (Map.Entry<String, String> entry : sensorsMap.entrySet()) {

            RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
            Call<QualityIndex> call = service.getQualityIndexByStationId(Integer.valueOf(entry.getKey()));
            call.enqueue(new Callback<QualityIndex>() {
                @Override
                public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                    QualityIndex qualityIndex = response.body();
                    SensorsListDataModel sensorsListDataModel = new SensorsListDataModel(qualityIndex, entry.getValue(), entry.getKey());
                    sensorsListDataModels.add(sensorsListDataModel);

                    Log.d("resp", String.valueOf(response.body()));
                    sensorsListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<QualityIndex> call, Throwable t) {

                }
            });
        }





        sensorsListAdapter = new SensorsListAdapter(sensorsListDataModels, getContext());
        listView.setAdapter(sensorsListAdapter);

        return rootView;
    }




}
