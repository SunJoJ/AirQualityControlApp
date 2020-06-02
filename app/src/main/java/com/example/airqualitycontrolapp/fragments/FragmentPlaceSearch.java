package com.example.airqualitycontrolapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.models.ItemMarker;
import com.example.airqualitycontrolapp.models.StationGIOS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class FragmentPlaceSearch extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private ClusterManager<ItemMarker> clusterManager;
    private SimpleCursorAdapter mAdapter;
    private ArrayList<String> suggestions;
    private MaterialToolbar searchBar;
    private String placeType;
    private MaterialButton selectButton;

    private String[] SUGGESTIONS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_map_place_search, container, false);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_fragment_search);
        mapFragment.getMapAsync(this);
        selectButton = rootView.findViewById(R.id.selectButton);
        selectButton.setVisibility(View.GONE);

        stationGIOSArrayList = (ArrayList<StationGIOS>) getArguments().getSerializable("listOfStations");
        placeType = getArguments().getString("placeType");

        suggestions = new ArrayList<>();
        for(int i = 0; i < stationGIOSArrayList.size(); i++) {
            String suggestion =  stationGIOSArrayList.get(i).getName();
            suggestions.add(suggestion);
        }

        SUGGESTIONS = suggestions.toArray(new String[0]);
        searchBar = rootView.findViewById(R.id.searchNavigationMenu);

        final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        SearchView searchView = (SearchView) searchBar.getMenu().findItem(R.id.action_search).getActionView();
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setIconifiedByDefault(false);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                String txt = cursor.getString(cursor.getColumnIndex("cityName"));
                searchView.setQuery(txt, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                stationGIOSArrayList.stream().filter(o -> o.getName().equals(s)).forEach(
                        o -> {
                            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(o.getLatitude()), Double.parseDouble(o.getLongitude())))
                                                                    .title(o.getAddressStreet()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker))
                                                                    .snippet(o.getId() + " " + o.getAddressStreet()));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(o.getLatitude()), Double.parseDouble(o.getLongitude())), 14));
                        }
                );
                searchView.setQuery("", false);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });



        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selectButton.setVisibility(View.VISIBLE);

                selectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getActivity().getSharedPreferences("placeType", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(placeType, marker.getSnippet());
                        editor.commit();
                    }
                });

                return false;
            }
        });



    }


    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        for (int i = 0; i < SUGGESTIONS.length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, SUGGESTIONS[i]});
        }
        mAdapter.changeCursor(c);
    }
}
