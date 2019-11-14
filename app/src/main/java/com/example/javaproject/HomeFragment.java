package com.example.javaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        TextView categoryView = new TextView(getContext());

        LinearLayout mainLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mainLayout.setLayoutParams(mainParams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        mainLayout.addView( createPointOfInterests("Naam van Route 1", R.drawable.camino_route_test, "17", "25 Km", v));
        mainLayout.addView( createPointOfInterests("Naam van Route 2", R.drawable.camino_route_test2, "24", "33 Km", v));

        RelativeLayout relativeLayout = v.findViewById(R.id.layout);
        relativeLayout.addView(mainLayout);
        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    public CardView createPointOfInterests(String title, int imageResource, String poiNumber, String kilometres, View v) {
        TextView categoryView = new TextView(getContext());
        CardView cardview = new CardView(getContext());
        ImageView imageView = new ImageView(getContext());
        TextView titleView = new TextView(getContext());
        TextView routeView = new TextView(getContext());
        TextView tagsView = new TextView(getContext());

        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout textLayout = new LinearLayout(getContext());

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams cardViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setPadding(5,25,5,0);

        cardview.setLayoutParams(cardViewParams);
        cardview.setRadius(30);
        cardview.setPadding(30, 30, 30, 0);
        cardview.setCardBackgroundColor(Color.parseColor("#bd971c"));
        cardview.setMaxCardElevation(15);
        cardview.setMaxCardElevation(6);
        cardview.getId();

        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cardview.getLayoutParams();
        cardViewMarginParams.setMargins(50, 30, 50, 30);
        cardview.requestLayout();

        titleView.setText(title + "\nAfstand: " + kilometres);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        titleView.setTextColor(Color.WHITE);
        titleView.setGravity(Gravity.START);
        titleView.setPadding(20,0,0,0);
        titleView.setLayoutParams(textParams);

        routeView.setText("Interessante locaties:    " + poiNumber);
        routeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        routeView.setTextColor(Color.WHITE);
        routeView.setGravity(Gravity.START);
        routeView.setPadding(40,0,0,0);
        titleView.setLayoutParams(textParams);

//        imageParams.gravity=Gravity.CENTER;
//
//        imageView.setImageResource(imageResource);
//        imageView.setAdjustViewBounds(true);
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imageView.setPadding(50,0,50,25);
//        imageView.setMaxHeight(300);
//        imageView.setLayoutParams(imageParams);



        textLayout.addView(titleView);
        textLayout.addView(routeView);
        textLayout.addView(tagsView);
        linearLayout.addView(textLayout);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(imageView);

        cardview.setOnTouchListener((v1, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    Intent intent = new Intent(getContext(), HomeRouteClickedActivity.class);
                    intent.putExtra("EXTRA_TITLE", title);
                    intent.putExtra("EXTRA_KILOMETRES", kilometres);
                    intent.putExtra("EXTRA_POINUMBER", poiNumber);
                    startActivity(intent);
                }
            }
            return false;
        });

        cardview.addView(linearLayout);

        return cardview;

    }
}


