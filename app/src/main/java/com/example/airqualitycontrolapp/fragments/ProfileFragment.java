package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.airqualitycontrolapp.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);

        ProgressBar pb = rootView.findViewById(R.id.pb);

        pb.setProgress(100);



        return rootView;
    }


}
