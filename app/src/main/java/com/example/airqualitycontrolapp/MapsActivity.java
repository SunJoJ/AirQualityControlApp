package com.example.airqualitycontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitAirVisualClient;
import com.example.airqualitycontrolapp.clients.RetrofitClientGIOS;
import com.example.airqualitycontrolapp.fragments.ProfileViewpagerFragment;
import com.example.airqualitycontrolapp.fragments.RatingListFragment;
import com.example.airqualitycontrolapp.fragments.ScreenInfoFragment;
import com.example.airqualitycontrolapp.fragments.ScreenMapFragment;
import com.example.airqualitycontrolapp.fragments.ViewPagerFragment;
import com.example.airqualitycontrolapp.models.DataAirVisual;
import com.example.airqualitycontrolapp.models.StationGIOS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity {

    private static final int PERMISSION_ID = 44;
    private Context mContext;
    private Activity mActivity;
    private BottomNavigationView navigationMenu;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private DataAirVisual dataAirVisual;
    private FusedLocationProviderClient fusedLocationClient;
    private double longitude;
    private double latitude;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.Theme_MyApp);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mContext = getApplicationContext();
        mActivity = MapsActivity.this;
        getLastLocation();

        RequestService service1= RetrofitAirVisualClient.getRetrofitInstance().create(RequestService.class);
        Call<DataAirVisual> call1 = service1.getAirVisualNearestCityData(String.valueOf(latitude), String.valueOf(longitude));
        call1.enqueue(new Callback<DataAirVisual>() {
            @Override
            public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                dataAirVisual = response.body();
                Log.d("resp", dataAirVisual.getCityData().getCity());

                ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataAirVisual", dataAirVisual);
                viewPagerFragment.setArguments(bundle);

                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_container, viewPagerFragment);
                trans.addToBackStack("viewPagerFragment");
                trans.commit();
            }

            @Override
            public void onFailure(Call<DataAirVisual> call, Throwable t) {
                Log.d("resp", t.getMessage());
            }
        });


        createNotificationChannel();
        Intent intent = new Intent(MapsActivity.this, QualityNotificationBroadcast.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MapsActivity.this, 10, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar firingCal  = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR_OF_DAY, 18);
        firingCal.set(Calendar.MINUTE, 44);
        firingCal.set(Calendar.SECOND, 0);

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime  = currentCal.getTimeInMillis();

        if(intendedTime >= currentTime) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firingCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }else{
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();

            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        navigationMenu = findViewById(R.id.bottomNavigation);
        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_home_button:

                        RequestService service1= RetrofitAirVisualClient.getRetrofitInstance().create(RequestService.class);
                        Call<DataAirVisual> call1 = service1.getAirVisualNearestCityData(String.valueOf(latitude), String.valueOf(longitude));
                        call1.enqueue(new Callback<DataAirVisual>() {
                            @Override
                            public void onResponse(Call<DataAirVisual> call, Response<DataAirVisual> response) {
                                dataAirVisual = response.body();
                                Log.d("resp", dataAirVisual.getCityData().getCity());

                                ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("dataAirVisual", dataAirVisual);
                                viewPagerFragment.setArguments(bundle);

                                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                                trans.replace(R.id.fragment_container, viewPagerFragment);
                                trans.addToBackStack("viewPagerFragment");
                                trans.commit();
                            }

                            @Override
                            public void onFailure(Call<DataAirVisual> call, Throwable t) {
                                Log.d("resp", t.getMessage());
                            }
                        });

                        return true;
                    case R.id.app_bar_map_button:
                        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                        Call<ArrayList<StationGIOS>> call = service.getAllStations();

                        call.enqueue(new Callback<ArrayList<StationGIOS>>() {
                            @Override
                            public void onResponse(Call<ArrayList<StationGIOS>> call, Response<ArrayList<StationGIOS>> response) {
                                stationGIOSArrayList = response.body();

                                ScreenMapFragment mapFragment = new ScreenMapFragment();
                                Bundle bundle1 = new Bundle();
                                bundle1.putSerializable("listOfStations", stationGIOSArrayList);
                                mapFragment.setArguments(bundle1);
                                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

                                trans.replace(R.id.fragment_container, mapFragment);
                                trans.addToBackStack("mapFragment");
                                trans.commit();

                            }

                            @Override
                            public void onFailure(Call<ArrayList<StationGIOS>> call, Throwable t) {
                                Log.d("Response", t.getLocalizedMessage());
                            }
                        });

                        return true;
                    case R.id.app_bar_me:

                        ProfileViewpagerFragment profileViewpagerFragment = new ProfileViewpagerFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, profileViewpagerFragment, "profileFragment");
                        fragmentTransaction.addToBackStack("profileFragment");
                        fragmentTransaction.commit();


                        return true;
                    case R.id.app_bar_statistics_button:

                        RatingListFragment ratingListFragment = new RatingListFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, ratingListFragment);
                        transaction.addToBackStack("ratingListFragment");
                        transaction.commit();

                        return true;
                    case R.id.app_bar_settings_button:



                        return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        navigationMenu.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    private void createNotificationChannel() {
        CharSequence name = "Air quality";
        String description = "Air quality notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("Air_quality_notification", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }



    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }


}
