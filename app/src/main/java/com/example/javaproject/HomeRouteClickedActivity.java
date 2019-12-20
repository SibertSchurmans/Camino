package com.example.javaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class HomeRouteClickedActivity extends AppCompatActivity
        implements OnMapReadyCallback, TaskLoadedCallback{

    private GoogleMap mMap;
    ProgressBar loader;
    RecyclerView recyclerView;
    ArrayList<Integer> pointsId;
    ArrayList<LatLng> route;
    Polyline currentPolyLine;
    Button getRoute, navigateButton;
    ArrayList<String> pointNames;
    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_route_clicked);

        loader = findViewById(R.id.loader);
        recyclerView = findViewById(R.id.recviewTagItems);
        getRoute = findViewById(R.id.getRoute);
        navigateButton = findViewById(R.id.navigateButton);
        mapFragment = new MapFragment();
        route = new ArrayList<>();
        pointNames = new ArrayList<>();
        getRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(HomeRouteClickedActivity.this).execute(getRoute(route), "walking");
            }
        });
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();

                Log.d("bundleTest", pointNames.toString());
                Log.d("bundleTest", route.toString());
                args.putStringArrayList("names", pointNames);
                args.putSerializable("route", route);
                mapFragment.setArguments(args);
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mapFragment).commit();*/
            }
        });

        // Google maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //data ophalen
        Integer routeId = getIntent().getIntExtra("EXTRA_ROUTEID", 1);
        String routeName = getIntent().getStringExtra("EXTRA_ROUTENAME");
        pointsId = getIntent().getIntegerArrayListExtra("EXTRA_POINTS");

        //data gebruiken
        getSupportActionBar().setTitle(routeName);
        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        GetRoute route = new GetRoute(this);
        route.getRoutebyId(routeId, loader, recyclerView);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        class Lpoi extends ListPOI<POI>{
            @Override
            public void add(POI o){
                builder.include(o.getLatLng());
                route.add(o.getLatLng());
                pointNames.add(o.getTitle());
                Log.d("arrayTest", route.toString());
                mMap.addMarker(new MarkerOptions().position(o.getLatLng()).title(o.getTitle()));
                Log.d(TAG, "add: " + builder);
                LatLngBounds bounds = builder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }

        }

        GetPOI getter =new GetPOI(this);
        Lpoi lpoi = new Lpoi();

        for(int i = 0; i < pointsId.size(); i++){

            getter.getPOIbyId(pointsId.get(i),lpoi);
        }



    }

    private String getRoute(ArrayList<LatLng> route){
        int size = route.size();
        String str_origin = "origin=" + route.get(0).latitude + "," + route.get(0).longitude;
        // Destination of route
        String str_dest = "destination=" + route.get(size - 1).latitude + "," + route.get(size - 1).longitude;
        // Mode
        String mode = "mode=walking";

        mMap.addMarker(new MarkerOptions().position(route.get(0)).title(pointNames.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(route.get(size-1)).title(pointNames.get(size-1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        String wayPoint = "&waypoints=optimize:true|";
        for(int i = 1; i < route.size() - 1; i++){
            LatLng point = route.get(i);
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
    public void onTaskDone(Object... values) {
        if (currentPolyLine != null){
            currentPolyLine.remove();
        }currentPolyLine = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
