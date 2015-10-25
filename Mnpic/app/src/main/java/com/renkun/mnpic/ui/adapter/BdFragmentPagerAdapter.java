package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.module.BDpic;
import com.renkun.mnpic.module.Picture;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by rk on 2015/10/24.
 */

public class BdFragmentPagerAdapter extends PagerAdapter {
    private ArrayList<BDpic.DATA> mDATAArrayList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public BdFragmentPagerAdapter(Context context,ArrayList<BDpic.DATA> DATAArrayList) {
        mDATAArrayList=DATAArrayList;
        mContext=context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        if (mDATAArrayList != null && mDATAArrayList.size() > 0) {
            return mDATAArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mLayoutInflater.inflate(R.layout.activity_photo_details_item,null);
        //PhotoView photoView = new PhotoView(container.getContext());
        PhotoView photoView= (PhotoView) view.findViewById(R.id.iv_photo);
        Picasso.with(mContext)
                .load(getUrl(mDATAArrayList.get(position)))
                .config(Bitmap.Config.ARGB_8888)
                .tag(mContext)
                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .into(photoView);
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    //优先返回小图的url
    private String getUrl(BDpic.DATA data){
        String url=data.thumb_large_url;
        if (url.startsWith("http://imgt"))
            url=data.thumbnail_url;
        if (url.startsWith("http://imgt"))
            url=data.image_url;
        Log.d("bd", url);
        return url;
    }

}
