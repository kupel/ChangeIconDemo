package com.example.hasee.changeicondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by hasee on 2017/11/6.
 */

public class WebViewActivity extends Activity{
    private WebView mMainWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mMainWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        com.tencent.smtt.sdk.WebSettings setting = mMainWebView.getSettings();
        setting.setDomStorageEnabled(true);
        setting.setRenderPriority(com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH);
        setting.setAppCacheEnabled(false);// 一定不能设置为缓存
        setting.setJavaScriptEnabled(true);
        setting.setAllowFileAccess(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);


        mMainWebView.loadUrl("https://app.shopin.cn/cms/index-v3.html");

    }

}
