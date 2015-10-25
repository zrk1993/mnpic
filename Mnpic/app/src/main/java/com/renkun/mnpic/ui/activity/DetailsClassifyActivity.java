package com.renkun.mnpic.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.BdFragment;

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

    public Fragment mContent;//当前显示的fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_classify);
        title= (TextView) findViewById(R.id.title);
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
        }

    }
    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.default_anim_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fragment_details, to)
                        .addToBackStack(null).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
