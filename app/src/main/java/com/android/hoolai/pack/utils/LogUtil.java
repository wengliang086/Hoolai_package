package com.android.hoolai.pack.utils;

import android.util.Log;

import com.android.hoolai.pack.GlobalContext;

public class LogUtil {

    private static String TAG = GlobalContext.TAG;

    public static void d(String msg) {
        Log.d(TAG, msg + "-" + Thread.currentThread().getName());
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        Log.e(TAG, msg, tr);
    }

    /**
     * ***************** 下面是带TAG方法
     *********************/

    public static void d(String tag, String msg) {
        Log.d(tag, msg + "-" + Thread.currentThread().getName());
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }
}
