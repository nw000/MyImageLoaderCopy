package com.example.nw.myimageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public class NullLoader extends AbsLoader{

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        Log.e(NullLoader.class.getSimpleName(), "### wrong schema, your image uri is : "
                + request.url);
        return null;
    }
}
