package com.example.bigyoung.diyview.base;

import android.app.Application;
import android.content.Context;
import android.os.*;
import android.support.v4.util.LruCache;

import java.util.HashMap;
import java.util.Map;

/**

 * 描述	      易错点:需要在清单文件里面进行配置
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;
    //用于存储联网返回的json string
    private static Map<String,String> jsonStringCacheMap=new HashMap<String,String>();

    public static Map<String, String> getJsonStringCacheMap() {
        return jsonStringCacheMap;
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程里面的创建的一个hanlder
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 得到主线程的线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {//程序的入口方法
        //上下文
        mContext = getApplicationContext();

        //主线程的Handler
        mMainThreadHandler = new Handler();

        //主线程的线程id
        mMainThreadId = android.os.Process.myTid();
        /**
         myTid:Thread
         myPid:Process
         myUid:User
         */
        super.onCreate();
    }
}
