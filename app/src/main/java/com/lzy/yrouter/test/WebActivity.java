package com.lzy.yrouter.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lzy.yrouter.api.RouterConstants;
import com.lzy.yrouter.annotation.Route;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/12 16:15
 **/
@Route("web")
public class WebActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.web);

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);//启用JS
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        //mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        //在js中调用本地java方法
        //mWebView.addJavascriptInterface(new JsInterface(), "WebView");


        String url = getIntent().getStringExtra(RouterConstants.WEB_PAGE_URL);
        mWebView.loadUrl(url);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            /*if (s != null && s.startsWith("ellabook2://")) {
                Router.parse(s).query("fromWeb", true).go(WebActivity.this);
                return true;
            }*/
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }
    };
}
