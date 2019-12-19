package com.example.javaproject;

import android.content.Context;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class HomeRouteClickedActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressBar loader;
    RecyclerView recyclerView;
    ArrayList<Integer> pointsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_route_clicked);

        loader = findViewById(R.id.loader);
        recyclerView = findViewById(R.id.recviewTagItems);

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

}
