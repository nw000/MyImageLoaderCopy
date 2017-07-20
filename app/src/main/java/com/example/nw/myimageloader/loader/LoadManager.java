package com.example.nw.myimageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nw on 17/7/20.
 */

public class LoadManager {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";

    private Loader nullLoader = new NullLoader();

    private Map<String, Loader> mLoaderMap = new HashMap<String, Loader>();

    private static volatile LoadManager mInstance;

    private LoadManager() {
        register(HTTP, new UrlLoader());
        register(HTTPS, new UrlLoader());
        register(FILE, new LocalLoader());
    }

    public final synchronized void register(String http, Loader loader) {
        mLoaderMap.put(http, loader);
    }

    public static LoadManager getInstance() {
        if (mInstance == null) {
            synchronized (LoadManager.class) {
                if (mInstance == null) {
                    mInstance = new LoadManager();
                }
            }
        }
        return mInstance;
    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return nullLoader;
    }
}
