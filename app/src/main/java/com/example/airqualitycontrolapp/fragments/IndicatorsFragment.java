package com.example.airqualitycontrolapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.airqualitycontrolapp.ChartPainter;
import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitClientGIOS;
import com.example.airqualitycontrolapp.models.Measurement;
import com.example.airqualitycontrolapp.models.PlacesData;
import com.example.airqualitycontrolapp.models.QualityIndex;
import com.example.airqualitycontrolapp.models.Sensor;
import com.example.airqualitycontrolapp.models.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndicatorsFragment extends Fragment {

    ProgressBar pm10;
    ProgressBar pm25;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_indicators, container, false);

        pm10 = rootView.findViewById(R.id.Pm10ProgressBar);
        pm25 = rootView.findViewById(R.id.Pm25ProgressBar);
        pm10.setSecondaryProgress(70);
        pm25.setSecondaryProgress(70);


        SharedPreferences settings = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);
        String homeId = settings.getString("Zewnątrz", "Nie wybrano");
        if(!homeId.equals("Nie wybrano")) {
            homeId = homeId.split(" ")[0];

            RequestService requestService = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
            Call<ArrayList<Sensor>> serviceSensorsByStationId = requestService.getSensorsByStationId(Integer.valueOf(homeId));
            serviceSensorsByStationId.enqueue(new Callback<ArrayList<Sensor>>() {
                @Override
                public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                    ArrayList<Sensor> sensorArrayList = response.body();
                    List<Measurement> measurementArrayList = new ArrayList<>();

                    RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                    for(int i = 0; i < sensorArrayList.size(); i++) {

                        Call<Measurement> measurementCall = service.getMeasurementsDataBySensorId(sensorArrayList.get(i).getId());
                        if(i == sensorArrayList.size() - 1) {
                            measurementCall.enqueue(new Callback<Measurement>() {
                                @Override
                                public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                    measurementArrayList.add(response.body());

                                    measurementArrayList.stream().filter(o -> o.getKey().equals("PM2.5")).forEach(
                                            o -> {
                                                List<Value> values = o.getValues();
                                                LocalDateTime now = LocalDateTime.now();
                                                List<Boolean> booleans = new ArrayList<>();
                                                for(int i = 0; i < now.getHour(); i++) {
                                                    booleans.add(false);
                                                }
                                                for(int i = 0; i < now.getHour(); i++) {
                                                    booleans.set(i, ChartPainter.isPollutionLevelExceeded("PM2.5", (int) values.get(i).getValue()));
                                                }
                                                int count = 0;
                                                for(int i = 0; i < booleans.size(); i++) {
                                                    if(booleans.get(i))
                                                        count++;
                                                }
                                                setPM25Progress(count*70/10);
                                            }
                                    );


                                }

                                @Override
                                public void onFailure(Call<Measurement> call, Throwable t) {
                                }
                            });
                        } else {
                            measurementCall.enqueue(new Callback<Measurement>() {
                                @Override
                                public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                    measurementArrayList.add(response.body());
                                }

                                @Override
                                public void onFailure(Call<Measurement> call, Throwable t) {
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Sensor>> call, Throwable t) {
                    Log.d("resp", t.getMessage());
                }
            });
        }


        SharedPreferences placeType = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);
        String workId = placeType.getString("Praca", "Nie wybrano");
        if(!workId.equals("Nie wybrano")) {
            workId = workId.split(" ")[0];

            RequestService requestService = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
            Call<ArrayList<Sensor>> serviceSensorsByStationId = requestService.getSensorsByStationId(Integer.valueOf(workId));
            serviceSensorsByStationId.enqueue(new Callback<ArrayList<Sensor>>() {
                @Override
                public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                    ArrayList<Sensor> sensorArrayList = response.body();
                    List<Measurement> measurementArrayList = new ArrayList<>();

                    RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                    for(int i = 0; i < sensorArrayList.size(); i++) {

                        Call<Measurement> measurementCall = service.getMeasurementsDataBySensorId(sensorArrayList.get(i).getId());
                        if(i == sensorArrayList.size() - 1) {
                            measurementCall.enqueue(new Callback<Measurement>() {
                                @Override
                                public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                    measurementArrayList.add(response.body());

                                    measurementArrayList.stream().filter(o -> o.getKey().equals("PM10")).forEach(
                                            o -> {
                                                List<Value> values = o.getValues();
                                                LocalDateTime now = LocalDateTime.now();
                                                List<Boolean> booleans = new ArrayList<>();
                                                for(int i = 0; i < now.getHour(); i++) {
                                                    booleans.add(false);
                                                }
                                                for(int i = 0; i < now.getHour(); i++) {
                                                    booleans.set(i, ChartPainter.isPollutionLevelExceeded("PM10", (int) values.get(i).getValue()));
                                                }
                                                int count = 0;
                                                for(int i = 0; i < booleans.size(); i++) {
                                                    if(booleans.get(i))
                                                        count++;
                                                }
                                                setPm10Progress(count*70/10);
                                            }
                                    );


                                }

                                @Override
                                public void onFailure(Call<Measurement> call, Throwable t) {
                                }
                            });
                        } else {
                            measurementCall.enqueue(new Callback<Measurement>() {
                                @Override
                                public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                    measurementArrayList.add(response.body());
                                }

                                @Override
                                public void onFailure(Call<Measurement> call, Throwable t) {
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Sensor>> call, Throwable t) {
                    Log.d("resp", t.getMessage());
                }
            });
        }




        return rootView;
    }

    private void setPM25Progress(int val) {
        LayerDrawable progressBarDrawable25 = (LayerDrawable) pm25.getProgressDrawable();
        Drawable progressDrawable25 = progressBarDrawable25.getDrawable(2);
        if(val <= 40) {
            progressDrawable25.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.darkGreen), PorterDuff.Mode.SRC_IN);
        } else if(val <= 65) {
            progressDrawable25.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN);
        } else {
            progressDrawable25.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.red), PorterDuff.Mode.SRC_IN);
        }
        pm25.setProgress(val);
    }

    private void setPm10Progress(int val) {
        LayerDrawable progressBarDrawable10 = (LayerDrawable) pm10.getProgressDrawable();
        Drawable progressDrawable10 = progressBarDrawable10.getDrawable(2);
        if(val <= 40) {
            progressDrawable10.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.darkGreen), PorterDuff.Mode.SRC_IN);
        } else if(val <= 65) {
            progressDrawable10.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN);
        } else {
            progressDrawable10.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.red), PorterDuff.Mode.SRC_IN);
        }
        pm10.setProgress(val);
    }

}
