package com.example.nw.myimageloader.core;

import com.example.nw.myimageloader.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;

/**
 * Created by nw on 17/7/20.
 */

public class RequestDispatcher extends Thread {

    private final BlockingQueue<BitmapRequest> queue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> queue) {
        this.queue = queue;

    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                BitmapRequest take = queue.take();
                if (take.isCancel) {
                    continue;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            throw new IllegalArgumentException("the uri is wrong" + uri);
        }
    }
}
