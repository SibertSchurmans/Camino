package com.example.javaproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class GetRoute {
    private Context environment;

    public GetRoute(Context context)
    {
        environment = context;
    }

    //haalt alle routes op
    public void getRoutes(ListRoute<Route> listRoute, ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache((environment.getCacheDir()), 1024 * 1024);
        Network network = new BasicNetwork((new HurlStack()));
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "http://171.25.229.102:8229/api/route";

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
                        JSONArray jPointsArray = object.getJSONArray("points");
                        ArrayList<Integer> pointsIdList = new ArrayList<>();

                        for(int a=0;a<jPointsArray.length();a++)
                        {
                            Integer pointId = jPointsArray.getJSONObject(a).getInt("id");
                            pointsIdList.add(pointId);
                        }

                        Route route = new Route(id,title,description,pointsIdList);
                        listRoute.add(route);
                    }
                    catch (Exception e)
                    {
                        //Toast error = Toast.makeText(environment.getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                        //error.show();
                    }
                }
                requestQueue.stop();
                loader.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                //fail.show();
                requestQueue.stop();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }




    //haalt route met opgegeven id op

    public void getRoutebyId(int Id, ProgressBar loader, RecyclerView recyclerView)
    {
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache((environment.getCacheDir()), 1024 * 1024);
        Network network = new BasicNetwork((new HurlStack()));
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "http://171.25.229.102:8229/api/route/"+Id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer id= response.getInt("id");
                    String title = response.getString("name");
                    String description = response.getString("description");
                    JSONArray jPointsArray = response.getJSONArray("points");

                    ArrayList<Integer> pointsIdList = new ArrayList<>();

                    ArrayList<Tag> tags = new ArrayList<>();
                    ArrayList<TagItem> tagItems = new ArrayList<>();

                    for(int a=0;a<jPointsArray.length();a++)
                    {
                        Integer pointId = jPointsArray.getJSONObject(a).getInt("id");
                        String pointName = jPointsArray.getJSONObject(a).getString("name");
                        LatLng latLng = new LatLng(jPointsArray.getJSONObject(a).getDouble("latitude"),jPointsArray.getJSONObject(a).getDouble("longitude"));
                        tagItems.add(new TagItem(pointName +"@"+ pointId));

                        pointsIdList.add(pointId);
                    }
                    Tag tag = new Tag("Scholen", tagItems);
                    tags.add(tag);

                    Route route = new Route(id,title,description,pointsIdList);

                    loader.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    TagItemAdapter adapter = new TagItemAdapter(tags);
                    recyclerView.setAdapter(adapter);



                }
                catch (Exception e)
                {
                    Toast error = Toast.makeText(environment.getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                    error.show();
                    Log.d(TAG, "ERROR JSON" + error.toString());
                }
                requestQueue.stop();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast fail = Toast.makeText(environment.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                fail.show();
                requestQueue.stop();
                Log.d(TAG, "ERROR JSON" + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);


    }


}
