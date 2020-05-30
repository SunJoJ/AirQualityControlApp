package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.adapters.PlacesAddressesAdapter;
import com.example.airqualitycontrolapp.models.PlacesAddresses;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.placesList);

        List<PlacesAddresses> placesAddresses = new ArrayList<>();
        placesAddresses.add(new PlacesAddresses("Dom", "address 1"));
        placesAddresses.add(new PlacesAddresses("Praca", "address 2"));
        placesAddresses.add(new PlacesAddresses("Zewnątrz", "address 3"));

        PlacesAddressesAdapter placesAdapter = new PlacesAddressesAdapter(placesAddresses);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();




        return rootView;
    }

}
