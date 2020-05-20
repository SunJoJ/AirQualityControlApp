package com.example.airqualitycontrolapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitAirVisualClient;
import com.example.airqualitycontrolapp.fragments.NearestCityFragment;
import com.example.airqualitycontrolapp.fragments.ViewPagerFragment;
import com.example.airqualitycontrolapp.models.DataAirVisual;
import com.example.airqualitycontrolapp.models.RatingListDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingListAdapter extends ArrayAdapter<RatingListDataModel> {

    private ArrayList<RatingListDataModel> ratingListDataModels;
    private Context context;

    public RatingListAdapter(ArrayList<RatingListDataModel> data, Context context) {
        super(context, R.layout.fragment_rating_position, data);
        this.ratingListDataModels = data;
        this.context = context;
    }

    private static class ViewHolder {
        TextView addressTextView;
        TextView indexTextView;
        ImageView flagImageView;
        TextView positionTextView;
        RelativeLayout colorIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RatingListDataModel ratingListDataModel = getItem(position);
        RatingListAdapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new RatingListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_rating_position, parent, false);
            viewHolder.addressTextView = convertView.findViewById(R.id.nameOfCity);
            viewHolder.indexTextView = convertView.findViewById(R.id.aqiIndex);
            viewHolder.flagImageView = convertView.findViewById(R.id.flag);
            viewHolder.positionTextView = convertView.findViewById(R.id.position);
            viewHolder.colorIndex = convertView.findViewById(R.id.indexColor);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RatingListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.addressTextView.setText(ratingListDataModel.getCity() + ", " + ratingListDataModel.getCountry());
        viewHolder.indexTextView.setText(Integer.toString(ratingListDataModel.getAqiIndex()));
        viewHolder.positionTextView.setText(Integer.toString(position+1));
        String country = ratingListDataModel.getCountry().toLowerCase();
        Integer resource = convertView.getResources().getIdentifier(country, "drawable", context.getPackageName());
        viewHolder.flagImageView.setImageResource(resource);


        Integer index = ratingListDataModel.getAqiIndex();
        if (isBetween(index, 0, 25)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_dark_green);
        } else if (isBetween(index, 26, 50)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_green);
        } else if (isBetween(index, 51, 100)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_yellow);
        } else if (isBetween(index, 101, 150)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_orange);
        } else if (isBetween(index, 151, 200)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_red);
        }   else if (isBetween(index, 201, 500)) {
            viewHolder.colorIndex.setBackgroundResource(R.drawable.rounded_corner_dark_red);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestService service= RetrofitAirVisualClient.getRetrofitInstance().create(RequestService.class);
                String url = "city?city="+ ratingListDataModel.getCity() +"&state=" + ratingListDataModel.getState() +"&country=" + ratingListDataModel.getCountry() + "&key=9d1d487e-78d7-45f2-9133-95c6b4d08844";
                Call<DataAirVisual> call1 = service.getAirVisualSupportedCityData(url);
                call1.enqueue(new Callback<DataAirVisual>() {
                    @Override
                    public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                        DataAirVisual dataAirVisual = response.body();
                        Log.d("resp", response.toString());
                        NearestCityFragment nearestCityFragment = new NearestCityFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataAirVisual", dataAirVisual);
                        nearestCityFragment.setArguments(bundle);

                        FragmentTransaction trans = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                        trans.add(R.id.fragment_container, nearestCityFragment);
                        trans.addToBackStack("nearestCityFragment");
                        trans.commit();
                    }

                    @Override
                    public void onFailure(Call<DataAirVisual> call, Throwable t) {
                        Log.d("resp", t.getMessage());
                    }
                });



            }
        });


        return convertView;
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


}
