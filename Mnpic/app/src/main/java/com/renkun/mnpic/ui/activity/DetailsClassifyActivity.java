package com.renkun.mnpic.ui.activity;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.FeedFragment;

public class DetailsClassifyActivity extends AppCompatActivity {
    private  int CLASSIFY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_classify);
        CLASSIFY=getIntent().getIntExtra("position",1);
        setDefaultFragment();

    }
    private void setDefaultFragment()
    {
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        FeedFragment feedFragment=new FeedFragment(CLASSIFY,2);
        transaction.replace(R.id.fragment_details,feedFragment);
        transaction.commit();
    }

}
