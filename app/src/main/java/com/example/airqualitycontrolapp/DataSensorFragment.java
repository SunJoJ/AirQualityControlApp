package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.internal.NavigationMenu;

import java.util.ArrayList;
import java.util.List;


public class DataSensorFragment extends Fragment {

    private ArrayList<Sensor> sensorArrayList;
    private QualityIndex qualityIndex;
    private String stationId;
    private String address;
    private NavigationMenu navigationMenu;
    private ArrayList<Measurement> measurementArrayList;

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
        measurementArrayList = (ArrayList<Measurement>) getArguments().getSerializable("measurementArrayList");

        ListView listView = rootView.findViewById(R.id.listOfPollutionComponents);
        List<String> params = new ArrayList<>();
        for(int i = 0; i < measurementArrayList.size(); i++) {
            List<Value> values = measurementArrayList.get(i).getValues();
            String lastValue = String.valueOf(values.get(0).getValue());
            params.add(measurementArrayList.get(i).getKey()+ ": " + lastValue + " µg/m3");
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
            default:
                indexTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }

        MaterialToolbar materialToolbar = rootView.findViewById(R.id.topAppBar);
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.closeButton:
                        Fragment fragment = getFragmentManager().findFragmentByTag("dataSensorFragment");
                        if(fragment != null)
                            getFragmentManager().beginTransaction().remove(fragment).commit();
                        return true;
                }

                return false;
            }
        });



        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }





}
