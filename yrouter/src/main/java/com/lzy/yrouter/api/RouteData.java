package com.lzy.yrouter.api;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/20 11:04
 **/
public class RouteData {
    private String format;
    private Class activity;

    public RouteData(String format, Class activity) {
        this.format = format;
        this.activity = activity;
    }

    public Class getActivity() {
        return activity;
    }

    public boolean isHttp() {
        String low = format.toLowerCase();
        return low.startsWith("http://") || low.startsWith("https://");
    }
}
