package com.renkun.mnpic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.adapter.FragmentAdapter;
import com.renkun.mnpic.ui.fragment.BdClassifyFragment;
import com.renkun.mnpic.ui.fragment.CollectFragment;
import com.renkun.mnpic.ui.fragment.HotFragment;
import com.renkun.mnpic.ui.fragment.TgClassifyFragment;

import net.youmi.android.spot.SpotManager;

import java.util.ArrayList;
import java.util.List;

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
        BdClassifyFragment classifyFragment=new BdClassifyFragment();
        TgClassifyFragment tgClassifyFragment=new TgClassifyFragment();
        fragments.add(hotFragment);
        fragments.add(tgClassifyFragment);
        fragments.add(classifyFragment);



        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
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

    /**
     * viewpager .切换动画
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean mIsExit;
    private void exit(){
        if (mIsExit){
            finish();
            System.exit(0);
        }
        else {
            mIsExit=true;
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    mIsExit=false;
                }
            }, 1000);

        }
    }
}
