package com.example.javaproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

class BD extends BitmapDrawable {
    private ArrayList<Rect> mSelection;

    public BD(Resources res, Bitmap bitmap, ArrayList<Rect> rect) {
        super(res, bitmap);
        mSelection = rect;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for(int i = 0; i < mSelection.size(); i++)
        {
            canvas.clipRect(mSelection.get(i), Region.Op.DIFFERENCE);

        }
        canvas.drawColor(0x66000000);
    }
}
