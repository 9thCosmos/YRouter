package com.lzy.testmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lzy.yrouter.annotation.Route

/**
 * @author Lizhengyu
 * @description
 * @date 2019/5/24 13:44
 **/
@Route("mine/mine")
class MineActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)
    }
}