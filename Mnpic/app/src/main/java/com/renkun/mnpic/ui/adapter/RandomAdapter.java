package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.module.RandomImg;
import com.renkun.mnpic.ui.activity.WebViewActivity;
import com.renkun.mnpic.util.ColorUtil;
import com.renkun.mnpic.util.Screenutil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by rk on 2015/10/12.
 */

public class RandomAdapter extends ArrayAdapter {
    private Resources mResources;
    private LayoutInflater mLayoutInflater;
    private GridView mGridView;
    private Context mContext;
    private ArrayList<RandomImg.Picture_List> list;
    //屏幕宽高
    private int widthPixels;
    private int heightPixels;


    public RandomAdapter(Context context, int resource,ArrayList<RandomImg.Picture_List> list) {
        super(context, resource);
        mContext = context;
        this.list=list;
        mResources=context.getResources();
        //图片宽高比3：4
        widthPixels = Screenutil.getScreenHeightANDheight(context)[0];
        heightPixels = (int) (widthPixels * 1.4);
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.fragment_random_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mImageView.setBackgroundColor(
                mResources.getColor(ColorUtil.getColor()));
        Picasso.with(mContext).load(list.get(position).picUrl)
                .into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, WebViewActivity.class);
                intent.setPackage(mContext.getPackageName());
                intent.putExtra("url",list.get(position).url);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class Holder {

        ImageView mImageView;
        TextView mTextView;

        public Holder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.item_pic);
            mTextView = (TextView) view.findViewById(R.id.item_text);
        }
    }
}
