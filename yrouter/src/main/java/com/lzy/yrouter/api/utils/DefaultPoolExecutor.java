package com.lzy.yrouter.api.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/22 11:14
 **/
public class DefaultPoolExecutor {
    private volatile static DefaultPoolExecutor instance;
    private Executor mExecutor;

    private DefaultPoolExecutor() {
        mExecutor = Executors.newFixedThreadPool(5);
    }

    public static DefaultPoolExecutor getInstance() {
        if (null == instance) {
            synchronized (DefaultPoolExecutor.class) {
                if (null == instance) {
                    instance = new DefaultPoolExecutor();
                }
            }
        }
        return instance;
    }

    public static void execute(Runnable r) {
        getInstance().mExecutor.execute(r);
    }
}
