package com.example.javaproject;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

public class POI {
    Integer Id;
    private String Title;
    private LatLng LatLng;
    private GoogleMap Mmap;
    private String Snippet;
    private Map Mapper;
    private ArrayList<Bitmap> Images;

    public POI(Integer id, String title, LatLng latLng, GoogleMap mmap, String snippet, Map mapper, ArrayList<Bitmap> images)
    {
        Id = id;
        Title=title;
        LatLng=latLng;
        Mmap=mmap;
        Snippet=snippet;
        Mapper=mapper;
        Images= images;
        makeMarker();
    }

    public POI(Integer id, String title, LatLng latLng,String snippet, ArrayList<Bitmap> images)
    {
        Id = id;
        Title=title;
        LatLng=latLng;
        Snippet=snippet;
        Images= images;
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
    public String getTitle(){return Title;}
    public String getSnippet(){return Snippet;};
    public LatLng getLatLng()
    {
        return LatLng;
    }
    public Integer getId(){
        return Id;
    }
}
