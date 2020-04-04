package com.example.airqualitycontrolapp;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MarkerDetailsFragment extends Fragment {

    private List<Sensor> sensorList;

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
            try {
                JSONArray jsonArray = new JSONArray(bundle.getString("Data"));
                sensorList = JSONParser.parseSensorsJsonArray(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String sensorData = "";

        for(int i = 0; i < sensorList.size(); i++) {
            Sensor sensor = sensorList.get(i);
            Parameter parameter = sensor.getParameter();

            loadMeasurementData("http://api.gios.gov.pl/pjp-api/rest/data/getData/" + sensor.getId());

            List<Measurement> measurementList = JSONParser.parseMeasurementsJsonArray(jsonArray);

            sensorData += parameter.getParamName() + ": ";

            for(int j = 0; j < measurementList.size(); j++) {
                Measurement measurement = measurementList.get(j);
                List<Value> values =  measurement.getValues();
                for(int k = 0; k < values.size(); k++) {
                    Value value = values.get(k);
                    sensorData += value.getValue() + " " + value.getDate() + "\n";
                }

            }
            sensorData += " \n ";

        }

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


    private void loadMeasurementData(final String url) {
        try {
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    try {
                        response = GIOSDataLoader.GET(client, url);
                        //Parse the response string here
                        Log.d("Response", response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }

                @Override
                protected void onPostExecute(String result) {
                    try {
                        jsonArray = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
