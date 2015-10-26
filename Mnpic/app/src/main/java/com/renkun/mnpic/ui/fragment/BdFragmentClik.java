package com.renkun.mnpic.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renkun.mnpic.R;
import com.renkun.mnpic.module.BDpic;
import com.renkun.mnpic.ui.activity.DetailsClassifyActivity;
import com.renkun.mnpic.ui.adapter.BdFragmentPagerAdapter;
import com.renkun.mnpic.util.WallpaperUtli;

import java.util.ArrayList;

/**
 * Created by rk on 2015/10/24.
 */
public class BdFragmentClik extends Fragment{
    public ArrayList<BDpic.DATA> mDATAArrayList;
    private int position;
    private DetailsClassifyActivity mActivity;
    public ViewPager mViewPager;
    private BdFragmentPagerAdapter mBdFragmentPagerAdapter;
    public BdFragmentClik(ArrayList<BDpic.DATA> datas,int position) {
        mDATAArrayList=datas;
        this.position=position;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= (DetailsClassifyActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bd_click, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.photo_pager);
        mBdFragmentPagerAdapter=new BdFragmentPagerAdapter(mActivity,mDATAArrayList);
        mViewPager.setAdapter(mBdFragmentPagerAdapter);
        mViewPager.setCurrentItem(position);
        mActivity.mLinearLayout.setVisibility(View.VISIBLE);
        mActivity.mLinearLayout.animate().alpha(1f).setDuration(300).start();
        setItemClick();//设置设置壁纸和收藏按钮

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.mLinearLayout.setVisibility(View.VISIBLE);
        mActivity.mLinearLayout.animate().alpha(1f).setDuration(300).start();
    }

    private void setItemClick(){
                mActivity.mButWrallper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mViewPager, "设置为壁纸吗？", Snackbar.LENGTH_LONG)
                        .setAction("是的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WallpaperUtli.setBitmap(getActivity(), getUrl(mDATAArrayList.get(mViewPager.getCurrentItem())));
                                Snackbar.make(mViewPager,"正在设置。。",Snackbar.LENGTH_LONG).show();

                            }
                        }).show();
            }
        });

        mActivity.mButCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mViewPager, "要保存图片吗？", Snackbar.LENGTH_LONG)
                        .setAction("是的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WallpaperUtli.savePic(getActivity(),
                                        getUrl(mDATAArrayList.get(mViewPager.getCurrentItem())), mDATAArrayList.get(mViewPager.getCurrentItem()).id+ ".png");
                                Snackbar.make(mViewPager,"正在保存。。",Snackbar.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }

    //优先返回da图的url
    private String getUrl(BDpic.DATA data){
        String url=data.image_url;
        if (url.startsWith("http://imgt"))
            url=data.thumbnail_url;
        if (url.startsWith("http://imgt"))
            url=data.thumb_large_url;
        Log.d("bd", url);
        return url;
    }

    @Override
    public void onPause() {
        mActivity.mLinearLayout.setVisibility(View.GONE);
        //隐藏动画
//        mActivity.mLinearLayout.animate().alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mActivity.mLinearLayout.setVisibility(View.GONE);
//            }
//        });

        super.onPause();
    }
}
