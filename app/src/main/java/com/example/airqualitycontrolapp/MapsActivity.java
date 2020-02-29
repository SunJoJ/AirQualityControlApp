package com.example.airqualitycontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends AppCompatActivity {

    private BottomNavigationView navigationMenu;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Sensor sensor = new Sensor();
//        sensor.setLatitude(52.232855);
//        sensor.setLongitude(20.9211133);
//        sensor.setCountry("Poland");
//        sensor.setCity("Warszawa");
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Sensor");
//        dbRef.push().setValue(sensor);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ScreenMapFragment firstFragment = new ScreenMapFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }




        navigationMenu = findViewById(R.id.bottomNavigation);

        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_add_place:
                        ScreenMapFragment screenMapFragment = new ScreenMapFragment();
                        Bundle argsPlace = new Bundle();

                        screenMapFragment.setArguments(argsPlace);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.fragment_container, screenMapFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        return true;
                    case R.id.app_bar_list:
                        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                        Bundle argsList = new Bundle();
                        //args.putInt(ViewPagerFragment.ARG_POSITION, position);
                        viewPagerFragment.setArguments(argsList);

                        FragmentTransaction pagerTransaction = getSupportFragmentManager().beginTransaction();

                        pagerTransaction.replace(R.id.fragment_container, viewPagerFragment);
                        pagerTransaction.addToBackStack(null);

                        pagerTransaction.commit();

                        return true;
                    case R.id.app_bar_statistics:
                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                        Sensor sensor = new Sensor();
                        sensor.setLatitude(52.232855);
                        sensor.setLongitude(20.9211133);
                        sensor.setCountry("Poland");
                        sensor.setCity("Warszawa");
                        dbRef = FirebaseDatabase.getInstance().getReference().child("Sensor");
                        dbRef.push().setValue(sensor);


                        return true;
                }
                return false;
            }
        });



    }

}
