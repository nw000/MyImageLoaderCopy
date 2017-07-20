package com.example.nw.myimageloader.policy;

import com.example.nw.myimageloader.request.BitmapRequest;

/**
 * Created by nw on 17/7/20.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request2.serialNum - request1.serialNum;
    }
}
