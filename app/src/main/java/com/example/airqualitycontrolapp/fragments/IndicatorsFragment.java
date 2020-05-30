package com.example.airqualitycontrolapp.fragments;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
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
        pm10.setSecondaryProgress(70);
        pm25.setSecondaryProgress(70);

        LayerDrawable progressBarDrawable10 = (LayerDrawable) pm10.getProgressDrawable();
        Drawable progressDrawable10 = progressBarDrawable10.getDrawable(2);
        progressDrawable10.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN);
        pm10.setProgress(60);


        LayerDrawable progressBarDrawable25 = (LayerDrawable) pm25.getProgressDrawable();
        Drawable progressDrawable25 = progressBarDrawable25.getDrawable(2);
        progressDrawable25.setColorFilter(ContextCompat.getColor(this.getContext(), R.color.darkGreen), PorterDuff.Mode.SRC_IN);

        pm25.setProgress(30);


        return rootView;
    }

}
