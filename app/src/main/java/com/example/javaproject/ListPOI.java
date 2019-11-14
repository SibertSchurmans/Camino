package com.example.javaproject;

import java.util.ArrayList;

public class ListPOI<POI> {
    private ArrayList<POI> list;

    public ListPOI()
    {
        list = new ArrayList<>();
    }

    public void add(POI poi)
    {
        list.add(poi);
    }
    public void remove(POI poi)
    {
        list.remove(poi);
    };
}
