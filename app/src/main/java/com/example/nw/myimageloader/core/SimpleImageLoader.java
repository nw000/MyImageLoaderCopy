package com.example.nw.myimageloader.core;

import android.widget.ImageView;

import com.example.nw.myimageloader.config.DisplayConfig;
import com.example.nw.myimageloader.config.SimpleImageLoaderConfig;
import com.example.nw.myimageloader.listener.ImageLoaderListener;
import com.example.nw.myimageloader.listener.SimpleImageLoaderListener;
import com.example.nw.myimageloader.utils.L;

/**
 * Created by nw on 17/7/18.
 */

public class SimpleImageLoader {

    public static final String TAG = SimpleImageLoader.class.getSimpleName();

    static final String LOG_INIT_CONFIG = "Initialize ImageLoader with configuration";
    static final String LOG_DESTROY = "Destroy ImageLoader";
    static final String LOG_LOAD_IMAGE_FROM_MEMORY_CACHE = "Load image from memory cache [%s]";

    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageLoader which had already been initialized before. " + "To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.";
    private static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
    private static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";

    private SimpleImageLoaderConfig config;
    private ImageLoaderListener defaultListener = new SimpleImageLoaderListener();

    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader();
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(SimpleImageLoaderConfig config) {
        if (config == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.config == null) {
            L.d(LOG_INIT_CONFIG);
            this.config = config;
        } else {
            L.d(WARNING_RE_INIT_CONFIG);
        }
    }


    public void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, null);
    }

    public void displayImage(String url, ImageView imageView, DisplayConfig config) {
        displayImage(url, imageView, config, null);
    }

    private void displayImage(String url, ImageView imageView, DisplayConfig config, ImageLoaderListener listener) {
        checkConfiguration();
        if (listener == null) {
            listener = defaultListener;
        }

    }

    private void checkConfiguration() {
        if (this.config == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }


}
