package com.example.airqualitycontrolapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.adapters.PlacesAdapter;
import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitClientGIOS;
import com.example.airqualitycontrolapp.models.PlacesData;
import com.example.airqualitycontrolapp.models.QualityIndex;
import com.example.airqualitycontrolapp.models.StationGIOS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesProfileFragment extends Fragment {

    private ArrayList<StationGIOS> stationGIOSArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_places_profile, container, false);

        Button connectButton = rootView.findViewById(R.id.sensorConnectionButton);
        RecyclerView recyclerView = rootView.findViewById(R.id.placesRecyclerView);

        List<PlacesData> placesDataList = new ArrayList<>();
        PlacesAdapter placesAdapter = new PlacesAdapter(placesDataList);
        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
        Call<ArrayList<StationGIOS>> call = service.getAllStations();

//        SharedPreferences settings = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);
//        String homeAddress = settings.getString("Dom", "Nie wybrano");
//        if(!homeAddress.equals("Nie wybrano")) {
//            homeAddress = homeAddress.split(" ")[0];
//        }
//        String workAddress = settings.getString("Praca", "Nie wybrano");
//        if(!workAddress.equals("Nie wybrano")) {
//            workAddress = workAddress.split(" ")[0];
//        }
//        String streetAddress = settings.getString("Zewnątrz", "Nie wybrano");
//        if(!streetAddress.equals("Nie wybrano")) {
//            streetAddress = streetAddress.split(" ")[0];
//        }


        call.enqueue(new Callback<ArrayList<StationGIOS>>() {
            @Override
            public void onResponse(Call<ArrayList<StationGIOS>> call, Response<ArrayList<StationGIOS>> response) {
                stationGIOSArrayList = response.body();


                SharedPreferences settings = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);
                String homeAddress = settings.getString("Dom", "Nie wybrano");
                if(!homeAddress.equals("Nie wybrano")) {
                    homeAddress = homeAddress.split(" ")[0];
                    String finalHomeAddress = homeAddress;
                    stationGIOSArrayList.stream().filter(o -> o.getId() == Integer.parseInt(finalHomeAddress)).forEach(
                            o -> {
                                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                                Call<QualityIndex> qualityIndexHomeCall = service.getQualityIndexByStationId(Integer.parseInt(finalHomeAddress));

                                qualityIndexHomeCall.enqueue(new Callback<QualityIndex>() {
                                    @Override
                                    public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                                        QualityIndex qualityIndex = response.body();
                                        placesDataList.add(new PlacesData("Dom", qualityIndex.getIndexLevel().getIndexLevelName()));
                                        placesAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<QualityIndex> call, Throwable t) {

                                    }
                                });

                            }
                    );
                } else {
                    placesDataList.add(new PlacesData("Dom", "--"));
                    placesAdapter.notifyDataSetChanged();
                }
                String workAddress = settings.getString("Praca", "Nie wybrano");
                if(!workAddress.equals("Nie wybrano")) {
                    workAddress = workAddress.split(" ")[0];
                    String finalWorkAddress = workAddress;
                    stationGIOSArrayList.stream().filter(o -> o.getId() == Integer.parseInt(finalWorkAddress)).forEach(
                            o -> {
                                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                                Call<QualityIndex> qualityIndexWorkCall = service.getQualityIndexByStationId(Integer.parseInt(finalWorkAddress));
                                qualityIndexWorkCall.enqueue(new Callback<QualityIndex>() {
                                    @Override
                                    public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                                        QualityIndex qualityIndex = response.body();
                                        placesDataList.add(new PlacesData("Praca", qualityIndex.getIndexLevel().getIndexLevelName()));
                                        placesAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<QualityIndex> call, Throwable t) {

                                    }
                                });


                            }
                    );
                } else {
                    placesDataList.add(new PlacesData("Praca", "--"));
                    placesAdapter.notifyDataSetChanged();
                }

                String streetAddress = settings.getString("Zewnątrz", "Nie wybrano");
                if(!streetAddress.equals("Nie wybrano")) {
                    streetAddress = streetAddress.split(" ")[0];
                    String finalStreetAddress = streetAddress;
                    stationGIOSArrayList.stream().filter(o -> o.getId() == Integer.parseInt(finalStreetAddress)).forEach(
                            o -> {
                                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                                Call<QualityIndex> qualityIndexStreetCall = service.getQualityIndexByStationId(Integer.parseInt(finalStreetAddress));
                                qualityIndexStreetCall.enqueue(new Callback<QualityIndex>() {
                                    @Override
                                    public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                                        QualityIndex qualityIndex = response.body();
                                        placesDataList.add(new PlacesData("Zewnątrz", qualityIndex.getIndexLevel().getIndexLevelName()));
                                        placesAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<QualityIndex> call, Throwable t) {

                                    }
                                });

                            }
                    );
                } else {
                    placesDataList.add(new PlacesData("Zewnątrz", "--"));
                    placesAdapter.notifyDataSetChanged();
                }

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(placesAdapter);
                placesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<StationGIOS>> call, Throwable t) {
                Log.d("Response", t.getLocalizedMessage());
            }
        });


        ProgressBar progressBarHome = rootView.findViewById(R.id.homeProgressBar);
        ProgressBar progressBarWork = rootView.findViewById(R.id.workProgressBar);
        ProgressBar progressBarStreet = rootView.findViewById(R.id.streetProgressBar);

        LayerDrawable progressBarDrawableHome = (LayerDrawable) progressBarHome.getProgressDrawable();
        Drawable progressBarDrawableHomeDrawable = progressBarDrawableHome.getDrawable(2);
        progressBarDrawableHomeDrawable.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);

        LayerDrawable progressBarDrawableWork = (LayerDrawable) progressBarWork.getProgressDrawable();
        Drawable progressBarDrawableWorkDrawable = progressBarDrawableWork.getDrawable(2);
        progressBarDrawableWorkDrawable.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);

        LayerDrawable progressBarDrawableStreet = (LayerDrawable) progressBarStreet.getProgressDrawable();
        Drawable progressBarDrawableStreetDrawable = progressBarDrawableStreet.getDrawable(2);
        progressBarDrawableStreetDrawable.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);

        progressBarHome.setProgress(50);
        progressBarWork.setProgress(30);
        progressBarStreet.setProgress(70);


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
