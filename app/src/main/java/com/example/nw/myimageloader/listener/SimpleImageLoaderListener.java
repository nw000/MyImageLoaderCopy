package com.example.nw.myimageloader.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.nw.myimageloader.utils.FailReason;

/**
 * Created by nw on 17/7/18.
 */

public class SimpleImageLoaderListener implements ImageLoaderListener {
    @Override
    public void onLoadingStart(String url, ImageView imageView) {

    }

    @Override
    public void onLoadingFailed(String url, ImageView imageView, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String url, ImageView imageView, Bitmap bitmap) {

    }

    @Override
    public void onLoadingCancelled(String url, ImageView imageView) {

    }
}
