package com.example.javaproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PoIFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pointsofinterest, container, false);
        FloatingActionButton mFab = v.findViewById(R.id.floatingActionButton2);
        mFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }

}
