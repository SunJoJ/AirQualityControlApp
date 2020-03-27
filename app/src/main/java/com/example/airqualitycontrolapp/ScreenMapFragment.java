package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScreenMapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference dbRef;
    private List<Sensor> sensors;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<Station> stationArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_map, container, false);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        stationArrayList = (ArrayList<Station>) getArguments().getSerializable("listOfStations");
        if(stationArrayList != null) {
            System.out.println("size of array " + stationArrayList.size());
        }


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        this.map = googleMap;

//        for(int i = 0; i < sensors.size(); i++) {
//            LatLng pp = new LatLng(sensors.get(i).getLatitude(), sensors.get(i).getLongitude());
//            map.addMarker(new MarkerOptions().position(pp).title(sensors.get(i).getCountry()+ ", " + sensors.get(i).getCity() ));
//        }

        for(int i = 0; i < stationArrayList.size(); i++) {
            Station station = stationArrayList.get(i);
            LatLng pp = new LatLng(station.getLatitude(), station.getLongitude());
            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_red_circle)));
        }

        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(pp, 8));
    }
}
