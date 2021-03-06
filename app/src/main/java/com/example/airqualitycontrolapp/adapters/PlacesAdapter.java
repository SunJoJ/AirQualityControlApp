package com.example.airqualitycontrolapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.models.PlacesData;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<PlacesData> placesDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;
        public TextView dataAqi;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.placeType);
            icon = itemView.findViewById(R.id.iconType);
            dataAqi = itemView.findViewById(R.id.aqiData);
            relativeLayout = itemView.findViewById(R.id.placeColorIndicator);
        }
    }

    public PlacesAdapter(List<PlacesData> placesDataList) {
        this.placesDataList = placesDataList;
    }

    @NonNull
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlacesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlacesData placesData = placesDataList.get(position);
        String aqi = placesData.getDataAqi();

        switch (aqi) {
            case "Bardzo dobry":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_dark_green);
                holder.dataAqi.setText("<25");
                break;
            case "Dobry":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_green);
                holder.dataAqi.setText("<50");
                break;
            case "Umiarkowany":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_yellow);
                holder.dataAqi.setText("<100");
                break;
            case "Dostateczny":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_orange);
                holder.dataAqi.setText("<150");
                break;
            case "Zły":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_red);
                holder.dataAqi.setText("<175");
                break;
            case "Bardzo zły":
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_dark_red);
                holder.dataAqi.setText("<200");
                break;
            default:
                holder.relativeLayout.setBackgroundResource(R.drawable.rounded_corner_unknown);
                holder.dataAqi.setText("--");
                break;
        }


        switch (placesData.getTitle()) {
            case "Praca":
                holder.icon.setImageResource(R.drawable.ic_work);
                break;
            case "Dom":
                holder.icon.setImageResource(R.drawable.ic_home);
                break;
            case "Zewnątrz":
                holder.icon.setImageResource(R.drawable.ic_nature);
                break;
        }
        holder.title.setText(placesData.getTitle());

    }

    @Override
    public int getItemCount() {
        return placesDataList.size();
    }


}
