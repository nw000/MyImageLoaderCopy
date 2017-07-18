package com.example.nw.myimageloader.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.nw.myimageloader.utils.FailReason;

/**
 * Created by nw on 17/7/18.
 */

public interface ImageLoaderListener {

    void onLoadingStart(String url, ImageView imageView);

    void onLoadingFailed(String url, ImageView imageView, FailReason failReason);

    void onLoadingComplete(String url, ImageView imageView, Bitmap bitmap);

    void onLoadingCancelled(String url, ImageView imageView);
}
