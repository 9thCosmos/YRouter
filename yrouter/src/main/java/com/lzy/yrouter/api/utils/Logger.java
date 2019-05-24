package com.lzy.yrouter.api.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/22 11:34
 **/
public class Logger {
    private static String defaultTag = "lzyrouter";
    private static boolean isShowLog = true;

    public static void setShowLog(boolean showLog) {
        isShowLog = showLog;
    }

    public static void debug(String tag, String message) {
        if (isShowLog) {
            Log.d(TextUtils.isEmpty(tag) ? defaultTag : tag, message);
        }
    }

    public static void debug(String message) {
        debug(defaultTag, message);
    }

    public static void info(String tag, String message) {
        if (isShowLog) {
            Log.i(TextUtils.isEmpty(tag) ? defaultTag : tag, message);
        }
    }

    public static void info(String message) {
        info(defaultTag, message);
    }

    public static void warning(String tag, String message) {
        if (isShowLog) {
            Log.w(TextUtils.isEmpty(tag) ? defaultTag : tag, message);
        }
    }

    public static void warning(String message) {
        warning(defaultTag, message);
    }

    public static void error(String tag, String message) {
        if (isShowLog) {
            Log.e(TextUtils.isEmpty(tag) ? defaultTag : tag, message);
        }
    }

    public static void error(String message) {
        error(defaultTag, message);
    }
}
