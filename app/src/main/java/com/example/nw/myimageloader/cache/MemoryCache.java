package com.example.nw.myimageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public class MemoryCache implements BitmapCache {


    private final LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().availableProcessors() / 1024);
        //取1/4作为可用内存的缓存
        int cacheSize = maxMemory / 4;

        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return lruCache.get(key.url);
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        lruCache.put(key.url, value);
    }

    @Override
    public void remove(BitmapRequest key) {
        lruCache.remove(key.url);
    }
}
