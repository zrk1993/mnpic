package com.renkun.mnpic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.adapter.FragmentAdapter;
import com.renkun.mnpic.ui.fragment.BaiduFragment;
import com.renkun.mnpic.ui.fragment.ClassifyFragment;
import com.renkun.mnpic.ui.fragment.CollectFragment;
import com.renkun.mnpic.ui.fragment.HotFragment;
import com.renkun.mnpic.ui.fragment.RandomFragment;

import net.youmi.android.spot.SpotManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;
    //v4中的ViewPager控件
    private ViewPager mViewPager;
    private ImageView fabBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //初始化控件和布局
        initView();
        //初始化有米广告
        initYOUMI(this);
    }
    private void initYOUMI(Context context){
        SpotManager.getInstance(context).loadSpotAds();
        SpotManager.getInstance(context).setSpotOrientation(
                SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(context).setAnimationType(SpotManager.ANIM_ADVANCE);
        //SpotManager.getInstance(this).showSpotAds(this);
    }
    private void initView(){
        fabBtn= (ImageView) this.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new fabBtnClicklistener());
        //MainActivity的布局文件中的主要控件初始化
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("热门美女");
        titles.add("精品分类");
        titles.add("我的收藏");


        //初始化TabLayout的title
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        //初始化ViewPager的数据集

        List<Fragment> fragments = new ArrayList<>();
        HotFragment hotFragment=new HotFragment();
        ClassifyFragment classifyFragment=new ClassifyFragment();
        CollectFragment collectFragment=new CollectFragment();
        fragments.add(hotFragment);
        fragments.add(classifyFragment);
        fragments.add(collectFragment);



        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    private class fabBtnClicklistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this, SETActivity.class);
            intent.setPackage(getPackageName());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
