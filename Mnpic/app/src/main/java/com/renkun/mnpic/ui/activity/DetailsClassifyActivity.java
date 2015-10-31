package com.renkun.mnpic.ui.activity;


import android.annotation.SuppressLint;
import android.content.Context;
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

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.BdFragment;

import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

public class DetailsClassifyActivity extends AppCompatActivity {
    private  String CLASSIFY;
    private TextView title;
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

    //广告
    public  boolean isShowYM;//插屏广告是否展示了
    @Override
    public void onBackPressed() {
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_classify);
        title= (TextView) findViewById(R.id.title);
        mBack= (ImageView) findViewById(R.id.fabBtn);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLinearLayout= (LinearLayout) findViewById(R.id.img_but);
        mButCollect= (ImageButton) findViewById(R.id.but_collect);
        mButWrallper= (ImageButton) findViewById(R.id.set_wrallper);
        CLASSIFY=getIntent().getStringExtra("classify");
        title.setText(CLASSIFY);
        //百度图片参数
        pn=0;
        rn=16;
        tag1="美女";
        tag2=CLASSIFY;
        flags="全部";
        fm=getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            mContent=new BdFragment(pn,rn,tag1,tag2,flags);
            transaction.add(R.id.fragment_details,mContent);
            transaction.commit();
            initYOUMI(this);
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

    private void initYOUMI(final Context context){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showYM(DetailsClassifyActivity.this);
            }
        }, 6000);

    }
    private void showYM(Context context){
        if (!isShowYM)
            SpotManager.getInstance(context).showSpotAds(context, new SpotDialogListener() {
                @Override
                public void onShowSuccess() {
                    isShowYM=true;
                    Log.i("YoumiSdk", "onShowSuccess");
                }
                @Override
                public void onShowFailed() {
                    isShowYM=false;
                    Log.i("YoumiSdk", "onShowFailed");
                }

                @Override
                public void onSpotClosed() {
                    isShowYM=false;
                    Log.e("YoumiSdk", "closed");
                }

                @Override
                public void onSpotClick() {
                    Log.i("YoumiSdk", "插屏点击");
                }
            });
    }
}
