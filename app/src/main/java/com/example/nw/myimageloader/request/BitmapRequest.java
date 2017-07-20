package com.example.nw.myimageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.nw.myimageloader.config.DisplayConfig;
import com.example.nw.myimageloader.core.SimpleImageLoader;
import com.example.nw.myimageloader.listener.ImageLoaderListener;
import com.example.nw.myimageloader.policy.LoadPolicy;
import com.example.nw.myimageloader.utils.ImageViewHelper;
import com.example.nw.myimageloader.utils.Md5Helper;

import java.lang.ref.WeakReference;

/**
 * Created by nw on 17/7/19.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {

    public final WeakReference<ImageView> imageViewWeakReference;
    public final String imageUriMD5;
    public final String url;
    public final DisplayConfig displayConfig;
    public final ImageLoaderListener listener;

    public final LoadPolicy policy = SimpleImageLoader.getInstance().config.loadPolicy;

    public BitmapRequest(String url, ImageView imageView, DisplayConfig displayConfig, ImageLoaderListener listener) {
        this.url = url;
        imageView.setTag(url);
        imageViewWeakReference = new WeakReference<>(imageView);
        this.displayConfig = displayConfig;
        this.listener = listener;
        imageUriMD5 = Md5Helper.toMD5(url);
    }

    /**
     * 请求序列号
     */
    public int serialNum = 0;
    /**
     * 是否取消该请求
     */
    public boolean isCancel = false;

    /**
     *
     */
    public boolean justCacheInMem = false;

    /**
     * 判断imageview的tag是否相等
     */
    public boolean isImageViewTagValid() {
        return imageViewWeakReference.get() != null ? imageViewWeakReference.get().getTag().equals(url) : false;
    }

    public ImageView getImageView() {
        return imageViewWeakReference.get();
    }

    public int getImageViewWidth() {
        return ImageViewHelper.getImageViewWidth(getImageView());
    }

    public int getImageViewHeight() {
        return ImageViewHelper.getImageViewHeight(getImageView());
    }


    @Override
    public int compareTo(@NonNull BitmapRequest request) {
        return policy.compare(this, request);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((imageViewWeakReference == null) ? 0 : imageViewWeakReference.get().hashCode());
        result = prime * result + serialNum;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!imageViewWeakReference.get().equals(other.imageViewWeakReference.get()))
            return false;
        if (serialNum != other.serialNum)
            return false;
        return true;
    }
}
