package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.module.Picture;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewPagerAdapter extends PagerAdapter {
    private Picture mPicture;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public PhotoViewPagerAdapter(Context context,Picture picture) {
        mPicture=picture;
        mContext=context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        if (mPicture != null && mPicture.list.size() > 0) {
            return mPicture.list.size();
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
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_photo);
        Picasso.with(mContext)
                .load(Api.TNPIC_http+mPicture.list.get(position).src)
                .config(Bitmap.Config.ARGB_8888)
                .error(R.drawable.erro)
                .tag(mContext)
                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}