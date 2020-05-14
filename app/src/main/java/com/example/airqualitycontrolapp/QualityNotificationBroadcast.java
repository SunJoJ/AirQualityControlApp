package com.example.airqualitycontrolapp;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitAirVisualClient;
import com.example.airqualitycontrolapp.models.DataAirVisual;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualityNotificationBroadcast extends BroadcastReceiver {
    private DataAirVisual dataAirVisual;

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_layout_collapsed);

        View view = View.inflate(context, R.layout.notification_layout_collapsed, null);

        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();

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
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_dark_green);
                } else if (isBetween(aqiIndex, 26, 50)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - dobra");
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_green);
                } else if (isBetween(aqiIndex, 51, 100)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - umiarkowana");
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_yellow);
                } else if (isBetween(aqiIndex, 101, 150)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - niezdrowa");
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_orange);
                } else if (isBetween(aqiIndex, 151, 200)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - zła");
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_red);
                }   else if (isBetween(aqiIndex, 201, 500)) {
                    collapsedView.setTextViewText(R.id.detailsNotification, "Jakość powietrza - bardzo zła");
                    collapsedView.setInt(R.id.aqiNumberNotification, "setBackgroundResource", R.drawable.rounded_corner_left_dark_red);
                }

                String address = dataAirVisual.getCityData().getCity() + ", " + dataAirVisual.getCityData().getState() + ", " + dataAirVisual.getCityData().getCountry();
                collapsedView.setTextViewText(R.id.addressNotification, address);
                collapsedView.setTextViewText(R.id.notificationTime, dtf.format(now));

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
