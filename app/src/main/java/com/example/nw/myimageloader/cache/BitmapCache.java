package com.example.nw.myimageloader.cache;

import android.graphics.Bitmap;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public interface BitmapCache {


    Bitmap get(BitmapRequest key);

    void put(BitmapRequest key, Bitmap value);

    void remove(BitmapRequest key);

}
