package com.example.airqualitycontrolapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.R;

import java.util.List;

public class ParametersAdapter extends RecyclerView.Adapter<ParametersAdapter.ViewHolder> {

    private List<String> listOfParameters;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }

    public ParametersAdapter(List<String> listOfParameters, Context context) {
        this.listOfParameters = listOfParameters;
        this.context = context;
    }

    @NonNull
    @Override
    public ParametersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_parameter, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ParametersAdapter.ViewHolder holder, int position) {
        String parameter = listOfParameters.get(position);
        holder.title.setText(parameter);

    }
    @Override
    public int getItemCount() {
        return listOfParameters.size();
    }


}
