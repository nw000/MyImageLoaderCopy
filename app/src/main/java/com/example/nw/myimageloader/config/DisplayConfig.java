package com.example.nw.myimageloader.config;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by nw on 17/7/18.
 */

public class DisplayConfig {
    private final int imageResOnLoading;
    private final int imageResForEmptyUri;
    private final int imageResOnFail;
    private final Drawable imageOnLoading;
    private final Drawable imageForEmptyUri;
    private final Drawable imageOnFail;

    private DisplayConfig(Builder builder) {
        imageResOnLoading = builder.imageResOnLoading;
        imageResForEmptyUri = builder.imageResForEmptyUri;
        imageResOnFail = builder.imageResOnFail;
        imageOnLoading = builder.imageOnLoading;
        imageForEmptyUri = builder.imageForEmptyUri;
        imageOnFail = builder.imageOnFail;
    }

    public boolean shouldShowImageForEmptyUri() {
        return imageResForEmptyUri != 0 || imageForEmptyUri != null;
    }

    public Drawable getImageForEmptyUri(Resources res) {
        if (shouldShowImageForEmptyUri()) {
            return imageForEmptyUri == null ? res.getDrawable(imageResForEmptyUri) : imageForEmptyUri;
        }
        return null;
    }


    public static final class Builder {
        private int imageResOnLoading = 0;
        private int imageResForEmptyUri = 0;
        private int imageResOnFail = 0;
        private Drawable imageOnLoading = null;
        private Drawable imageForEmptyUri = null;
        private Drawable imageOnFail = null;

        public Builder showImageResOnLoading(int imageResOnLoading) {
            this.imageResOnLoading = imageResOnLoading;
            return this;
        }

        public Builder showImageResForEmptyUri(int imageResForEmptyUri) {
            this.imageResForEmptyUri = imageResForEmptyUri;
            return this;
        }

        public Builder showImageResOnFail(int imageResOnFail) {
            this.imageResOnFail = imageResOnFail;
            return this;
        }

        public Builder showImageOnLoading(Drawable imageOnLoading) {
            this.imageOnLoading = imageOnLoading;
            return this;
        }

        public Builder showImageOnImageForEmptyUri(Drawable imageForEmptyUri) {
            this.imageForEmptyUri = imageForEmptyUri;
            return this;
        }

        public Builder showImageResOnFail(Drawable imageOnFail) {
            this.imageOnFail = imageOnFail;
            return this;
        }

        public DisplayConfig build() {
            return new DisplayConfig(this);
        }
    }

    public static DisplayConfig createSimpleConfig() {
        return new Builder().build();
    }
}
