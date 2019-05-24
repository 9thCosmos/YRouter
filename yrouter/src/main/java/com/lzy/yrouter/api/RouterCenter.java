package com.lzy.yrouter.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.TintContextWrapper;
import android.text.TextUtils;
import com.lzy.yrouter.api.utils.ClassUtils;
import com.lzy.yrouter.api.utils.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/20 11:51
 **/
public class RouterCenter {
    private static String SCHEME;// 只处理SCHEME://XX/XX开头的uri
    private static Map<String, RouteData> routeMap;
    private volatile static boolean debuggable = false;

    public static void init(String scheme, Context context) {
        SCHEME = scheme;
        routeMap = new HashMap<>();
        try {
            Set<String> routeMap = ClassUtils.getFileNameByPackageName(context, RouterConstants.ROUTE_PACKAGE);
            for (String className : routeMap) {
                Class<?> clz = Class.forName(className);
                Method method = clz.getDeclaredMethod(RouterConstants.ROUTE_LOAD_METHOD);
                method.invoke(clz.getConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void map(String format, Class<? extends Activity> activity) {
        RouteData route = new RouteData(format, activity);
        routeMap.put(format, route);
    }

    public static boolean open(Context context, String url) {
        return open(context, url, null);
    }

    public static boolean open(Context context, String url, int requestCode) {
        return open(context, url, null, requestCode);
    }

    public static boolean open(Context context, String url, Bundle data) {
        return open(context, url, data, -1);
    }

    private static boolean open(Context context, String url, Bundle data, int requestCode) {
        String low = url.toLowerCase();
        if (low.startsWith("http://") || low.startsWith("https://")) {
            if (data == null) {
                data = new Bundle();
            }
            data.putString(RouterConstants.WEB_PAGE_URL, url);

            return doOpen(context, "web", data, requestCode);
        } else {
            return open(context, Uri.parse(url), data, requestCode);
        }
    }

    public static boolean open(Context context, Uri uri) {
        return open(context, uri, null);
    }

    public static boolean open(Context context, Uri uri, int requestCode) {
        return open(context, uri, null, requestCode);
    }

    public static boolean open(Context context, Uri uri, Bundle data) {
        return open(context, uri, data, -1);
    }

    private static boolean open(Context context, Uri uri, Bundle data, int requestCode) {
        if (!SCHEME.equals(uri.getScheme())) {
            throw new RuntimeException("can not recognized" + uri.getScheme());
        }

        String queryData = uri.getQuery();
        if (!TextUtils.isEmpty(queryData)) {
            if (data == null) {
                data = new Bundle();
            }

            queryData = queryData.replaceAll("&", "=");
            String[] querys = queryData.split("=");
            for (int i = 0; i + 1 < querys.length; i = i + 2) {
                data.putString(querys[i], querys[i + 1]);
            }
        }

        return doOpen(context, uri.getAuthority() + uri.getPath(), data, requestCode);
    }

    private static boolean doOpen(Context context, String routePath, Bundle data, int requestCode) {
        if (routeMap.containsKey(routePath)) {
            RouteData route = routeMap.get(routePath);
            Intent intent = new Intent(context, route.getActivity());
            if (data != null) {
                intent.putExtras(data);
            }
            if (requestCode >= 0) {
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, requestCode);
                    return true;
                } else if (context instanceof TintContextWrapper) {
                    ((Activity) ((TintContextWrapper) context).getBaseContext()).startActivityForResult(intent, requestCode);
                    return true;
                } else {
                    throw new RuntimeException("can not startActivityForResult context " + context);
                }
            } else {
                context.startActivity(intent);
                return true;
            }
        } else {
            throw new RuntimeException("Find nothing for \"" + SCHEME + "://" + routePath + "\", do you declared it?");
        }
    }

    public static synchronized void openDebug() {
        debuggable = true;
        Logger.info("openDebug");
    }

    public static boolean debuggable() {
        return debuggable;
    }
}
