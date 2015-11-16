package com.renkun.mnpic.ui.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.TgFeedFragment;


public class TgDetailsClassifyActivity extends AppCompatActivity {
    private  int CLASSIFY;
    private TextView title;
    private ImageView mImageView;
    private static final String titles[]={"性感美女","韩日美女","丝袜美腿","美女照片",
            "美女写真","清纯美女","性感车模"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_classify);
        title= (TextView) findViewById(R.id.title);
        mImageView= (ImageView) findViewById(R.id.fabBtn);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CLASSIFY=getIntent().getIntExtra("position",1);
        title.setText(titles[CLASSIFY-1]);
        setDefaultFragment();

    }
    private void setDefaultFragment()
    {
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        TgFeedFragment feedFragment=new TgFeedFragment(CLASSIFY,2);
        transaction.replace(R.id.fragment_details,feedFragment);
        transaction.commit();
    }
    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();

    }

}
