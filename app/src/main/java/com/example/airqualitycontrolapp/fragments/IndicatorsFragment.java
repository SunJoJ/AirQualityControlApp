package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.airqualitycontrolapp.R;

public class IndicatorsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_indicators, container, false);

        ProgressBar pm10 = rootView.findViewById(R.id.Pm10ProgressBar);
        ProgressBar pm25 = rootView.findViewById(R.id.Pm25ProgressBar);

        pm10.setProgress(60);
        pm10.setSecondaryProgress(70);

        pm25.setProgress(30);
        pm25.setSecondaryProgress(70);

        return rootView;
    }

}
