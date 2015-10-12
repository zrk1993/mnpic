package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;

import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;


/**
 * Created by rk on 2015/10/12.
 */
public class PicListCursorAdapter extends CursorAdapter {
    private Resources mResources;
    private LayoutInflater mLayoutInflater;
    private GridView mGridView;

    public PicListCursorAdapter(Context context, GridView gridView) {
        super(context, null, false);
        mResources=context.getResources();
        mLayoutInflater=LayoutInflater.from(context);
        mGridView=gridView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.fragment_pic_item,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder=getHolder(view);
        Uri uri = Uri.parse(Api.TNPIC_http + cursor.getString(cursor.getColumnIndex("img")));
        holder.draweeView.setAspectRatio(0.75f);
        holder.draweeView.setImageURI(uri);

    }
    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }
    static class Holder{

        SimpleDraweeView draweeView;
        TextView mTextView;
        public Holder(View view){
            draweeView= (SimpleDraweeView) view.findViewById(R.id.item_pic);
            mTextView= (TextView) view.findViewById(R.id.item_text);
        }
    }
}
