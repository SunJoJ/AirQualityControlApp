package com.example.airqualitycontrolapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenMapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference dbRef;
    private List<Sensor> sensors;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private String response;
    private OkHttpClient client;
    private JSONArray jsonArray;
    private List<JSONArray> jsonArrayList;
    private ArrayList<Sensor> sensorArrayList;
    private Map<Integer, ArrayList<Sensor>> stringArrayListMap;
    private Integer id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_map, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        client = new OkHttpClient();
        jsonArrayList = new ArrayList<>();

        stationGIOSArrayList = (ArrayList<StationGIOS>) getArguments().getSerializable("listOfStations");

        stringArrayListMap= new HashMap<>();

       for(int i = 0; i < stationGIOSArrayList.size(); i++) {
            RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
            id = stationGIOSArrayList.get(i).getId();
            Call<ArrayList<Sensor>> call = service.getSensorsByStationId(id);

            call.enqueue(new Callback<ArrayList<Sensor>>() {
                @Override
                public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                    //sensorArrayList = response.body();
                    Log.d("resp", String.valueOf(response.body()));
                    stringArrayListMap.put(id, response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Sensor>> call, Throwable t) {
                    Log.d("resp", t.getMessage());
                }
            });
        }



        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        for(int i = 0; i < stationGIOSArrayList.size(); i++) {
            StationGIOS station = stationGIOSArrayList.get(i);

            LatLng pp = new LatLng(Double.parseDouble(station.getLatitude()), Double.parseDouble(station.getLongitude()));
            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId() + " " + station.getAddressStreet()));
        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                MarkerDetailsFragment markerDetailsFragment = new MarkerDetailsFragment();
                String curId = marker.getSnippet().split(" ")[0];


                ArrayList<Sensor> sensorArrayList1 = stringArrayListMap.get(Integer.parseInt(curId));

                Bundle bundle = new Bundle();
                bundle.putSerializable("Data", sensorArrayList1);

                markerDetailsFragment.setArguments(bundle);
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

                trans.add(R.id.fragment_container, markerDetailsFragment);
                trans.addToBackStack(null);
                trans.commit();


                return false;
            }
        });

    }


}
