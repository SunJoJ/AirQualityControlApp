package com.example.airqualitycontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity{

    private Context mContext;
    private Activity mActivity;
    private BottomNavigationView navigationMenu;
    private DatabaseReference dbRef;
    private JSONArray jsonArray;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private OkHttpClient client;
    private String response;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_maps);

        mContext = getApplicationContext();
        mActivity = MapsActivity.this;
        client = new OkHttpClient();

//        Bundle bundle1 = new Bundle();
//        bundle1.putSerializable("listOfStations", stationArrayList);

//        if (findViewById(R.id.fragment_container) != null) {
//            if (savedInstanceState != null) {
//                return;
//            }
//            ScreenMapFragment firstFragment = new ScreenMapFragment();
//            firstFragment.setArguments(getIntent().getExtras());
//            firstFragment.setArguments(bundle1);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, firstFragment).commit();
//        }


        RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
        Call<ArrayList<StationGIOS>> call = service.getAllStations();

        call.enqueue(new Callback<ArrayList<StationGIOS>>() {
            @Override
            public void onResponse(Call<ArrayList<StationGIOS>> call, Response<ArrayList<StationGIOS>> response) {
                Log.d("Response", String.valueOf(response.body().size()));
                stationGIOSArrayList = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<StationGIOS>> call, Throwable t) {
                Log.d("Response", t.getLocalizedMessage());
            }
        });



        navigationMenu = findViewById(R.id.bottomNavigation);

        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_home_button:


                        return true;
                    case R.id.app_bar_map_button:
                        ScreenMapFragment mapFragment = new ScreenMapFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("listOfStations", stationGIOSArrayList);
                        mapFragment.setArguments(bundle1);
                        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

                        trans.replace(R.id.fragment_container, mapFragment);
                        trans.addToBackStack(null);
                        trans.commit();

                        return true;
                    case R.id.app_bar_me:
                        //Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.app_bar_statistics_button:
                        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                        Bundle argsList = new Bundle();
                        //args.putInt(ViewPagerFragment.ARG_POSITION, position);
                        viewPagerFragment.setArguments(argsList);

                        FragmentTransaction pagerTransaction = getSupportFragmentManager().beginTransaction();

                        pagerTransaction.replace(R.id.fragment_container, viewPagerFragment);
                        pagerTransaction.addToBackStack(null);

                        pagerTransaction.commit();

                        return true;
                    case R.id.app_bar_settings_button:

                        Log.d("resp", String.valueOf(stationGIOSArrayList.size()));

                        return true;
                }
                return false;
            }
        });
    }


}
