package com.example.nw.myimageloader.config;

import android.content.Context;
import android.content.res.Resources;

import com.example.nw.myimageloader.cache.BitmapCache;
import com.example.nw.myimageloader.cache.MemoryCache;
import com.example.nw.myimageloader.policy.LoadPolicy;
import com.example.nw.myimageloader.policy.SericePolicy;

/**
 * Created by nw on 17/7/18.
 */

public class SimpleImageLoaderConfig {


    public final BitmapCache bitmapCache;
    public final DisplayConfig displayConfig;
    private Context context;
    public final Resources res;
    public LoadPolicy loadPolicy;

    private SimpleImageLoaderConfig(Builder builder) {
        displayConfig = builder.displayConfig;
        context = builder.context;
        res = context.getResources();
        bitmapCache = builder.bitmapCache;
    }


    public static final class Builder {
        private DisplayConfig displayConfig = DisplayConfig.createSimpleConfig();
        private Context context;
        private BitmapCache bitmapCache = new MemoryCache();
        private LoadPolicy loadPolicy = new SericePolicy();

        public Builder() {
        }

        public Builder displayConfig(DisplayConfig config) {
            displayConfig = config;
            return this;
        }

        public Builder context(Context context) {
            context = context.getApplicationContext();
            return this;
        }

        public Builder bitmapCache(BitmapCache bitmapCache) {
            this.bitmapCache = bitmapCache;
            return this;
        }

        public Builder loadPolicy(LoadPolicy loadPolicy) {
            this.loadPolicy = loadPolicy;
            return this;
        }

        public SimpleImageLoaderConfig build() {
            return new SimpleImageLoaderConfig(this);
        }
    }
}
