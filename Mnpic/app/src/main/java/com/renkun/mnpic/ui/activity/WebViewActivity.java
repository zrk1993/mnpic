package com.renkun.mnpic.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renkun.mnpic.R;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.Gallery;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);
        mWebView= (WebView) findViewById(R.id.web_view);

        String url=getIntent().getStringExtra("url");
        initWebView();

        mWebView.loadUrl(url);


    }
    private void initWebView(){
        //用webview加载url
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webSettings =   mWebView .getSettings();
        //支持javascrip
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");
        //打开页面时， 自适应屏幕
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        //便页面支持缩放：
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按返回键时， 不退出程序而是返回上一浏览页面：
        if ((keyCode == KeyEvent.KEYCODE_BACK) &&   mWebView .canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void loadData(String url) {

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        Log.d("http", request.urlString());
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //解析字符串
                final String s = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadData(s,"text/html", "utf-8");
                    }
                });

            }
        });

    }

}
