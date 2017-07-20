package com.example.nw.myimageloader.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.nw.myimageloader.diskcache.DiskLruCache;
import com.example.nw.myimageloader.diskcache.IOUtil;
import com.example.nw.myimageloader.request.BitmapRequest;
import com.example.nw.myimageloader.utils.BitmapDecoder;
import com.example.nw.myimageloader.utils.L;
import com.example.nw.myimageloader.utils.Md5Helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nw on 17/7/20.
 */

public class DiskCache implements BitmapCache {

    /**
     * 1MB
     *
     * @param key
     * @return
     */
    private static final int MB = 1024 * 1024;

    /**
     * cache dir
     */
    private static final String IMAGE_DISK_CACHE = "bitmap";


    private static volatile DiskCache mInstance;

    /**
     * DiskLruCache mDiskLruCache
     */
    private DiskLruCache mDiskLruCache;

    private DiskCache(Context context) {
        initDiskLruCache(context);
    }

    private void initDiskLruCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, IMAGE_DISK_CACHE);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache
                    .open(cacheDir, getAppVersion(context), 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DiskCache.class) {
                if (mInstance == null) {
                    mInstance = new DiskCache(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.d("", "### context : " + context + ", dir = " + context.getExternalCacheDir());
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * @param context
     * @return
     */
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public Bitmap get(final BitmapRequest bean) {
        // 图片解析器
        BitmapDecoder decoder = new BitmapDecoder() {

            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                final InputStream inputStream = getInputStream(bean.imageUriMD5);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                        options);
                IOUtil.closeQuietly(inputStream);
                return bitmap;
            }
        };

        return decoder.decodeBitmap(bean.getImageViewWidth(),
                bean.getImageViewHeight());
    }

    private InputStream getInputStream(String md5) {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(md5);
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(BitmapRequest request, Bitmap value) {
        if (request.justCacheInMem) {
            L.e(IMAGE_DISK_CACHE);
            return;
        }
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(request.imageUriMD5);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (writeBitmapToDisk(value, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                IOUtil.closeQuietly(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToDisk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        boolean result = true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            IOUtil.closeQuietly(bos);
        }

        return result;
    }

    @Override
    public void remove(BitmapRequest key) {
        try {
            mDiskLruCache.remove(Md5Helper.toMD5(key.imageUriMD5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
