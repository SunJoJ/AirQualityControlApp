package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ParameterInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_parameter_info, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TextView textView = rootView.findViewById(R.id.text);
        String key = getArguments().getString("key");

        switch (key) {
            case "CO":
                textView.setText(R.string.CO);
                break;
            case "SO2":
                textView.setText(R.string.SO2);
                break;
            case "NO2":
                textView.setText(R.string.NO2);
                break;
            case "O3":
                textView.setText(R.string.O3);
                break;
            case "C6H6":
                textView.setText(R.string.C6H6);
                break;
            case "PM10":
                textView.setText(R.string.PM10);
                break;
            case "PM2.5":
                textView.setText(R.string.PM25);
                break;
        }




        return rootView;
    }



}
