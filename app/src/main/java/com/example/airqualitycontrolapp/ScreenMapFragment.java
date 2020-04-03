package com.example.airqualitycontrolapp;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ScreenMapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference dbRef;
    private List<Sensor> sensors;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<Station> stationArrayList;
    private String response;
    private OkHttpClient client;
    private JSONArray jsonArray;
    private List<JSONArray> jsonArrayList;


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

        stationArrayList = (ArrayList<Station>) getArguments().getSerializable("listOfStations");

//        for(int i = 0; i < stationArrayList.size(); i++) {
//            Station station = stationArrayList.get(i);
//            loadSensorsData("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + station.getId());
//
//        }
//        for(int i = 0; i < jsonArrayList.size(); i++) {
//            List<Sensor> sensorList = JSONParser.parseSensorsJsonArray(jsonArrayList.get(i));
//        }


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;


        for(int i = 0; i < stationArrayList.size(); i++) {
            Station station = stationArrayList.get(i);

            LatLng pp = new LatLng(station.getLatitude(), station.getLongitude());
            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId() + " " + station.getAddressStreet()));
        }
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(pp, 8));


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                MarkerDetailsFragment markerDetailsFragment = new MarkerDetailsFragment();
                String id = marker.getSnippet().split(" ")[0];
                loadSensorsData("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + id);

                Bundle bundle = new Bundle();
                bundle.putString("Data", response);
                //List<Sensor> sensorList = JSONParser.parseSensorsJsonArray(jsonArray);

                markerDetailsFragment.setArguments(bundle);
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

                trans.add(R.id.fragment_container, markerDetailsFragment);
                trans.addToBackStack(null);
                trans.commit();


                return false;
            }
        });

    }



    private void loadSensorsData(final String url) {
        try {
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    try {
                        response = GIOSDataLoader.GET(client, url);
                        //Parse the response string here
                        Log.d("Response", response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }

                @Override
                protected void onPostExecute(String result) {
                    try {
                        jsonArray = new JSONArray(response);
                        jsonArrayList.add(new JSONArray(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
