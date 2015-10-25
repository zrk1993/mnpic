package com.renkun.mnpic.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renkun.mnpic.R;
import com.renkun.mnpic.module.BDpic;
import com.renkun.mnpic.ui.adapter.BdFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by rk on 2015/10/24.
 */
public class BdFragmentClik extends Fragment{
    private ArrayList<BDpic.DATA> mDATAArrayList;
    private int position;
    private FragmentActivity mActivity;
    private ViewPager mViewPager;
    private BdFragmentPagerAdapter mBdFragmentPagerAdapter;
    public BdFragmentClik(ArrayList<BDpic.DATA> datas,int position) {
        mDATAArrayList=datas;
        this.position=position;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bd_click, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.photo_pager);
        mBdFragmentPagerAdapter=new BdFragmentPagerAdapter(mActivity,mDATAArrayList);
        mViewPager.setAdapter(mBdFragmentPagerAdapter);
        mViewPager.setCurrentItem(position);
        return view;
    }
}
