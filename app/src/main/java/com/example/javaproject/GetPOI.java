package com.example.javaproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetPOI {
    private Context environment;
    private GoogleMap mMap;
    private Map<Marker,POI> markerPOIMap;


    public GetPOI(Context context, GoogleMap mmap, Map<Marker,POI> markerPOImap)
    {
        environment = context;
        mMap=mmap;
        markerPOIMap = markerPOImap;
    }

    public GetPOI(Context context)
    {
        environment = context;
    }



    public void getPOIMap(){
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache((environment.getCacheDir()), 1024 * 1024);
        Network network = new BasicNetwork((new HurlStack()));
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "http://171.25.229.102:8229/api/point";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length();i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id= object.getInt("id");
                        String title = object.getString("name");
                        String description = object.getString("description");
                        LatLng latLng = new LatLng(object.getDouble("latitude"),object.getDouble("longitude"));
                        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://171.25.229.102:8229/api/point/"+id+"/photos/", null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray answer) {
                                try{
                                String photo = answer.getString(0);
                                POI poi = new POI(id,title,latLng,mMap,description,markerPOIMap,photo);
                                }
                                catch (Exception f)
                                {
                                    Toast error = Toast.makeText(environment.getApplicationContext(), f.getMessage(),Toast.LENGTH_LONG);
                                    error.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                                fail.show();
                            }
                        });
                        requestQueue.add(request);
                    }
                    catch (Exception e)
                    {
                        Toast error = Toast.makeText(environment.getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                        error.show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                fail.show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getPOIFragment(ListPOI<POI> listPOI, ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache((environment.getCacheDir()), 1024 * 1024);
        Network network = new BasicNetwork((new HurlStack()));
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "http://171.25.229.102:8229/api/point";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length();i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Integer id= object.getInt("id");
                        String title = object.getString("name");
                        String description = object.getString("description");
                        LatLng latLng = new LatLng(object.getDouble("latitude"),object.getDouble("longitude"));
                        //POI poi = new POI(id,title,latLng,description);
                        //listPOI.add(poi);
                    }
                    catch (Exception e)
                    {
                        Toast error = Toast.makeText(environment.getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                        error.show();
                    }
                }
                loader.setVisibility(View.INVISIBLE);
                requestQueue.stop();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                fail.show();
                requestQueue.stop();
                loader.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getPOIbyId(int Id)
    {
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache((environment.getCacheDir()), 1024 * 1024);
        Network network = new BasicNetwork((new HurlStack()));
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "http://171.25.229.102:8229/api/point/"+Id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer id= response.getInt("id");
                    String title = response.getString("name");
                    String description = response.getString("description");
                    LatLng latLng = new LatLng(response.getDouble("latitude"),response.getDouble("longitude"));
                    //POI poi = new POI(id,title,latLng,description);
                }
                catch (Exception e)
                {
                    Toast error = Toast.makeText(environment.getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                    error.show();
                }
                requestQueue.stop();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                fail.show();
                requestQueue.stop();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
