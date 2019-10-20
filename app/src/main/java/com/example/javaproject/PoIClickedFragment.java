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


public class PoIClickedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_po_iclicked, container, false);

        Bundle bundle = getArguments();
        String title = bundle.getString("Title");
        int image = bundle.getInt("Image");
        Log.d("poi", title);

        TextView titleText = (TextView) v.findViewById(R.id.title);
        ImageView imageView = v.findViewById(R.id.image);

        imageView.setImageResource(R.drawable.kathedraal);
        titleText.setText(title);
        return v;

    }

}
