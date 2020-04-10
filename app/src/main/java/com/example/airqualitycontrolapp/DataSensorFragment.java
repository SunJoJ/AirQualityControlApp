package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class DataSensorFragment extends Fragment {

    private ArrayList<Sensor> sensorArrayList;
    private QualityIndex qualityIndex;
    private String stationId;
    private String address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sensor_data, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sensorArrayList = (ArrayList<Sensor>) getArguments().getSerializable("sensorsList");
        qualityIndex = (QualityIndex) getArguments().getSerializable("Index");
        address = getArguments().getString("Address");
        stationId = getArguments().getString("StationId");

        ListView listView = rootView.findViewById(R.id.listOfPollutionComponents);
        List<String> params = new ArrayList<>();
        for(int i = 0; i < sensorArrayList.size(); i++) {
            params.add(sensorArrayList.get(i).getParameter().getParamName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, params);
       listView.setAdapter(adapter);


        TextView indexTextView = rootView.findViewById(R.id.indexTextView);
        TextView addressTextView = rootView.findViewById(R.id.addressTextView);
        String index = qualityIndex.getIndexLevel().getIndexLevelName();
        indexTextView.setText(index);
        addressTextView.setText(address);

        switch (index) {
            case "Bardzo dobry":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                break;
            case "Dobry":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_green);
                break;
            case "Umiarkowany":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_yellow);
                break;
            case "Dostateczny":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_orange);
                break;
            case "Zły":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_red);
                break;
            case "Bardzo zły":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                break;
            case "Brak indeksu":
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
            default:
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }

        return rootView;
    }


}
