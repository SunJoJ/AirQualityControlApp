package com.example.airqualitycontrolapp;

import android.content.Context;
import android.icu.text.ListFormatter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class SensorsListAdapter extends ArrayAdapter<SensorsListDataModel> implements View.OnTouchListener {

    private ArrayList<SensorsListDataModel> sensorsListDataModels;
    Context mContext;

    public SensorsListAdapter(ArrayList<SensorsListDataModel> data, Context context) {
        super(context, R.layout.fragment_marker_details, data);
        this.sensorsListDataModels = data;
        this.mContext = context;
    }


    private static class ViewHolder {
        TextView addressTextView;
        TextView detailsTextView;
        RelativeLayout markerDetailsFragmentLayout;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SensorsListDataModel sensorsListDataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_marker_details, parent, false);
            viewHolder.addressTextView = convertView.findViewById(R.id.addressTextView);
            viewHolder.detailsTextView = convertView.findViewById(R.id.detailsTextView);
            viewHolder.markerDetailsFragmentLayout = convertView.findViewById(R.id.markerDetailsFragmentLayout);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.addressTextView.setText(sensorsListDataModel.getAddress());
        String index = sensorsListDataModel.getQualityIndex().getIndexLevel().getIndexLevelName();
        viewHolder.detailsTextView.setText("Indeks jakości - " + index.toLowerCase());
        switch (index) {
            case "Bardzo dobry":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                break;
            case "Dobry":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_green);
                break;
            case "Umiarkowany":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_yellow);
                break;
            case "Dostateczny":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_orange);
                break;
            case "Zły":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_red);
                break;
            case "Bardzo zły":
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                break;
            default:
                viewHolder.detailsTextView.setBackgroundResource(R.drawable.rounded_corner_unknown);
                break;
        }


        return convertView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
