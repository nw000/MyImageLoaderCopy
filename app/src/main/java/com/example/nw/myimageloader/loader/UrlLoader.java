package com.example.nw.myimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nw.myimageloader.diskcache.IOUtil;
import com.example.nw.myimageloader.request.BitmapRequest;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nw on 17/7/20.
 */

public class UrlLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imageUrl = request.url;
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null ;
        HttpURLConnection conn = null ;
        try {
            URL url = new URL(imageUrl);
            conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            bitmap =  BitmapFactory.decodeStream(is, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(fos);
            if ( conn != null ) {
                // 关闭流
                conn.disconnect();
            }
        }
        return bitmap;
    }
}
