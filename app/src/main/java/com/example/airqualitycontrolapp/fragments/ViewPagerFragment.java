package com.example.airqualitycontrolapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.models.DataAirVisual;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private DataAirVisual dataAirVisual;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_viewpager, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
        if(fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();

        mPager = rootView.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(pagerAdapter);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#6ec6ff"), Color.parseColor("#ffffff"));
        tabLayout.setupWithViewPager(mPager);

        dataAirVisual = (DataAirVisual) getArguments().getSerializable("dataAirVisual");


        return rootView;
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                NearestCityFragment nearestCityFragment = new NearestCityFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataAirVisual", dataAirVisual);
                nearestCityFragment.setArguments(bundle);
                return nearestCityFragment;
            } else {
                SelectedSensorsFragment selectedSensorsFragment = new SelectedSensorsFragment();

                return selectedSensorsFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Najbliższe";
            else
                return "Wybrane";
        }
    }
}
