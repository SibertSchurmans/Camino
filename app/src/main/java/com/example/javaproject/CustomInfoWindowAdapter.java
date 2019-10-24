package com.example.javaproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

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
                ArrayList<Bitmap> bitmapArrayList = Id.getBitmaps();
                photo.setImageBitmap(bitmapArrayList.get(0));
            }

            return view;
        }
}
