package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;

/**
 * Created by rk on 2015/10/13.
 */
public class ClassifyAdapter extends ArrayAdapter {
    private Resources mResources;
    private LayoutInflater mLayoutInflater;
    private GridView mGridView;
    private Context mContext;
    //分类图片
    private static final int pic[]={R.mipmap.pic_1,R.mipmap.pic_2,R.mipmap.pic_3,R.mipmap.pic_4,
            R.mipmap.pic_5,R.mipmap.pic_6,R.mipmap.pic_7};
    //分类标题
    private static final String title[]={"性感美女","韩日美女","丝袜美腿","美女照片",
                                            "美女写真","清纯美女","性感车模"};
    public ClassifyAdapter(Context context, int resource) {
        super(context, resource);
        mContext=context;
    }

    @Override
    public int getCount() {
        return 7;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            final LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_classify_item, null);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.mTextView.setText(title[position]);
        Uri uri = Uri.parse("res://com.renkun.mnpic/" + pic[position]);
        holder.mImageView.setAspectRatio(0.65f);
        holder.mImageView.setImageURI(uri);
        return convertView;
    }

    static class Holder{

        SimpleDraweeView mImageView;
        TextView mTextView;

        public Holder(View view){
            mImageView= (SimpleDraweeView) view.findViewById(R.id.item_pic);
            mTextView= (TextView) view.findViewById(R.id.item_text);
        }
    }
}
