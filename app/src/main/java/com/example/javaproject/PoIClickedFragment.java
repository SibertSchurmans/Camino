package com.example.javaproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PoIClickedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_po_iclicked, container, false);
        TextView titleText = (TextView) v.findViewById(R.id.title);
        ImageView imageView = v.findViewById(R.id.image);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(0, 25, 0, 25);
        imageView.setImageResource(R.drawable.caminologo);
        Bundle bundle = getArguments();
        int Id= bundle.getInt("Id");
        GetPOI getter =new GetPOI(getContext());
        class Lpoi extends ListPOI<POI>{
            @Override
            public void add(POI o){
                Picasso.get().load(o.getPhotoLink()).placeholder(R.drawable.caminologo).error(R.drawable.caminologo).into(imageView);
                titleText.setText(o.getTitle());
            }
        }
        Lpoi lpoi = new Lpoi();
        getter.getPOIbyId(Id,lpoi);
        return v;

    }

}
