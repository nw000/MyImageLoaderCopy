package com.example.nw.myimageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public class DoubleCache implements BitmapCache {

    DiskCache diskCache;

    MemoryCache mMemoryCache = new MemoryCache();

    public DoubleCache(Context context) {
        diskCache = DiskCache.getInstance(context);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            bitmap = diskCache.get(key);
            saveBitmapIntoMemory(key, bitmap);
        }
        return bitmap;
    }

    private void saveBitmapIntoMemory(BitmapRequest key, Bitmap bitmap) {
        // 如果Value从disk中读取,那么存入内存缓存
        if (bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        diskCache.put(key, value);
        mMemoryCache.put(key, value);
    }

    @Override
    public void remove(BitmapRequest key) {
        diskCache.remove(key);
        mMemoryCache.remove(key);
    }


}
