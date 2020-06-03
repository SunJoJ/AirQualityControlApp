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
import com.google.android.material.tabs.TabLayout;

public class ProfileViewpagerFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile_viewpager, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        viewPager = rootView.findViewById(R.id.profileViewPager);
        pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = rootView.findViewById(R.id.profileTabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#6ec6ff"), Color.parseColor("#ffffff"));
        tabLayout.setupWithViewPager(viewPager);



        return rootView;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                PlacesProfileFragment placesProfileFragment = new PlacesProfileFragment();

                return placesProfileFragment;
            } else if (position == 1){
                IndicatorsFragment indicatorsFragment = new IndicatorsFragment();

                return indicatorsFragment;
            } else {
                ProfileFragment profileFragment = new ProfileFragment();


                return profileFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return "Lokalizacji";
            else if(position == 1) return "Wpływ";
            else return "Profil";
        }
    }

}
