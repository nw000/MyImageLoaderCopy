package com.example.nw.myimageloader.policy;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public interface LoadPolicy {
    public int compare(BitmapRequest request1, BitmapRequest request2);
}
