package com.example.airqualitycontrolapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonArray;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import retrofit2.Retrofit;

public class MarkerDetailsFragment extends Fragment {

    private ArrayList<Sensor> sensorList;

    private String response;
    private OkHttpClient client;
    private JSONArray jsonArray;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_marker_details, container, false);

        client = new OkHttpClient();

        Bundle bundle = this.getArguments();

        if(bundle!=null) {
            sensorList = (ArrayList<Sensor>) bundle.getSerializable("Data");
            Log.d("resp", String.valueOf(sensorList.size()));
        }
        Map<Integer, List<Measurement>> mapOfMeasurements = new HashMap<>();

        String sensorData = "";

//        for(int i = 0; i < sensorList.size(); i++) {
//            Sensor sensor = sensorList.get(i);
//            Parameter parameter = sensor.getParameter();
//
//            List<Measurement> measurementList = mapOfMeasurements.get(sensor.getId());
//
//            sensorData += parameter.getParamName() + ": ";
//
//            for(int j = 0; j < measurementList.size(); j++) {
//                Measurement measurement = measurementList.get(j);
//                List<Value> values =  measurement.getValues();
//                for(int k = 0; k < values.size(); k++) {
//                    Value value = values.get(k);
//                    sensorData += measurement.getKey() + " " + value.getValue() + " " + value.getDate() + "\n";
//                }
//
//            }
//            sensorData += " \n ";
//
//        }

        TextView textView = rootView.findViewById(R.id.detailsTextView);
        textView.setText(sensorData);


        Button button = rootView.findViewById(R.id.closeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });



        return rootView;
    }



}
