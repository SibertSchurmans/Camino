package com.example.javaproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener, TaskLoadedCallback, View.OnClickListener{
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private GeoApiContext mGeoApiContext = null;
    private static final String TAG = "marker";
    private ArrayList<PolylineData> mPolylinesData = new ArrayList<>();
    private Marker mSelectedMarker = null;
    private MarkerOptions santiago, ucll;
    private Polyline currentPolyLine;
    private Button navigationButton;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public MapFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        navigationButton = v.findViewById(R.id.NavigationButton);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        navigationButton.setOnClickListener(this);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng dest = new LatLng(42.878212, -8.544844);
        LatLng origin = new LatLng(50.90769, 5.41875);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));

        santiago = new MarkerOptions().position(dest).title("Santiago De Compostella");
        ucll = new MarkerOptions().position(origin).title("Diepenbeek");

        mMap.addMarker(santiago);
        mMap.addMarker(ucll);

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        int index = 0;
        for (PolylineData polylineData : mPolylinesData) {
            Log.d(TAG, "onPolylineClick: toString: " + polylineData.toString());
            if (polyline.getId().equals(polylineData.getPolyline().getId())) {
                polylineData.getPolyline().setColor(getResources().getColor(R.color.lightBlue));
                polylineData.getPolyline().setZIndex(1);

                LatLng endLocation = new LatLng(
                        polylineData.getLeg().endLocation.lat,
                        polylineData.getLeg().endLocation.lng
                );

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(endLocation)
                        .title("Route #" + index)
                        .snippet("Duration: " + polylineData.getLeg().duration)
                );

                marker.showInfoWindow();
            } else {
                polylineData.getPolyline().setColor(getResources().getColor(R.color.Gray));
                polylineData.getPolyline().setZIndex(0);
            }
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyLine != null)
            currentPolyLine.remove();
        currentPolyLine = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onClick(View v) {
        new FetchURL(getContext()).execute(getUrl(ucll.getPosition(), santiago.getPosition(), "walking"), "walking");
    }
}
