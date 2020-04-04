package com.example.airqualitycontrolapp;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class MarkerDetailsFragment extends Fragment {

    private List<Sensor> sensorList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_marker_details, container, false);

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
            sensorData += parameter.getParamName() + " " + parameter.getParamFormula() + " \n";
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


}
