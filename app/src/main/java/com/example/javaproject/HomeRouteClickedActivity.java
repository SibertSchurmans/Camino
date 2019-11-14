package com.example.javaproject;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeRouteClickedActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_route_clicked);

        // Google maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //data ophalen
        String title = getIntent().getStringExtra("EXTRA_TITLE");
        String kilometres = getIntent().getStringExtra("EXTRA_KILOMETRES");
        String poiNumber = getIntent().getStringExtra("EXTRA_POINUMBER");
        Log.d("route", title);


        //data gebruiken
        getSupportActionBar().setTitle(title);
        //TextView titleText = findViewById(R.id.title);

        //titleText.setText(kilometres);


        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recviewTagItems);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        ArrayList<Tag> tags = new ArrayList<>();

        ArrayList<TagItem> tagItems = new ArrayList<>();
        tagItems.add(new TagItem("kerk 1"));
        tagItems.add(new TagItem("kerk 2"));
        tagItems.add(new TagItem("kerk 3"));
        tagItems.add(new TagItem("kerk 4"));
        tagItems.add(new TagItem("kerk 5"));

        Tag tag = new Tag("kerken", tagItems);
        tags.add(tag);

        ArrayList<TagItem> tagItems2 = new ArrayList<>();
        tagItems2.add(new TagItem("cathedraal 1"));
        tagItems2.add(new TagItem("cathedraal 2"));
        tagItems2.add(new TagItem("cathedraal 3"));
        tagItems2.add(new TagItem("cathedraal 4"));
        tagItems2.add(new TagItem("cathedraal 5"));

        Tag tag2 = new Tag("cathedralen", tagItems2);
        tags.add(tag2);

        TagItemAdapter adapter = new TagItemAdapter(tags);
        recyclerView.setAdapter(adapter);

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

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
