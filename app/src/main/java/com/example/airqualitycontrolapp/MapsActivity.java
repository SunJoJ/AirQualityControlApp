package com.example.airqualitycontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private BottomNavigationView navigationMenu;
    private DatabaseReference dbRef;
    private JSONArray jsonArray;
    private ArrayList<Station> stationArrayList;

    private OkHttpClient client;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_maps);

        mContext = getApplicationContext();
        mActivity = MapsActivity.this;

        client = new OkHttpClient();

        sendRequest("http://api.gios.gov.pl/pjp-api/rest/station/findAll");

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("listOfStations", stationArrayList);

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





        navigationMenu = findViewById(R.id.bottomNavigation);

        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_home_button:

                        return true;
                    case R.id.app_bar_map_button:
                        try {
                            stationArrayList = (ArrayList<Station>) JSONParser.parseStationJsonArray(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ScreenMapFragment mapFragment = new ScreenMapFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("listOfStations", stationArrayList);
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

                        return true;

                }
                return false;
            }
        });


    }


    private void sendRequest(String url) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
//                try{
//                    Log.d("api_response", response.body().string());
//                } catch (Exception e){
//                    Log.d("resp_error", response.message());
//                }

                try {
                    jsonArray = new JSONArray(response.body().string());
                    //stationArrayList = (ArrayList<Station>) JSONParser.parseStationJsonArray(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
    }

}
