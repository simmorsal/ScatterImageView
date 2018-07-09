package com.simmorsal.scatterimageview;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.simmorsal.library.Entry;
import com.simmorsal.library.ScatterImageView;

public class MainActivity extends AppCompatActivity {

    private ScatterImageView scatterImageView;
    private int index = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scatterImageView = findViewById(R.id.scatterImageView);


        // adding images
        scatterImageView.addImages(this, 40, 10,
                new Entry(Color.parseColor("#E91E63"), R.drawable.ic_audiotrack_black_24dp),
                new Entry(Color.parseColor("#03A9F4"), R.drawable.ic_audiotrack_black_24dp),
                new Entry(Color.parseColor("#CDDC39"), R.drawable.ic_audiotrack_black_24dp));

        scatterImageView.addImage(this, R.drawable.ic_monetization_on_black_24dp, null, 40, Color.parseColor("#4CAF50"), 20);

        scatterImageView.addImages(this, 40, 20,
                new Entry(Color.parseColor("#FFEB3B"), R.drawable.beer),
                new Entry(Color.parseColor("#D81B60"), R.drawable.glass_wine));

        scatterImageView.addImages(this, 40, 15,
                new Entry(Color.parseColor("#9C27B0"), R.drawable.ic_favorite_black_24dp),
                new Entry(Color.parseColor("#00BCD4"), R.drawable.ic_favorite_border_black_24dp));


        // listening for touch
        findViewById(R.id.rootView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    onRootViewClicked(motionEvent);
                }

                return true;
            }
        });
    }

    private void onRootViewClicked(MotionEvent e) {
        index++;
        int x = (int) e.getX(), y = (int) e.getY();
        switch (index) {
            case 1:
                scatterImageView.setHaphazardize(true);
                scatterImageView.start(x, y, 2000, 100, 0, 29);
                break;

            case 2:
                scatterImageView.setHaphazardize(false);
                scatterImageView.start(x, y, 2000, 150, 30, 49);
                break;

            case 3:
                scatterImageView.setHaphazardize(true);
                scatterImageView.start(x, y, 2000, 120, 50, 89);
                break;

            case 4:
                scatterImageView.setHaphazardize(false);
                scatterImageView.start(x, y, 3000, 150, 90, 119);
                index = 0;
        }
    }
}
