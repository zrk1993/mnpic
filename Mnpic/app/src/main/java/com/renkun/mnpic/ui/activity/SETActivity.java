package com.renkun.mnpic.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.fragment.SettingsFragment;



public class SETActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private SettingsFragment mSettingsFragment;
    private Button mButtonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set);
        mButtonBack= (Button) findViewById(R.id.back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSettingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction().replace(R.id.set_fragment, mSettingsFragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            finish();
            return true;
        }else
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }




}
