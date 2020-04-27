package com.example.airqualitycontrolapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkerDetailsFragment extends Fragment {


    private QualityIndex qualityIndex;
    private String address;
    private String stationId;
    private ArrayList<Sensor> sensorArrayList;
    private ArrayList<Measurement> measurementArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_marker_details, container, false);

        qualityIndex = (QualityIndex) getArguments().getSerializable("Index");
        address = getArguments().getString("Address");
        stationId = getArguments().getString("StationId");


        final String index = qualityIndex.getIndexLevel().getIndexLevelName();

        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
        Call<ArrayList<Sensor>> call = service.getSensorsByStationId(Integer.valueOf(stationId));
        call.enqueue(new Callback<ArrayList<Sensor>>() {
            @Override
            public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                sensorArrayList = response.body();
                Log.d("resp", response.body().toString());
            }

            @Override
            public void onFailure(Call<ArrayList<Sensor>> call, Throwable t) {
                Log.d("resp", t.getMessage());
            }
        });


        TextView detailsTextView = rootView.findViewById(R.id.detailsTextView);
        TextView addressTextView = rootView.findViewById(R.id.addressTextView);
        detailsTextView.setText("Indeks jakości - " + index.toLowerCase());
        switch (index) {
            case "Bardzo dobry":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                break;
            case "Dobry":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_green);
                break;
            case "Umiarkowany":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_yellow);
                break;
            case "Dostateczny":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_orange);
                break;
            case "Zły":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_red);
                break;
            case "Bardzo zły":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                break;
            default:
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }
        addressTextView.setText(address);

        Button chooseButton = rootView.findViewById(R.id.chooseButton);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> sensorsMap = new HashMap<>();
                String jsonString = new Gson().toJson(sensorsMap);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SELECTED_SENSORS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(sharedPreferences.contains("MAP_OF_SENSORS")) {
                    String mapJson = sharedPreferences.getString("MAP_OF_SENSORS", jsonString);

                    TypeToken<HashMap<String,String>> token = new TypeToken<HashMap<String, String>>() {};
                    HashMap<String,String> retrievedMap = new Gson().fromJson(mapJson, token.getType());
                    retrievedMap.put(stationId, address);
                    String json = new Gson().toJson(retrievedMap);
                    editor.putString("MAP_OF_SENSORS", json);

                } else {
                    sensorsMap.put(stationId, address);
                    String json = new Gson().toJson(sensorsMap);
                    editor.putString("MAP_OF_SENSORS", json);
                }
                editor.apply();

            }
        });



        RelativeLayout relativeLayout = rootView.findViewById(R.id.markerDetailsFragmentLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSensorFragment dataSensorFragment = new DataSensorFragment();
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("sensorsList", sensorArrayList);
                bundle.putString("Address", address);
                bundle.putSerializable("Index", qualityIndex);
                bundle.putString("StationId", stationId);

//                RequestService service = RetrofitRxClientGIOS.getRxRetrofitInstance().create(RequestService.class);
//                List<Observable<?>> requests = new ArrayList<>();
//                for(int i = 0; i < sensorArrayList.size(); i++) {
//                    requests.add(service.getObsMeasurementsDataBySensorId(sensorArrayList.get(i).getId()));
//                }
//                final List<Measurement>[] measurementList = new List[]{new ArrayList<>()};
//                Observable.zip(requests, new Function<Object[], Measurement[]>() {
//                    @Override
//                    public Measurement[] apply(Object[] objects) throws Exception {
//                        //Log.d("resp", objects.toString());
//                        Measurement[] measurements = new Measurement[objects.length];
//                        for(int i = 0; i < objects.length; i++) {
//                            measurements[i] = (Measurement) objects[i];
//                        }
//                        return measurements;
//                    }
//                }).subscribe(measurements -> measurementList[0] = Arrays.asList(measurements));
//                Log.d("resp", measurementList[0].toString());

                measurementArrayList = new ArrayList<>();

                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                for(int i = 0; i < sensorArrayList.size(); i++) {
                    Call<Measurement> call = service.getMeasurementsDataBySensorId(sensorArrayList.get(i).getId());

                    if(i == sensorArrayList.size()-1) {
                        call.enqueue(new Callback<Measurement>() {
                            @Override
                            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                measurementArrayList.add(response.body());
                                Log.d("resp", response.body().toString());
                                bundle.putSerializable("measurementArrayList", measurementArrayList);
                                dataSensorFragment.setArguments(bundle);
                                trans.add(R.id.fragment_container, dataSensorFragment, "dataSensorFragment");
                                trans.addToBackStack("dataSensorFragment");
                                trans.commit();
                            }

                            @Override
                            public void onFailure(Call<Measurement> call, Throwable t) {
                            }
                        });
                    } else {
                        call.enqueue(new Callback<Measurement>() {
                            @Override
                            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                measurementArrayList.add(response.body());
                                Log.d("resp", response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<Measurement> call, Throwable t) {
                            }
                        });
                    }
                }



            }
        });


        return rootView;
    }



}
