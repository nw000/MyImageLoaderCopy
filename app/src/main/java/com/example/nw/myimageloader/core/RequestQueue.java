package com.example.nw.myimageloader.core;

import com.example.nw.myimageloader.request.BitmapRequest;
import com.example.nw.myimageloader.utils.L;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nw on 17/7/19.
 */

public class RequestQueue {

    /*
     * 线程安全的请求队列
     */
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();
    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    /**
     * 默认的核心线程数
     */
    private static final int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 线程数量
     */
    private int defaultCoreNums = DEFAULT_CORE_NUMS;


    public RequestQueue() {
        this(DEFAULT_CORE_NUMS);
    }


    public RequestQueue(int defaultCoreNums) {
        this.defaultCoreNums = defaultCoreNums;
    }


    public void addRequest(BitmapRequest request) {
        if (!mRequestQueue.contains(request)) {
            request.serialNum = this.generateSeriaNumber();
            mRequestQueue.add(request);
        } else {
            L.d("请求队列中已经有了", request);
        }
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public BlockingQueue<BitmapRequest> getAllRequests() {
        return mRequestQueue;
    }

    private int generateSeriaNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }


}
