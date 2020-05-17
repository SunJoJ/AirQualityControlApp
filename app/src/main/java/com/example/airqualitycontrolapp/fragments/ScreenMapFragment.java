package com.example.airqualitycontrolapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.airqualitycontrolapp.MapIconRenderer;
import com.example.airqualitycontrolapp.R;
import com.example.airqualitycontrolapp.clients.RequestService;
import com.example.airqualitycontrolapp.clients.RetrofitClientGIOS;
import com.example.airqualitycontrolapp.models.ItemMarker;
import com.example.airqualitycontrolapp.models.QualityIndex;
import com.example.airqualitycontrolapp.models.Sensor;
import com.example.airqualitycontrolapp.models.StationGIOS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenMapFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference dbRef;
    private List<Sensor> sensors;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private ArrayList<StationGIOS> stationGIOSArrayList;
    private ArrayList<Sensor> sensorArrayList;
    private ArrayList<QualityIndex> qualityIndexArrayList;

    private ClusterManager<ItemMarker> clusterManager;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private SimpleCursorAdapter mAdapter;
    private ArrayList<String> suggestions;
    private MaterialToolbar searchBar;

    private String[] SUGGESTIONS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_map, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        stationGIOSArrayList = (ArrayList<StationGIOS>) getArguments().getSerializable("listOfStations");

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
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(o.getLatitude()), Double.parseDouble(o.getLongitude())), 14));

                        }
                );



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

    public boolean containsName(final List<StationGIOS> list, final String name){
        return list.stream().anyMatch(o -> o.getName().equals(name));
    }

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        for (int i = 0; i < SUGGESTIONS.length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, SUGGESTIONS[i]});
        }
        mAdapter.changeCursor(c);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        clusterManager = new ClusterManager<>(getContext(), map);
        map.setOnMarkerClickListener(clusterManager);
        clusterManager.setRenderer(new MapIconRenderer(getContext(), map, clusterManager));

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ItemMarker>() {
            @Override
            public boolean onClusterItemClick(ItemMarker item) {

                Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();

                final MarkerDetailsFragment markerDetailsFragment = new MarkerDetailsFragment();
                final String curId = item.getSnippet().split(" ")[0];
                final String address = item.getSnippet().substring(item.getSnippet().indexOf(" ") + 1);

                RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
                Call<QualityIndex> call = service.getQualityIndexByStationId(Integer.valueOf(curId));

                call.enqueue(new Callback<QualityIndex>() {
                    @Override
                    public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
                        QualityIndex qualityIndex = response.body();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Index", qualityIndex);
                        bundle.putString("Address", address);
                        bundle.putString("StationId", curId);

                        markerDetailsFragment.setArguments(bundle);
                        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

                        trans.add(R.id.marker_details, markerDetailsFragment, "markerDetailsFragment");
                        trans.addToBackStack("markerDetailsFragment");
                        trans.commit();
                    }

                    @Override
                    public void onFailure(Call<QualityIndex> call, Throwable t) {

                    }
                });


                return true;
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Fragment fragment = getFragmentManager().findFragmentByTag("markerDetailsFragment");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ItemMarker>() {
            @Override
            public boolean onClusterClick(Cluster<ItemMarker> cluster) {
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        for(int i = 0; i < stationGIOSArrayList.size(); i++) {
            StationGIOS station = stationGIOSArrayList.get(i);
            LatLng pp = new LatLng(Double.parseDouble(station.getLatitude()), Double.parseDouble(station.getLongitude()));
            map.setOnCameraIdleListener(clusterManager);


            clusterManager.addItem(new ItemMarker(Double.parseDouble(station.getLatitude()), Double.parseDouble(station.getLongitude()), station.getName(), station.getId()
                    + " " + station.getAddressStreet()
                    + ", " + station.getCity().getName()));




//            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId()
//                    + " " + station.getAddressStreet()
//                    + ", " + station.getCity().getName()));

//            RequestService service = RetrofitClientGIOS.getRetrofitInstance().create(RequestService.class);
//            Call<QualityIndex> call = service.getQualityIndexByStationId(stationGIOSArrayList.get(i).getId());
//
//            call.enqueue(new Callback<QualityIndex>() {
//                @Override
//                public void onResponse(Call<QualityIndex> call, Response<QualityIndex> response) {
//                    QualityIndex qualityIndex =  response.body();
//
//                    switch (qualityIndex.getIndexLevel().getIndexLevelName()) {
//                        case "Bardzo dobry":
//                        case "Dobry":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        case "Umiarkowany":
//                        case "Dostateczny":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        case "Zły":
//                        case "Bardzo zły":
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_red_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                        default:
//                            map.addMarker(new MarkerOptions().position(pp).title(station.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_yellow_marker)).snippet(station.getId()
//                                    + " " + station.getAddressStreet()
//                                    + ", " + station.getCity().getName()));
//                            break;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<QualityIndex> call, Throwable t) {
//
//                }
//            });


        }
    }


}
