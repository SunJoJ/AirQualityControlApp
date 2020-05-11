package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

public class ParameterInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_parameter_info, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TextView textView = rootView.findViewById(R.id.text);
        MaterialToolbar materialToolbar = rootView.findViewById(R.id.topAppBar);
        String key = getArguments().getString("key");

        switch (key) {
            case "CO":
                Spanned sp1 = Html.fromHtml( getString(R.string.CO));
                textView.setText(sp1);
                materialToolbar.setTitle("Tlenek węgla");
                break;
            case "SO2":
                Spanned sp2 = Html.fromHtml( getString(R.string.SO2));
                textView.setText(sp2);
                materialToolbar.setTitle("Dwutlenek siarki");
                break;
            case "NO2":
                Spanned sp3 = Html.fromHtml(getString(R.string.NO2));
                textView.setText(sp3);
                materialToolbar.setTitle("Dwutlenek azotu");
                break;
            case "O3":
                Spanned sp4 = Html.fromHtml(getString(R.string.O3));
                textView.setText(sp4);
                materialToolbar.setTitle("Ozon");
                break;
            case "C6H6":
                Spanned sp5 = Html.fromHtml(getString(R.string.C6H6));
                textView.setText(sp5);
                materialToolbar.setTitle("Benzen");
                break;
            case "PM10":
                Spanned sp6 = Html.fromHtml(getString(R.string.PM10));
                textView.setText(sp6);
                materialToolbar.setTitle("Pył zawieszony PM10");
                break;
            case "PM2.5":
                Spanned sp7 = Html.fromHtml(getString(R.string.PM25));
                textView.setText(sp7);
                materialToolbar.setTitle("Pył zawieszony PM2.5");
                break;
        }
        textView.setMovementMethod(new ScrollingMovementMethod());



        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.closeButton:
                        Fragment fragment = getFragmentManager().findFragmentByTag("parameterInfoFragment");
                        if(fragment != null)
                            getFragmentManager().beginTransaction().remove(fragment).commit();
                        return true;
                }

                return false;
            }
        });



        return rootView;
    }



}
