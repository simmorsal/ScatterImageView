package com.simmorsal.library;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ScatterImageView extends FrameLayout {
    public ScatterImageView(@NonNull Context context) { super(context); }
    public ScatterImageView(@NonNull Context context, @Nullable AttributeSet attrs) { super(context, attrs); }
    public ScatterImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }
//    @SuppressLint("NewApi")
//    public ScatterImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) { super(context, attrs, defStyleAttr, defStyleRes); }

    private List<ImageView> list = new ArrayList<>();
    private boolean haphazardize = false;
    private int halfTheSize;

    public ScatterImageView addImage(Context context, int imgRes, Bitmap bitmap, int size, int color, int count) {
//        halfTheSize = dpToPx(size) / 2;
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(size), dpToPx(size)));
            if (imgRes != 0)
                iv.setImageResource(imgRes);
            else
                iv.setImageBitmap(bitmap);
            if (color != 0)
                iv.setColorFilter(color);

            iv.setVisibility(GONE);

            addView(iv);

            list.add(iv);
        }
        return this;
    }

    public ScatterImageView addImages(Context context, int size, int color, int count, int... imgReses) {

        halfTheSize = dpToPx(size) / 2;
        for (int i = 0; i < count; i++) {
            for (int imgRes : imgReses) {
                ImageView iv = new ImageView(context);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(size), dpToPx(size)));
                iv.setImageResource(imgRes);
                if (color != 0)
                    iv.setColorFilter(color);
                iv.setVisibility(GONE);
                addView(iv);
                list.add(iv);
            }
        }

        return this;
    }

    public ScatterImageView addImages(Context context, int size, int color, int count, Bitmap... bitmaps) {

        halfTheSize = dpToPx(size) / 2;
        for (int i = 0; i < count; i++) {
            for (Bitmap bitmap : bitmaps) {
                ImageView iv = new ImageView(context);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(size), dpToPx(size)));
                iv.setImageBitmap(bitmap);
                if (color != 0)
                    iv.setColorFilter(color);
                iv.setVisibility(GONE);
                addView(iv);
                list.add(iv);
            }
        }

        return this;
    }


    public ScatterImageView addImages(Context context, int size, int count, Entry... entries) {

        halfTheSize = dpToPx(size) / 2;
        for (int i = 0; i < count; i++) {
            for (Entry o : entries) {
                ImageView iv = new ImageView(context);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(size), dpToPx(size)));
                if (o.getImgRes() != 0)
                    iv.setImageResource(o.getImgRes());
                else
                    iv.setImageBitmap(o.getBitmap());
                if (o.getColor() != 0)
                    iv.setColorFilter(o.getColor());
                iv.setVisibility(GONE);
                addView(iv);
                list.add(iv);
            }
        }

        return this;
    }




    @RequiresApi(16)
    public void start(int posX, int posY, int duration, int travelDistance) {
        start(posX, posY, duration, travelDistance, 0, list.size() - 1);
    }

    int haphedDuration;
    @RequiresApi(16)
    public void start(int posX, int posY, int duration, int travelDistance, int from, int to) {
        if (list.size() > 0) {
            List<ImageView> list = new ArrayList<>();
            for (int i = from; i <= to; i++)
                list.add(this.list.get(i));

            posX -= halfTheSize;
            posY -= halfTheSize;

            // degrees between images in circle
            int alpha = 360 / list.size();

            for (int i = 0; i < list.size(); i++) {
//                int x = (int) (dpToPx(0) * Math.cos(Math.toRadians(alpha)));
//                int y = (int) (dpToPx(0) * Math.sin(Math.toRadians(alpha)));
                int endX = (int) (dpToPx(travelDistance) * Math.cos(Math.toRadians(alpha)));
                int endY = (int) (dpToPx(travelDistance) * Math.sin(Math.toRadians(alpha)));
                haphedDuration = duration;
                final ImageView iv = list.get(i);
                iv.setVisibility(VISIBLE);
                ((MarginLayoutParams)iv.getLayoutParams())
                        .setMargins(posX, posY, 0, 0);
//                        .setMargins(posX - iv.getWidth() / 2, posY + iv.getHeight() / 2, 0, 0);
                iv.requestLayout();
                iv.setTranslationX(0);
                iv.setTranslationY(0);
                iv.setAlpha(1f);
                iv.animate().cancel();

                if (haphazardize) {
                    endX = (int) ((Math.random() * (endX / 2)) + (endX / 2));
                    endY = (int) ((Math.random() * (endY / 2)) + (endY / 2));
                    haphedDuration =
                            (int) ((Math.random() * (haphedDuration / 2)) + (haphedDuration / 2));
                }

//                iv.animate().translationX(endX - x).translationY(endY - y)
                iv.animate().translationX(endX).translationY(endY)
                        .setDuration(haphedDuration).withLayer();

                // fading out
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv.animate().alpha(0).setDuration(haphedDuration / 2).withLayer();
                    }
                }, haphedDuration / 3);


                alpha += alpha / (i + 1);
            }
        }
        else
            Log.d("ScatterImageView", "--- NO IMAGES ADDED YET ---");
    }


    public ScatterImageView setHaphazardize(boolean b) {
        haphazardize = b;
        return this;
    }

    public ScatterImageView clearViews() {
        removeAllViews();
        list.clear();
        return this;
    }

    public int getListSize () {
        return list.size();
    }



    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}












