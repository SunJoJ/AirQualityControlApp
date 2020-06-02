package com.example.airqualitycontrolapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.ItemClickSupport;
import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.adapters.PlacesAddressesAdapter;
import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitClientGIOS;
import com.example.airqualitycontrolapp.models.PlacesAddresses;
import com.example.airqualitycontrolapp.models.StationGIOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ArrayList<StationGIOS> stationGIOSArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.placesList);

        List<PlacesAddresses> placesAddresses = new ArrayList<>();
        SharedPreferences settings = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);

        String homeAddress = settings.getString("Dom", "Nie wybrano");
        if(!homeAddress.equals("Nie wybrano")) {
            String[] oldArr = homeAddress.split(" ");
            String[] newArray = Arrays.copyOfRange(oldArr, 1, oldArr.length);
            homeAddress = "";
            for (String s: newArray) { homeAddress += s;}
        }
        String workAddress = settings.getString("Praca", "Nie wybrano");
        if(!workAddress.equals("Nie wybrano")) {
            String[] oldArr = workAddress.split(" ");
            String[] newArray = Arrays.copyOfRange(oldArr, 1, oldArr.length);
            workAddress = "";
            for (String s: newArray) { workAddress+= s;}
        }
        String streetAddress = settings.getString("Zewnątrz", "Nie wybrano");
        if(!streetAddress.equals("Nie wybrano")) {
            String[] oldArr = streetAddress.split(" ");
            String[] newArray = Arrays.copyOfRange(oldArr, 1, oldArr.length);
            streetAddress = "";
            for (String s: newArray) { streetAddress+= s;}
        }

        placesAddresses.add(new PlacesAddresses("Dom", homeAddress));
        placesAddresses.add(new PlacesAddresses("Praca", workAddress));
        placesAddresses.add(new PlacesAddresses("Zewnątrz", streetAddress));

        PlacesAddressesAdapter placesAdapter = new PlacesAddressesAdapter(placesAddresses);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String placeType = placesAddresses.get(position).getPlaceType();

                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                Call<ArrayList<StationGIOS>> call = service.getAllStations();

                call.enqueue(new Callback<ArrayList<StationGIOS>>() {
                    @Override
                    public void onResponse(Call<ArrayList<StationGIOS>> call, Response<ArrayList<StationGIOS>> response) {
                        stationGIOSArrayList = response.body();

                        FragmentPlaceSearch fragmentPlaceSearch = new FragmentPlaceSearch();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("listOfStations", stationGIOSArrayList);
                        bundle1.putString("placeType", placeType);
                        fragmentPlaceSearch.setArguments(bundle1);
                        FragmentTransaction trans = getFragmentManager().beginTransaction();

                        trans.replace(R.id.fragment_container, fragmentPlaceSearch, "fragmentPlaceSearch");
                        trans.addToBackStack("fragmentPlaceSearch");
                        trans.commit();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<StationGIOS>> call, Throwable t) {
                        Log.d("Response", t.getLocalizedMessage());
                    }
                });


            }
        });




        return rootView;
    }

}
