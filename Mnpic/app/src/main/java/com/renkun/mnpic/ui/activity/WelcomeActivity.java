package com.renkun.mnpic.ui.activity;

/**
 * Created by rk on 2015/10/22.
 */

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.renkun.mnpic.R;

public class WelcomeActivity extends Activity {

    SplashView splashView;
    Context context;
    View splash;
    RelativeLayout splashLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        // 初始化接口，应用启动的时候调用
        // 参数：appId, appSecret, 调试模式
        AdManager.getInstance(context).init("0a2c3972cef8990e", "e34d9f70f7c3c3a1", true);
        // 第二个参数传入目标activity，或者传入null，改为setIntent传入跳转的intent
        splashView = new SplashView(context, null);
        // 设置是否显示倒数
        splashView.setShowReciprocal(false);
        // 隐藏关闭按钮
        splashView.hideCloseBtn(false);

        Intent intent = new Intent(context, MainActivity.class);
        splashView.setIntent(intent);
        splashView.setIsJumpTargetWhenFail(true);

        splash = splashView.getSplashView();
        setContentView(R.layout.activity_welcom);
        splashLayout = ((RelativeLayout) findViewById(R.id.splashview));
        splashLayout.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        params.addRule(RelativeLayout.ABOVE, R.id.cutline);
        splashLayout.addView(splash, params);

        SpotManager.getInstance(context).showSplashSpotAds(context, splashView,
                new SpotDialogListener() {

                    @Override
                    public void onShowSuccess() {
                        splashLayout.setVisibility(View.VISIBLE);
                        splashLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pic_enter_anim_alpha));
                        Log.d("youmisdk", "展示成功");
                    }

                    @Override
                    public void onShowFailed() {
                        Log.d("youmisdk", "展示失败");
                    }

                    @Override
                    public void onSpotClosed() {
                        Log.d("youmisdk", "展示关闭");
                    }

                    @Override
                    public void onSpotClick() {
                        Log.i("YoumiAdDemo", "插屏点击");
                    }
                });

        // 2.简单调用方式
        // 如果没有特殊要求，简单使用此句即可实现插屏的展示
        // SpotManager.getInstance(context).showSplashSpotAds(context,
        // MainActivity.class);

    }

    // 请务必加上词句，否则进入网页广告后无法进去原sdk
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10045) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // 取消后退键
    }

    @Override
    protected void onResume() {

        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port
        }
    }

}

