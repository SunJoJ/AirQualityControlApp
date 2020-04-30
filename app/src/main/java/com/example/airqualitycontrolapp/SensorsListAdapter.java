package com.example.airqualitycontrolapp;

import android.content.Context;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorsListAdapter extends ArrayAdapter<SensorsListDataModel> {

    private ArrayList<SensorsListDataModel> sensorsListDataModels;
    private ArrayList<Sensor> sensorArrayList;
    private ArrayList<Measurement> measurementArrayList;
    Context mContext;

    public SensorsListAdapter(ArrayList<SensorsListDataModel> data, Context context) {
        super(context, R.layout.fragment_marker_details, data);
        this.sensorsListDataModels = data;
        this.mContext = context;
    }


    private static class ViewHolder {
        TextView addressTextView;
        TextView detailsTextView;
        RelativeLayout markerDetailsFragmentLayout;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SensorsListDataModel sensorsListDataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_marker_details, parent, false);
            viewHolder.addressTextView = convertView.findViewById(R.id.addressTextView);
            viewHolder.detailsTextView = convertView.findViewById(R.id.detailsTextView);
            viewHolder.markerDetailsFragmentLayout = convertView.findViewById(R.id.markerDetailsFragmentLayout);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.addressTextView.setText(sensorsListDataModel.getAddress());
        String index = sensorsListDataModel.getQualityIndex().getIndexLevel().getIndexLevelName();
        viewHolder.detailsTextView.setText("Indeks jakości - " + index.toLowerCase());
        switch (index) {
            case "Bardzo dobry":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                break;
            case "Dobry":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_green);
                break;
            case "Umiarkowany":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_yellow);
                break;
            case "Dostateczny":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_orange);
                break;
            case "Zły":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_red);
                break;
            case "Bardzo zły":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                break;
            default:
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSensorFragment dataSensorFragment = new DataSensorFragment();
                FragmentTransaction trans = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("sensorsList", sensorArrayList);
                bundle.putString("Address", sensorsListDataModel.getAddress());
                bundle.putSerializable("Index", sensorsListDataModel.getQualityIndex());
                bundle.putString("StationId", sensorsListDataModel.getStationId());

                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                Call<ArrayList<Sensor>> call = service.getSensorsByStationId(Integer.valueOf(sensorsListDataModel.getStationId()));
                call.enqueue(new Callback<ArrayList<Sensor>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                        sensorArrayList = response.body();

                        measurementArrayList = new ArrayList<>();

                        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                        for(int i = 0; i < sensorArrayList.size(); i++) {
                            Call<Measurement> callMeasurements = service.getMeasurementsDataBySensorId(sensorArrayList.get(i).getId());

                            if(i == sensorArrayList.size()-1) {
                                callMeasurements.enqueue(new Callback<Measurement>() {
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
                                callMeasurements.enqueue(new Callback<Measurement>() {
                                    @Override
                                    public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                                        measurementArrayList.add(response.body());
                                        Log.d("resp", response.body().toString());
                                    }

                                    @Override
                                    public void onFailure(Call<Measurement> call, Throwable t) {
                                        Log.d("resp", t.getMessage());
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
        });

        return convertView;
    }


}
