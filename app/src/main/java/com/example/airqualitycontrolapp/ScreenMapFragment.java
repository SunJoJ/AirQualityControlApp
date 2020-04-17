package com.example.airqualitycontrolapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenMapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference dbRef;
    private List<Sensor> sensors;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private ArrayList<Sensor> sensorArrayList;
    private ArrayList<QualityIndex> qualityIndexArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_map, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        stationGIOSArrayList = (ArrayList<StationGIOS>) getArguments().getSerializable("listOfStations");

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        for(int i = 0; i < stationGIOSArrayList.size(); i++) {
            StationGIOS station = stationGIOSArrayList.get(i);
            LatLng pp = new LatLng(Double.parseDouble(station.getLatitude()), Double.parseDouble(station.getLongitude()));
            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId()
                    + " " + station.getAddressStreet()
                    + ", " + station.getCity().getName()));

            RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
            Call<QualityIndex> call = service.getQualityIndexByStationId(stationGIOSArrayList.get(i).getId());

//            call.enqueue(new Callback<QualityIndex>() {
//                @Override
//                public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
//                    QualityIndex qualityIndex =  response.body();
//
//                    switch (qualityIndex.getIndexLevel().getIndexLevelName()) {
//                        case "Bardzo dobry":
//                        case "Dobry":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        case "Umiarkowany":
//                        case "Dostateczny":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        case "Zły":
//                        case "Bardzo zły":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_red_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        default:
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<QualityIndex> call, Throwable t) {
//
//                }
//            });


        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                final MarkerDetailsFragment markerDetailsFragment = new MarkerDetailsFragment();
                final String curId = marker.getSnippet().split(" ")[0];
                final String address = marker.getSnippet().substring(marker.getSnippet().indexOf(" ") + 1);

                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                Call<QualityIndex> call = service.getQualityIndexByStationId(Integer.valueOf(curId));

                call.enqueue(new Callback<QualityIndex>() {
                    @Override
                    public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                        QualityIndex qualityIndex = response.body();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Index", qualityIndex);
                        bundle.putString("Address", address);
                        bundle.putString("StationId", curId);

                        markerDetailsFragment.setArguments(bundle);
                        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

                        trans.add(R.id.fragment_container, markerDetailsFragment, "markerDetailsFragment");
                        trans.addToBackStack("markerDetailsFragment");
                        trans.commit();
                    }

                    @Override
                    public void onFailure(Call<QualityIndex> call, Throwable t) {

                    }
                });


                return true;
            }
        });

    }


}
