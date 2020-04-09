package com.example.airqualitycontrolapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonArray;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MarkerDetailsFragment extends Fragment {

    private ArrayList<Sensor> sensorList;

    private String response;
    private OkHttpClient client;
    private JSONArray jsonArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_marker_details, container, false);

        QualityIndex qualityIndex = (QualityIndex) getArguments().getSerializable("Index");
        String address = getArguments().getString("Address");
        Log.d("resp", qualityIndex.toString());

        Map<Integer, List<Measurement>> mapOfMeasurements = new HashMap<>();

        String index = qualityIndex.getIndexLevel().getIndexLevelName();

        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
        //Call<ArrayList<Measurement>> call = service.getMeasurementsDataBySensorId();


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


        Button button = rootView.findViewById(R.id.chooseButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();

            }
        });

        return rootView;
    }



}
