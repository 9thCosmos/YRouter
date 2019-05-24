package com.lzy.yrouter.test

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lzy.yrouter.annotation.Route
import com.lzy.yrouter.api.RouterCenter

@Route("main/main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.test).setOnClickListener {
            val info = UserInfo()
            info.couse = "couse"
            info.name = "name"
            val data = Bundle()
            data.putSerializable("user", info)
            RouterCenter.open(this@MainActivity, Uri.parse("lzy://login/main?name=123&key=1"), data)
        }


        findViewById<View>(R.id.web).setOnClickListener { RouterCenter.open(this@MainActivity, "https://baidu.com") }

        findViewById<View>(R.id.mine).setOnClickListener {
            RouterCenter.open(
                this@MainActivity,
                Uri.parse("lzy://mine/mine")
            )
        }
    }

}
