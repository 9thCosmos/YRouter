package com.lzy.yrouter.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.lzy.yrouter.annotation.Route;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/12 10:14
 **/
@Route({"login/main", "login"})
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserInfo info = (UserInfo) getIntent().getSerializableExtra("user");
        Log.e("lzy", info.getName());
    }
}
