package com.example.airqualitycontrolapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.DevicesFragment;

public class ScreenInfoFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frament_info, container, false);

       // Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getFragmentManager().addOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener) this);
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
        //else
            //onBackStackChanged();


        return rootView;
    }



//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        if(intent.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
//            TerminalFragment terminal = (TerminalFragment) getFragmentManager().findFragmentByTag("terminal");
//            if (terminal != null)
//                terminal.status("USB device detected");
//        }
//        super.onNewIntent(intent);
//    }

    @Override
    public void onBackStackChanged() {
       // getActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }
}
