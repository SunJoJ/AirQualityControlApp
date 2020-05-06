package com.example.airqualitycontrolapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingListFragment extends Fragment {

    private ListView listView;
    private ArrayList<RatingListDataModel> ratingListDataModels;
    private static RatingListAdapter ratingListAdapter;

    private ArrayList<String> listOfCities = new ArrayList<String>(){ {
        add("Chengdu,Sichuan,China");
        add("Delhi,Delhi,India");
        add("Moscow,Moscow,Russia");
        add("Beijing,Beijing,China");
        add("Nur-Sultan,Nur-Sultan,Kazakhstan");
        add("Lahore,Punjab,Pakistan");
        add("Kyiv,Kyiv,Ukraine");
        add("Wroclaw,Lower Silesia,Poland");
        add("Krakow,Lesser Poland Voivodeship,Poland");
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rating_list, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        listView = rootView.findViewById(R.id.ratingList);
        ratingListDataModels = new ArrayList<>();

        for(int i = 0; i < listOfCities.size(); i++) {

            RequestService service = RetrofitAirVisualClient.getRetrofitInstance().create(RequestService.class);
            String[] data = listOfCities.get(i).split(",");

            String url = "city?city="+ data[0] +"&state=" + data[1] +"&country=" + data[2] + "&key=9d1d487e-78d7-45f2-9133-95c6b4d08844";
            Call<DataAirVisual> call = service.getAirVisualSupportedCityData(url);

            if(i == listOfCities.size() - 1) {
                call.enqueue(new Callback<DataAirVisual>() {
                    @Override
                    public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                        DataAirVisual dataAirVisual = response.body();
                        String address = dataAirVisual.getCityData().getCity() + ", " + dataAirVisual.getCityData().getCountry();
                        Integer aqiIndex = dataAirVisual.getCityData().getCurrent().getPollution().getAqius();
                        RatingListDataModel ratingListDataModel = new RatingListDataModel(address, aqiIndex);
                        ratingListDataModels.add(ratingListDataModel);

                        ratingListDataModels.sort((o1, o2) -> o1.getAqiIndex().compareTo(o2.getAqiIndex()));
                        Collections.reverse(ratingListDataModels);


                        ratingListAdapter = new RatingListAdapter(ratingListDataModels, getContext());
                        listView.setAdapter(ratingListAdapter);
                        ratingListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DataAirVisual> call, Throwable t) {

                    }
                });
            } else {
                call.enqueue(new Callback<DataAirVisual>() {
                    @Override
                    public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                        DataAirVisual dataAirVisual = response.body();
                        String address = dataAirVisual.getCityData().getCity() + ", " + dataAirVisual.getCityData().getCountry();
                        Integer aqiIndex = dataAirVisual.getCityData().getCurrent().getPollution().getAqius();
                        RatingListDataModel ratingListDataModel = new RatingListDataModel(address, aqiIndex);
                        ratingListDataModels.add(ratingListDataModel);

                    }

                    @Override
                    public void onFailure(Call<DataAirVisual> call, Throwable t) {

                    }
                });
            }

        }


        return rootView;
    }

}
