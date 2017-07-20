package com.example.nw.myimageloader.cache;

import android.graphics.Bitmap;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public class NoCache implements BitmapCache {
    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
