package com.example.airqualitycontrolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NearestCityFragment extends Fragment {

    private DataAirVisual dataAirVisual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_nearest_city, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        dataAirVisual = (DataAirVisual) getArguments().getSerializable("dataAirVisual");

        TextView address = rootView.findViewById(R.id.addressText);
        TextView cityName = rootView.findViewById(R.id.cityNameText);
        TextView qualityIndex = rootView.findViewById(R.id.qualityIndex);
        TextView qualityText = rootView.findViewById(R.id.qualityText);
        TextView tempText = rootView.findViewById(R.id.tempText);
        TextView humidityText = rootView.findViewById(R.id.humidityText);
        TextView windSpeedText = rootView.findViewById(R.id.windSpeedText);
        RelativeLayout quality = rootView.findViewById(R.id.quality);
        ImageView weatherIcon = rootView.findViewById(R.id.weatherIcon);
        TextView timeStamp = rootView.findViewById(R.id.timeStamp);

        String addressText =  dataAirVisual.getCityData().getState() + ", " + dataAirVisual.getCityData().getCountry();
        address.setText(addressText);
        cityName.setText(dataAirVisual.getCityData().getCity());
        String qualityIndexStr = dataAirVisual.getCityData().getCurrent().getPollution().getAqius() + " AQI";
        qualityIndex.setText(qualityIndexStr);
        String tempStr = dataAirVisual.getCityData().getCurrent().getWeather().getTemperature() + " °C";
        tempText.setText(tempStr);
        String humidStr = dataAirVisual.getCityData().getCurrent().getWeather().getHumidity() + " %";
        humidityText.setText(humidStr);
        String windSpeedStr = dataAirVisual.getCityData().getCurrent().getWeather().getWindSpeed() + " km/g";
        windSpeedText.setText(windSpeedStr);
        timeStamp.setText(dataAirVisual.getCityData().getCurrent().getPollution().getTimeStamp());

        Integer aqiIndex = dataAirVisual.getCityData().getCurrent().getPollution().getAqius();
        if (isBetween(aqiIndex, 0, 25)) {
            qualityText.setText("Jakość powietrza - bardzo dobra");
            quality.setBackgroundResource(R.drawable.rounded_corner_dark_green);
        } else if (isBetween(aqiIndex, 26, 50)) {
            qualityText.setText("Jakość powietrza - dobra");
            quality.setBackgroundResource(R.drawable.rounded_corner_green);
        } else if (isBetween(aqiIndex, 51, 100)) {
            qualityText.setText("Jakość powietrza - umiarkowana");
            quality.setBackgroundResource(R.drawable.rounded_corner_yellow);
        } else if (isBetween(aqiIndex, 101, 150)) {
            qualityText.setText("Jakość powietrza - niezdrowa");
            quality.setBackgroundResource(R.drawable.rounded_corner_orange);
        } else if (isBetween(aqiIndex, 151, 200)) {
            qualityText.setText("Jakość powietrza - zła");
            quality.setBackgroundResource(R.drawable.rounded_corner_red);
        }   else if (isBetween(aqiIndex, 201, 500)) {
            qualityText.setText("Jakość powietrza - bardzo zła");
            quality.setBackgroundResource(R.drawable.rounded_corner_dark_red);
        }

        String iconWeather = dataAirVisual.getCityData().getCurrent().getWeather().getIc();
        switch (iconWeather) {
            case "01d":
                weatherIcon.setImageResource(R.drawable.a01d);
                break;
            case "01n":
                weatherIcon.setImageResource(R.drawable.a01n);
                break;
            case "02d":
                weatherIcon.setImageResource(R.drawable.a02d);
                break;
            case "02n":
                weatherIcon.setImageResource(R.drawable.a02n);
                break;
            case "03d":
                weatherIcon.setImageResource(R.drawable.a03d);
                break;
            case "04d":
                weatherIcon.setImageResource(R.drawable.a04d);
                break;
            case "09d":
                weatherIcon.setImageResource(R.drawable.a09d);
                break;
            case "10d":
                weatherIcon.setImageResource(R.drawable.a10d);
                break;
            case "10n":
                weatherIcon.setImageResource(R.drawable.a10n);
                break;
        }


        return rootView;
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


}
