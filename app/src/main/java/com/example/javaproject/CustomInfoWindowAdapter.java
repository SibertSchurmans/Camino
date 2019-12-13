package com.example.javaproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private Activity Context;
        private Map<Marker, POI> mMarkerMap;

        public CustomInfoWindowAdapter(Activity context, Map<Marker, POI> mmarkermap){
            Context=context;
            mMarkerMap=mmarkermap;
        }
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view=Context.getLayoutInflater().inflate(R.layout.poipopup,null);

            TextView title= (TextView) view.findViewById(R.id.poiTitle);
            ImageView photo =(ImageView) view.findViewById((R.id.poiPhoto));

            title.setText(marker.getTitle());
            POI Id =mMarkerMap.get(marker);
            if(Id != null)
            {
                Picasso.get().load(Id.getPhotoLink()).placeholder(R.drawable.caminologo).error(R.drawable.caminologo).into(photo);
            }

            return view;
        }

}
