package com.example.javaproject;

import java.util.ArrayList;

public class ListRoute<Route> {
    private ArrayList<Route> list;

    public ListRoute()
    {
        list = new ArrayList<>();
    }

    public void add(Route route)
    {
        list.add(route);
    }
    public void remove(Route route)
    {
        list.remove(route);
    };
}
