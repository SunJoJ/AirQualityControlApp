package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.ChartPainter;
import com.example.airqualitycontrolapp.ItemClickSupport;
import com.example.airqualitycontrolapp.adapters.ParameterGridAdapter;
import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.adapters.ParametersAdapter;
import com.example.airqualitycontrolapp.models.Measurement;
import com.example.airqualitycontrolapp.models.QualityIndex;
import com.example.airqualitycontrolapp.models.Sensor;
import com.example.airqualitycontrolapp.models.Value;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
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

        Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
        if(fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();

        sensorArrayList = (ArrayList<Sensor>) getArguments().getSerializable("sensorsList");
        qualityIndex = (QualityIndex) getArguments().getSerializable("Index");
        address = getArguments().getString("Address");
        stationId = getArguments().getString("StationId");
        measurementArrayList = (ArrayList<Measurement>) getArguments().getSerializable("measurementArrayList");

        TextView indexTextView = rootView.findViewById(R.id.indexTextView);
        TextView addressTextView = rootView.findViewById(R.id.addressTextView);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        RecyclerView gridRecyclerView = rootView.findViewById(R.id.gridRecyclerView);
        String index = qualityIndex.getIndexLevel().getIndexLevelName();
        indexTextView.setText("Indeks jakości - " + index.toLowerCase());
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

        List<String> params = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        ParametersAdapter parametersAdapter = new ParametersAdapter(keys, getContext());
        ParameterGridAdapter parameterGridAdapter = new ParameterGridAdapter(params, getContext());
        for(int i = 0; i < measurementArrayList.size(); i++) {
            List<Value> values = measurementArrayList.get(i).getValues();
            if(values.size() != 0) {
                String lastValue = String.valueOf(values.get(1).getValue());
                params.add(measurementArrayList.get(i).getKey() + ": " + lastValue);
                keys.add(measurementArrayList.get(i).getKey());
                parametersAdapter.notifyDataSetChanged();
                parameterGridAdapter.notifyDataSetChanged();
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gridRecyclerView.setAdapter(parameterGridAdapter);
        parameterGridAdapter.notifyDataSetChanged();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(parametersAdapter);
        parametersAdapter.notifyDataSetChanged();


        BarChart barChart = rootView.findViewById(R.id.barChart);
        List<Value> values = measurementArrayList.get(0).getValues();
        BarData data = ChartPainter.paintChart(values, measurementArrayList.get(0).getKey(), getContext(), barChart);
        barChart.setData(data);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.animateY(2000);


        ItemClickSupport.addTo(gridRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String key = measurementArrayList.get(position).getKey();
                ParameterInfoFragment parameterInfoFragment = new ParameterInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                parameterInfoFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, parameterInfoFragment);
                transaction.addToBackStack("parameterInfoFragment");
                transaction.commit();
            }
        });


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                List<Value> values = measurementArrayList.get(position).getValues();
                BarData data = ChartPainter.paintChart(values, measurementArrayList.get(0).getKey(), getContext(), barChart);
                barChart.setData(data);
                barChart.animateY(2000);
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
