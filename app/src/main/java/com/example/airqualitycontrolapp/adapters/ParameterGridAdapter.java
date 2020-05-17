package com.example.airqualitycontrolapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airqualitycontrolapp.ChartPainter;
import com.example.airqualitycontrolapp.R;

import java.util.List;

public class ParameterGridAdapter extends RecyclerView.Adapter<ParameterGridAdapter.ViewHolder> {

    private List<String> listOfParameters;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RelativeLayout colorIndicator;
        public TextView data;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            colorIndicator = view.findViewById(R.id.colorIndicator);
            data = view.findViewById(R.id.data);
        }
    }

    public ParameterGridAdapter(List<String> listOfParameters, Context context) {
        this.listOfParameters = listOfParameters;
        this.context = context;
    }

    @NonNull
    @Override
    public ParameterGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_parameter, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ParameterGridAdapter.ViewHolder holder, int position) {
        String parameter = listOfParameters.get(position);
        String name = parameter.split(":")[0].trim();
        String data = parameter.split(":")[1].trim();
        if(data.length() > 7){
            data = data.substring(0, 7);
        }
        int val = (int) Double.parseDouble(data);
        holder.title.setText(name);
        holder.data.setText(data);
        holder.colorIndicator.setBackgroundResource(ChartPainter.intervalParameter(name, val));

    }
    @Override
    public int getItemCount() {
        return listOfParameters.size();
    }


}