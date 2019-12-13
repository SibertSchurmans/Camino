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
    private String PhotoLink;

    public POI(Integer id, String title, LatLng latLng, GoogleMap mmap, String snippet, Map mapper, String photoLink)
    {
        Id = id;
        Title=title;
        LatLng=latLng;
        Mmap=mmap;
        Snippet=snippet;
        Mapper=mapper;
        PhotoLink  = photoLink;
        makeMarker();
    }

    public POI(Integer id, String title, LatLng latLng,String snippet, String photoLink)
    {
        Id = id;
        Title=title;
        LatLng=latLng;
        Snippet=snippet;
        PhotoLink=photoLink;
    }

    private void makeMarker()
    {
        MarkerOptions markerOptions= new MarkerOptions().position(LatLng).title(Title).snippet(Snippet);
        Marker marker= Mmap.addMarker(markerOptions);
        Mmap.addMarker(markerOptions);
        Mapper.put(marker,this);
    }

    public String getPhotoLink() {return PhotoLink;}
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
