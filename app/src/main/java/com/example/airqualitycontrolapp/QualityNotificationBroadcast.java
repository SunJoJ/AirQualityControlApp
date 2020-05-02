package com.example.airqualitycontrolapp;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import io.opencensus.resource.Resource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualityNotificationBroadcast extends BroadcastReceiver {
    private DataAirVisual dataAirVisual;

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_layout_collapsed);

        View view = View.inflate(context, R.layout.notification_layout_collapsed, null);


        TextView addressNotification = view.findViewById(R.id.addressNotification);

        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");


        RequestService service1= RetrofitAirVisualClient.getRetrofitInstance().create(RequestService.class);
        Call<DataAirVisual> call1 = service1.getAirVisualNearestCityData(String.valueOf(latitude), String.valueOf(longitude));
        call1.enqueue(new Callback<DataAirVisual>() {
            @Override
            public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                dataAirVisual = response.body();

                Integer aqiIndex = dataAirVisual.getCityData().getCurrent().getPollution().getAqius();
                collapsedView.setTextViewText(R.id.aqiNumberNotification, aqiIndex + " AQI");
                if (isBetween(aqiIndex, 0, 25)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - bardzo dobra");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_dark_green);
                } else if (isBetween(aqiIndex, 26, 50)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - dobra");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_green);
                } else if (isBetween(aqiIndex, 51, 100)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - umiarkowana");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_yellow);
                } else if (isBetween(aqiIndex, 101, 150)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - niezdrowa");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_orange);
                } else if (isBetween(aqiIndex, 151, 200)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - zła");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_red);
                }   else if (isBetween(aqiIndex, 201, 500)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - bardzo zła");
                    collapsedView.setInt(R.id.notificationDetails, "setBackgroundResource", R.drawable.rounded_corner_dark_red);
                }

                String address = dataAirVisual.getCityData().getCity() + ", " + dataAirVisual.getCityData().getState() + ", " + dataAirVisual.getCityData().getCountry();
                collapsedView.setTextViewText(R.id.addressNotification, address);
                addressNotification.setText(address);
                Notification notification = new NotificationCompat.Builder(context, "Air_quality_notification")
                        .setSmallIcon(R.drawable.wind_icon)
                        .setCustomContentView(collapsedView)
                        //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .build();

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(10, notification);


            }

            @Override
            public void onFailure(Call<DataAirVisual> call, Throwable t) {
                Log.d("resp", t.getMessage());
            }
        });


    }


    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
