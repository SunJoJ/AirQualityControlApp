package com.example.airqualitycontrolapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.models.PlacesAddresses;

import java.util.List;

public class PlacesAddressesAdapter extends RecyclerView.Adapter<PlacesAddressesAdapter.ViewHolder> {

    private List<PlacesAddresses> placesAddressesList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;
        public TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.placeType);
            icon = itemView.findViewById(R.id.imagePlace);
            address = itemView.findViewById(R.id.address);

        }
    }

    public PlacesAddressesAdapter(List<PlacesAddresses> placesAddressesList) {
        this.placesAddressesList = placesAddressesList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_select, parent, false);
        return new PlacesAddressesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlacesAddresses placesAddresses = placesAddressesList.get(position);
        holder.address.setText(placesAddresses.getAddress());
        holder.title.setText(placesAddresses.getPlaceType());

        switch (placesAddresses.getPlaceType()) {
            case "Praca":
                holder.icon.setImageResource(R.drawable.ic_business_24px);
                break;
            case "Dom":
                holder.icon.setImageResource(R.drawable.ic_home_black_24dp);
                break;
            case "Zewnątrz":
                holder.icon.setImageResource(R.drawable.ic_nature_people_black_24dp);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return placesAddressesList.size();
    }


}
