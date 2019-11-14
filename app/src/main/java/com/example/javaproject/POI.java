package com.example.javaproject;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

public class POI {
    private String Title;
    private LatLng LatLng;
    private GoogleMap Mmap;
    private String Snippet;
    private Map Mapper;
    private ArrayList<Bitmap> Images;

    public POI(String title, LatLng latLng, GoogleMap mmap, String snippet, Map mapper, ArrayList<Bitmap> images)
    {
        Title=title;
        LatLng=latLng;
        Mmap=mmap;
        Snippet=snippet;
        Mapper=mapper;
        Images= images;
        makeMarker();
    }

    private void makeMarker()
    {
        MarkerOptions markerOptions= new MarkerOptions().position(LatLng).title(Title).snippet(Snippet);
        Marker marker= Mmap.addMarker(markerOptions);
        Mmap.addMarker(markerOptions);
        Mapper.put(marker,this);
    }

    public ArrayList<Bitmap> getBitmaps()
    {
        return Images;
    }

    public LatLng getLatLng()
    {
        return LatLng;
    }
}
