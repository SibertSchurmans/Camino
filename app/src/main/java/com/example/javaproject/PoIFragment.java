package com.example.javaproject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class PoIFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pointsofinterest, container, false);
        FloatingActionButton mFab = v.findViewById(R.id.floatingActionButton2);


        TextView categoryView = new TextView(getContext());

        categoryView.setText("Kerken");
        categoryView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        categoryView.setPadding(25,25,25,25);
        categoryView.setTextColor(Color.WHITE);
        categoryView.setGravity(Gravity.START);

        LinearLayout mainLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mainLayout.setLayoutParams(mainParams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(categoryView);

        mainLayout.addView(createPointOfInterests("Toulouse", R.drawable.testimage, "Route: Tolosana", "Tags: Kerk, Toulouse, Tolosana", v));
        mainLayout.addView(createPointOfInterests("Toulouse2", R.drawable.testimage, "Route: Tolosana", "Tags: Kerk, Toulouse, Tolosana", v));

        RelativeLayout relativeLayout = v.findViewById(R.id.layout);
        relativeLayout.addView(mainLayout);

        mFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }

    public CardView createPointOfInterests(String title, int imageResource, String route, String tags, View v) {


        CardView cardview = new CardView(getContext());
        ImageView imageView = new ImageView(getContext());
        TextView titleView = new TextView(getContext());
        TextView routeView = new TextView(getContext());
        TextView tagsView = new TextView(getContext());

        LinearLayout linearLayout = new LinearLayout(getContext());

        LinearLayout textLayout = new LinearLayout(getContext());

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams cardViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(250, 250);



        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setPadding(5,25,5,25);

        imageParams.gravity=Gravity.CENTER;

        imageView.setImageResource(imageResource);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(0,25,0,25);
        imageView.setLayoutParams(imageParams);


        cardview.setLayoutParams(cardViewParams);
        cardview.setRadius(15);
        cardview.setPadding(25, 25, 25, 25);
        cardview.setCardBackgroundColor(Color.WHITE);
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);

        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cardview.getLayoutParams();
        cardViewMarginParams.setMargins(50, 30, 50, 30);
        cardview.requestLayout();

        titleView.setText(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        titleView.setTextColor(Color.BLUE);
        titleView.setGravity(Gravity.START);
        titleView.setLayoutParams(textParams);

        routeView.setText(route);
        routeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        routeView.setTextColor(Color.BLUE);
        routeView.setGravity(Gravity.START);
        titleView.setLayoutParams(textParams);

        tagsView.setText(tags);
        tagsView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        tagsView.setPadding(0,50,0,0);
        tagsView.setTextColor(Color.BLUE);
        tagsView.setGravity(Gravity.START);
        tagsView.setLayoutParams(textParams);



        linearLayout.addView(imageView);

        textLayout.addView(titleView);
        textLayout.addView(routeView);
        textLayout.addView(tagsView);

        linearLayout.addView(textLayout);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        cardview.addView(linearLayout);

        return cardview;

    }

}
