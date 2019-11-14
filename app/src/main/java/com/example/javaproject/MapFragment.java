package com.example.javaproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener, TaskLoadedCallback, View.OnClickListener{
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private GeoApiContext mGeoApiContext = null;
    private static final String TAG = "marker";
    private ArrayList<PolylineData> mPolylinesData = new ArrayList<>();
    private MarkerOptions santiago, ucll, markerGenk, markerKerk;
    LatLng dest = new LatLng(42.878212, -8.544844);
    LatLng origin = new LatLng(50.90769, 5.41875);
    LatLng genk = new LatLng(50.957502, 5.482947);
    LatLng kerk = new LatLng(50.956471, 5.183986);
    private Polyline currentPolyLine;
    private Button navigationButton;
    private HashMap<Marker,POI> markerPOIMap;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markerPOIMap = new HashMap<>();
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));

        //santiago = new MarkerOptions().position(dest).title("Santiago De Compostella");
        //ucll = new MarkerOptions().position(origin).title("Diepenbeek");
        //markerGenk = new MarkerOptions().position(genk).title("Genk").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        //markerKerk = new MarkerOptions().position(kerk).title("Kerk").icon(BitmapDescriptorFactory.fromAsset("Kerk.png"));

        //mMap.addMarker(santiago);
        //mMap.addMarker(ucll);
        //mMap.addMarker(markerGenk);
        //mMap.addMarker(markerKerk);
        GetPOI getter = new GetPOI(getContext(),mMap,markerPOIMap);
        getter.getPOIMap();

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }

        CameraPosition googlePlex = CameraPosition.builder()
                .target(origin)
                .zoom(4)
                .bearing(0)
                .tilt(45)
                .build();

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(getActivity(), markerPOIMap);
        mMap.setInfoWindowAdapter(adapter);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                POI poi= markerPOIMap.get(marker);
                Bundle bundle = new Bundle();
                bundle.putString("Title", poi.getTitle());
                bundle.putInt("Image", 1);

                // set Fragmentclass Arguments
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                PoIClickedFragment fragobj = new PoIClickedFragment();
                fragobj.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, fragobj);
                fragmentTransaction.commit();
            }
        });

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        //adding waypoints to the route
        ArrayList<LatLng> wayPoints = new ArrayList<LatLng>();
        LatLng paris = new LatLng(48.8588377, 2.2770202);
        LatLng carcassone = new LatLng(43.2077961,2.3140611);
        LatLng orleans = new LatLng(47.8733947,1.8421688);
        wayPoints.add(paris);
        wayPoints.add(orleans);
        wayPoints.add(carcassone);
        String wayPoint = "&waypoints=";
        for(int i = 0; i <= wayPoints.size() - 1; i++){
            LatLng point = wayPoints.get(i);
            wayPoint += point.latitude + "," + point.longitude + "|";
        }
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode + wayPoint;
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
        if (currentPolyLine != null){
            currentPolyLine.remove();
        }currentPolyLine = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onClick(View v) {
        new FetchURL(getContext()).execute(getUrl(ucll.getPosition(), santiago.getPosition(), "walking"), "walking");
    }
}
