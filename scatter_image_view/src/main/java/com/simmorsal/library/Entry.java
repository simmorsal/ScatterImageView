package com.simmorsal.library;

import android.graphics.Bitmap;

public class Entry {
    private int color;
    private int imgRes = 0;
    private Bitmap bitmap = null;

    public Entry (int color, int imgRes) {
        this.color = color;
        this.imgRes = imgRes;
    }

    public Entry (int color, Bitmap bitmap) {
        this.color = color;
        this.bitmap = bitmap;
    }


    public int getColor() {
        return color;
    }

    public int getImgRes() {
        return imgRes;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
