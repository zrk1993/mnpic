package com.renkun.mnpic.ui.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.appx.BDInterstitialAd;
import com.baidu.mobstat.StatService;
import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.BdFragment;

public class DetailsClassifyActivity extends AppCompatActivity {
    private String CLASSIFY;
    private TextView title;
    private static String TAG = "AppX_Interstitial";
    private BDInterstitialAd appxInterstitialAdView;
    //百度图片URL参数
    private int pn;
    private int rn;
    private String tag1;
    private String tag2;
    private String flags;
    android.support.v4.app.FragmentManager fm;
    public LinearLayout mLinearLayout;
    public ImageButton mButCollect;
    public ImageButton mButWrallper;
    private ImageView mBack;
    public Fragment mContent;//当前显示的fragment

    private Handler mHandler;
    private Runnable mRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_classify);
        title = (TextView) findViewById(R.id.title);
        mBack = (ImageView) findViewById(R.id.fabBtn);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLinearLayout = (LinearLayout) findViewById(R.id.img_but);
        mButCollect = (ImageButton) findViewById(R.id.but_collect);
        mButWrallper = (ImageButton) findViewById(R.id.set_wrallper);
        CLASSIFY = getIntent().getStringExtra("classify");
        title.setText(CLASSIFY);
        mHandler=new Handler();
        //百度图片参数
        pn = 0;
        rn = 16;
        tag1 = "美女";
        tag2 = CLASSIFY;
        flags = "全部";
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            mContent = new BdFragment(pn, rn, tag1, tag2, flags);
            transaction.add(R.id.fragment_details, mContent);
            transaction.commit();
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            @SuppressLint("CommitTransaction") FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.default_anim_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fragment_details, to)
                        .addToBackStack(null).commit(); // 隐藏当前的fragment，add下一个到Activity中
                mLinearLayout.setVisibility(View.VISIBLE);
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void initAD(){
        appxInterstitialAdView = new BDInterstitialAd(this,
                "W25kv3YNB07D09PS2gF9FCdpxgbtdV4N", "BT6pAg9wXtRKRGL6GUGXr2iy");
        // 设置插屏广告行为监听器
        appxInterstitialAdView.setAdListener(new BDInterstitialAd.InterstitialAdListener() {

            @Override
            public void onAdvertisementDataDidLoadFailure() {
                Log.e(TAG, "load failure");
            }

            @Override
            public void onAdvertisementDataDidLoadSuccess() {
                Log.e(TAG, "load success");
            }

            @Override
            public void onAdvertisementViewDidClick() {
                Log.e(TAG, "on click");
            }

            @Override
            public void onAdvertisementViewDidHide() {
                Log.e(TAG, "on hide");
            }

            @Override
            public void onAdvertisementViewDidShow() {
                Log.e(TAG, "on show");
            }

            @Override
            public void onAdvertisementViewWillStartNewIntent() {
                Log.e(TAG, "leave");
            }

        });

        mRunnable=new Runnable() {
            @Override
            public void run() {
                if (appxInterstitialAdView.isLoaded()) {
                    appxInterstitialAdView.showAd();
                } else {
                    Log.i(TAG, "AppX Interstitial Ad is not ready");
                    appxInterstitialAdView.loadAd();
                }
            }
        };

        mHandler.postDelayed(mRunnable, 5000);
    }

    public void onResume() {
        super.onResume();
        initAD();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
         */
        StatService.onResume(this);
    }

    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
         */
        StatService.onPause(this);

    }

}
