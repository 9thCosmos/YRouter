package com.lzy.yrouter.test

import android.app.Application
import com.lzy.yrouter.api.RouterCenter

/**
 * @author Lizhengyu
 * @description
 * @date 2019/5/24 11:18
 **/
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RouterCenter.openDebug()
        RouterCenter.init("lzy", this.applicationContext)
    }
}