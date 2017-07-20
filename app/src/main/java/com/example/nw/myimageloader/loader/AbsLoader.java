package com.example.nw.myimageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.nw.myimageloader.cache.BitmapCache;
import com.example.nw.myimageloader.config.DisplayConfig;
import com.example.nw.myimageloader.core.SimpleImageLoader;
import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public abstract class AbsLoader implements Loader {

    private static BitmapCache mCache = SimpleImageLoader.getInstance().config.bitmapCache;


    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = mCache.get(request);
        if (bitmap == null) {
            showLoading(request);
            bitmap = onLoadImage(request);
            cacheBitmap(request, bitmap);
        }
        deliveryToUIThread(request, bitmap);
    }

    protected abstract Bitmap onLoadImage(BitmapRequest request);


    /**
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        // 缓存新的图片
        if (bitmap != null && mCache != null) {
            synchronized (mCache) {
                mCache.put(request, bitmap);
            }
        }
    }

    private void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid()
                && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {

                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.imageResOnLoading);
                }
            });
        }
    }

    /**
     * 将结果投递到UI,更新ImageView
     *
     * @param bean
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request,
                                      final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {

            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    /**
     * 更新ImageView
     *
     * @param request
     * @param result
     */
    private void updateImageView(BitmapRequest request, Bitmap result) {
        final ImageView imageView = request.getImageView();
        final String uri = request.url;
        if (result != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(result);
        }

        // 加载失败
        if (result == null && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.imageResOnFail);
        }
    }

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.imageResOnLoading > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.imageResOnFail > 0;
    }

}
