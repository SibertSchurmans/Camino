package com.example.javaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PoiClickedActivity extends AppCompatActivity {
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
        ImageView image = findViewById(R.id.image);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setPadding(0, 25, 0, 25);
        image.setImageResource(R.drawable.caminologo);

        GetPOI getter =new GetPOI(this);
        class Lpoi extends ListPOI<POI>{
            @Override
            public void add(POI o){
                Picasso.get().load(o.getPhotoLink()).placeholder(R.drawable.caminologo).error(R.drawable.caminologo).into(image);
                titleText.setText(o.getTitle());
                descriptionText.setText((o.getSnippet()));
                //zodra route en tags in db zitten haal uit commentaar
//                routeText.setText(o.getRoute());
//                tagText.setText((o.getTag()));
            }
        }
        Lpoi lpoi = new Lpoi();
        getter.getPOIbyId(id,lpoi);
        routeText.setText("UCLL");
        tagText.setText("school");

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




