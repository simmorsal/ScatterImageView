# Scatter Image View

A library that scatters some ImageViews around haphazardly, or orderly 

![screenrecord-2018-07-14-19-38-35222](https://user-images.githubusercontent.com/24822099/42732838-5a9cd2b8-883d-11e8-8463-aefee1f2be11.gif)

# Usage

## Gradle

``` gradle
    implementation 'com.simmorsal.library:scatter_image_view:1.0.0'
```

## XML

Your XML file should look like this:

![scatterimageview](https://user-images.githubusercontent.com/24822099/42663223-e8da4c5e-8649-11e8-844a-aed0ea338c33.png)

The `rootView` of your XML layout file should be a `RelativeLayout` (or `FrameLayout`), add anything you want to the layout, and at the bottom of the `rootView`, add `ScatterImageView`, and set it's width and height to `match_parent`.

 __NOTE:__ if you give an elevation to any of the layouts, make sure to give a higher one to `ScatterImageView`.

## Java

Aside from getting a reference to the `ScatterImageView` in the XML layout, this part has two steps. One, adding images at the start of layout creation. and two, starting the animation.

1. First keep in mind that all the images you add are stored in _one_ `list` (more on this below). For adding images use any of the following 4 methods:
    
    ``` java
    addImage(Context context, int imgRes, Bitmap bitmap, int size, int color, int count)
    ```
    Use this method when you want to add one image. If you want to use an `imageResource`, pass `null` for `bitmap`, and if you want to use `bitmap`, pass `0` for `imgRes`.

    ``` java
    addImages(Context context, int size, int color, int count, int... imgReses)
    ```
    ``` java
    addImages(Context context, int size, int color, int count, Bitmap... bitmaps)
    ```

    The two methods above are used when you have a set of `imageResources`, or `bitmaps`.

    ``` java
    addImages(Context context, int size, int count, Entry... entries)
    ```
    This is for when you have multiple images, and you want each to have it's own `colorFilter`. Here is an example for the last method:

    ``` java
        scatterImageView.addImages(this, 40, 20,
                new Entry(Color.parseColor("#FFEB3B"), R.drawable.beer),
                new Entry(Color.parseColor("#D81B60"), R.drawable.glass_wine));
    ```

    You can pass either `imageResource` or `bitmap` as second argument for `Entry`.\
    This example creates 20 instances of images in `entries` array, totaling in 40 images added to the `list`.


2. To start the animation, you can first choose the scattering to be orderly (like dollar signs and hearts in the GIF above), or haphazardly (like music notes and drinks in the GIF):

    ``` java
    scatterImageView.setHaphazardize(true);
    ```

    Then call `start()` on the object:

    ``` java
    start(int posX, int posY, int duration, int travelDistance)
    and
    start(int posX, int posY, int duration, int travelDistance, int from, int to)
    ```

    `posX` and `posY` are the position you want the animation to start. `duration` is how long should it take images to move `travelDistance` from the center.

    As mentioned above, all the images you add are stored in one `list`. So by using `from` and `to` in the `start()`, you can specify from which image to which one you want animated.

    So continuing example above:

    ``` java
    scatterImageView.setHaphazardize(true)
                    .start(x, y, 2000, 100, 0, 39);
    ```

    So if you added 20 more images to the list and wanted to show them, pass `40` for `from`, and `59` for `to`.


    ###  Tips on getting `posX` and `posY` with touch:

    If you want to pass the touch position to the `start()`, 2 easy ways are like this:

    - One, if you have a widget the size of the screen and want the touches to be registered on that (like the GIF example above), make the widget `clickable`, and then listen for the touch on it:

        ``` java
        fullScreenWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // this if acts like a click, so if you need onClick on the widget, write your code in this IF
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    int posX = (int) e.getX();
                    int posY = (int) e.getY();
                    ...
                }
                return false;
            }
        });
        ```

    - Two, if you don't have a full screen widget, instead you have a widget inside a, say, `NestedScrollView`, you can write the exact same code as above for it, with the difference of getting the location of the widget on the screen, and adding values to the `posX` and `posY`:
    
        ``` java
        int[] widgetPosition = new int[2];
        theWidget.getLocationOnScreen(widgetPosition);

        int posX = (int) e.getX() + widgetPosition[0];
        int posY = (int) e.getY() + widgetPosition[1];
        ```

## Important notes:

- You can add images to the list at any point of app's runtime, but as it makes the app stutter, I suggest doing it in `onCreate`.
- If you dont want `colorFilter` on you images, pass `0` for `color`.
- All the sizes are in `dp`.


## License

None. have fun with the code.