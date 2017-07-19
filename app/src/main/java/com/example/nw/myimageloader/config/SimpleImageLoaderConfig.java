package com.example.nw.myimageloader.config;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by nw on 17/7/18.
 */

public class SimpleImageLoaderConfig {

    public final DisplayConfig displayConfig;
    private Context context;
    public final Resources res;

    private SimpleImageLoaderConfig(Builder builder) {
        displayConfig = builder.displayConfig;
        context = builder.context;
        res = context.getResources();
    }


    public static final class Builder {
        private DisplayConfig displayConfig = DisplayConfig.createSimpleConfig();
        private Context context;

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

        public SimpleImageLoaderConfig build() {
            return new SimpleImageLoaderConfig(this);
        }
    }
}
