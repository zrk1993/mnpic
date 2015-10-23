package com.renkun.mnpic.ui.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.BaiduFragment;
import com.renkun.mnpic.ui.fragment.FeedFragment;

public class DetailsClassifyActivity extends AppCompatActivity {
    private  String CLASSIFY;
    private TextView title;
    //百度图片URL参数
    private int pn;
    private int rn;
    private String tag1;
    private String tag2;
    private String flags;
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
        rn=15;
        tag1="美女";
        tag2="全部";
        flags=CLASSIFY;
        setDefaultFragment();

    }
    private void setDefaultFragment()
    {
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        BaiduFragment baiduFragment=new BaiduFragment(pn,rn,tag1,tag2,flags);
        transaction.replace(R.id.fragment_details,baiduFragment);
        transaction.commit();
    }

}
