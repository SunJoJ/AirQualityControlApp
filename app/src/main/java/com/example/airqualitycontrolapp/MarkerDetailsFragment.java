package com.example.airqualitycontrolapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonArray;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MarkerDetailsFragment extends Fragment {


    private QualityIndex qualityIndex;
    private String address;
    private String stationId;
    private ArrayList<Sensor> sensorArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_marker_details, container, false);

        qualityIndex = (QualityIndex) getArguments().getSerializable("Index");
        address = getArguments().getString("Address");
        stationId = getArguments().getString("StationId");


        Map<Integer, List<Measurement>> mapOfMeasurements = new HashMap<>();

        final String index = qualityIndex.getIndexLevel().getIndexLevelName();

        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
        Call<ArrayList<Sensor>> call = service.getSensorsByStationId(Integer.valueOf(stationId));
        call.enqueue(new Callback<ArrayList<Sensor>>() {
            @Override
            public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                sensorArrayList = response.body();
                Log.d("resp", response.body().toString());
            }

            @Override
            public void onFailure(Call<ArrayList<Sensor>> call, Throwable t) {
                Log.d("resp", t.getMessage());
            }
        });


        TextView detailsTextView = rootView.findViewById(R.id.detailsTextView);
        TextView addressTextView = rootView.findViewById(R.id.addressTextView);
        detailsTextView.setText(index);
        switch (index) {
            case "Bardzo dobry":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                break;
            case "Dobry":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_green);
                break;
            case "Umiarkowany":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_yellow);
                break;
            case "Dostateczny":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_orange);
                break;
            case "Zły":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_red);
                break;
            case "Bardzo zły":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                break;
            case "Brak indeksu":
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
            default:
                detailsTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }
        addressTextView.setText(address);

        Button chooseButton = rootView.findViewById(R.id.chooseButton);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });


        RelativeLayout relativeLayout = rootView.findViewById(R.id.markerDetailsFragmentLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSensorFragment dataSensorFragment = new DataSensorFragment();
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("sensorsList", sensorArrayList);
                bundle.putString("Address", address);
                bundle.putSerializable("Index", qualityIndex);
                bundle.putString("StationId", stationId);

                dataSensorFragment.setArguments(bundle);
                trans.add(R.id.fragment_container, dataSensorFragment, "dataSensorFragment");
                trans.addToBackStack("dataSensorFragment");
                trans.commit();
            }
        });


        return rootView;
    }



}
