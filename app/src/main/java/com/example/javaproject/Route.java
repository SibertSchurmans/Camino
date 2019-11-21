package com.example.javaproject;
import java.util.ArrayList;

public class Route {
    public Integer getId() {
        return Id;
    }

    private Integer Id;
    private String Name;
    private String Description;
    private ArrayList<Integer> Points;

    public Route() {}

    public Route(Integer id, String name, String description, ArrayList<Integer> points)
    {
        Id = id;
        Name = name;
        Description = description;
        Points = points;
    }

    public Route(Integer id, String name, String description)
    {
        Id = id;
        Name = name;
        Description = description;
    }



    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<Integer> getPoints() {
        return Points;
    }

    public void setPoints(ArrayList<Integer> points) {
        Points = points;
    }

}
