package com.example.bigyoung.diyview.utils;

import android.content.Context;

import com.example.bigyoung.diyview.views.LoadingPager;

/**
 * Created by BigYoung on 2017/4/25.
 * 处理网络连接结果
 */

public class ResultNetConnection {
    /**
     * 处理网络异常
     * @param context
     * @return
     */
    public static int resultNetFailed(Context context){
        MyToast.show(context,"网络异常");
        return LoadingPager.FAILED_STATE;
    }
}
