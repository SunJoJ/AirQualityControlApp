package com.example.airqualitycontrolapp;

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
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_viewpager, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mPager = rootView.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(pagerAdapter);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager);

        return rootView;
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new ScreenInfoFragment();
            } else {
                return new ScreenInfoFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }
}
