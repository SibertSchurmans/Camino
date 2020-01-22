package com.example.javaproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PoiClickedActivity extends AppCompatActivity implements View.OnTouchListener {

    private String photoLink;
    private ImageView image;
    private ArrayList<Rect> mAreas;
    private ArrayList<HashMap<String, Object>> map;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_clicked);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("info");

        Integer id = getIntent().getIntExtra("Id",1);


        TextView titleText = findViewById(R.id.title);
        TextView routeText = findViewById(R.id.routeView);
        TextView tagText = findViewById(R.id.tagView);
        TextView descriptionText = findViewById(R.id.descriptionView);
        image = findViewById(R.id.image);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        image.setImageResource(R.drawable.caminologo);


        GetPOI getter =new GetPOI(this);
        class Lpoi extends ListPOI<POI>{
            @Override
            public void add(POI o){

                photoLink = o.getPhotoLink();
                //Picasso.get().load(o.getPhotoLink()).placeholder(R.drawable.caminologo).error(R.drawable.caminologo).into(image);
                titleText.setText(o.getTitle());
                descriptionText.setText((o.getSnippet()));

                //zodra route en tags in db zitten haal uit commentaar
//                routeText.setText(o.getRoute());
//                tagText.setText((o.getTag()));

                new GetImage().execute();
            }

            class GetImage extends AsyncTask<Void, Void, Bitmap> {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                protected Bitmap doInBackground(Void... voids) {



                    Bitmap mIcon11 = null;
                    try {
                        String urldisplay = photoLink;
                        InputStream in = new java.net.URL(urldisplay).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);

                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }

                    map = GetClickableParts(id);
                    return mIcon11;
                }


                @Override
                protected void onPostExecute(Bitmap results) {


                    image.setImageBitmap(results);
                    image.setOnTouchListener(PoiClickedActivity.this::onTouch);
                    bitmap = results;
                    setAreas();
                }
            }


        }
        Lpoi lpoi = new Lpoi();
        getter.getPOIbyId(id,lpoi);
        routeText.setText("UCLL");
        tagText.setText("school");





    }

    public void setAreas()
    {
        mAreas = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            int xCoord = Integer.parseInt(map.get(i).get("xCoord").toString());
            int yCoord = Integer.parseInt(map.get(i).get("yCoord").toString());

            double height = bitmap.getHeight() * 0.25;
            double width = bitmap.getWidth() * 0.25;

            if (width > height)
            {
                width = height;
            }
            else
            {
                height = width;
            }

            mAreas.add(new Rect(xCoord - (int) width, yCoord - (int) height, xCoord + (int) width, yCoord + (int) height));

        }
    }

    public ArrayList<HashMap<String, Object>> GetClickableParts(int imageId) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        try {
            URL url = new URL(photoLink + "/parts");
            String s = "";
            // Create Connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == 200) {
                //Succesfully connected

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                s = sb.toString();
            } else {
                // If failed create exception with response message from the connection
                throw new Exception(connection.getResponseMessage());
            }
            connection.disconnect();

            JSONArray mainObject = new JSONArray(s);

            for (int i = 0; i < mainObject.length(); i++) {
                JSONObject partString = new JSONObject(mainObject.getString(i));

                int xCoord = Integer.parseInt(partString.getString("x_coor"));
                int yCoord = Integer.parseInt(partString.getString("y_coor"));
                int forwardId = Integer.parseInt(partString.getString("forward_id"));
                String forwardType = partString.getString("forward_type");


                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("xCoord", xCoord);
                map.put("yCoord", yCoord);
                map.put("forwardId", forwardId);
                map.put("forwardType", forwardType);
                data.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Toast.makeText(v.getContext(), event.getX() + " " + event.getY(), Toast.LENGTH_SHORT).show();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < mAreas.size(); i++) {
                if (mAreas.get(i).contains((int) event.getX(), (int) event.getY())) {
                    //Toast.makeText(v.getContext(), map.get(i).get("forwardType").toString() + " " + map.get(i).get("forwardId").toString(), Toast.LENGTH_SHORT).show();

                    if (map.get(i).get("forwardType").toString().equals("wiki")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", map.get(i).get("forwardId").toString());

                        WikiFragment wikiFragment = new WikiFragment();
                        wikiFragment.setArguments(bundle);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment_container, wikiFragment)
                                .addToBackStack(PoiClickedActivity.class.getSimpleName())
                                .commit();

                        return true;
                    }
                    else if(map.get(i).get("forwardType").toString().equals("image")) {
                        Intent intent = new Intent(this, PoiClickedActivity.class);
                        intent.putExtra("Id",  (int)map.get(i).get("forwardId"));
                        startActivity(intent);
                    }
                }

            }
            Resources res = getResources();
            Drawable d = new BD(res, bitmap , mAreas);
            image.setImageDrawable(d);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    image.setImageBitmap(bitmap);
                }
            }, 5000);

        }
        return false;
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
}




