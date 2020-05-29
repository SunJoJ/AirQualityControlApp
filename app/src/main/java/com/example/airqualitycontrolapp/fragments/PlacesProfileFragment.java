package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.adapters.PlacesAdapter;
import com.example.airqualitycontrolapp.models.PlacesData;

import java.util.ArrayList;
import java.util.List;

public class PlacesProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_places_profile, container, false);

        Button connectButton = rootView.findViewById(R.id.sensorConnectionButton);
        RecyclerView recyclerView = rootView.findViewById(R.id.placesRecyclerView);

        List<PlacesData> placesDataList = new ArrayList<>();
        placesDataList.add(new PlacesData("Praca", "50"));
        placesDataList.add(new PlacesData("Dom", "30"));
        placesDataList.add(new PlacesData("Zewnątrz", "60"));
        PlacesAdapter placesAdapter = new PlacesAdapter(placesDataList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();


        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenInfoFragment screenInfoFragment = new ScreenInfoFragment();
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_container, screenInfoFragment);
                trans.addToBackStack(null);
                trans.commit();
            }
        });



        return rootView;
    }

}
