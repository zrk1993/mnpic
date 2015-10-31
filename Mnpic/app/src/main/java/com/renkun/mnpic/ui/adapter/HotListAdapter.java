package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.util.ColorUtil;
import com.renkun.mnpic.util.Screenutil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by rk on 2015/10/17.
 */
public class HotListAdapter extends CursorAdapter {
    private Context mContext;
    private ListView mListView;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat format=new SimpleDateFormat("MM-dd");
    //屏幕宽高
    private int widthPixels;
    private int heightPixels;
    private Resources mResources;

    public HotListAdapter(Context context, ListView listView) {
        super(context, null, false);
        mResources=context.getResources();
        mLayoutInflater=LayoutInflater.from(context);
        mListView=listView;
        //图片宽高比3：4
        widthPixels= Screenutil.getScreenHeightANDheight(context)[0];
        heightPixels= (int) (widthPixels*1.4);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.fragment_hot_item,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder=getHolder(view);
        holder.mImageView.setBackgroundColor(
                mResources.getColor(ColorUtil.getColor()));

        Picasso.with(context).load(Api.TNPIC_http + cursor.getString(cursor.getColumnIndex("img")))
                .resize(widthPixels,widthPixels)
                .centerInside()
                .into(holder.mImageView);
        String[] strings=format.format(cursor.getLong(cursor.getColumnIndex("time"))).split("-");
        holder.mTextViewDay
                .setText(strings[1]);
        holder.mTextViewMonth
                .setText(strings[0]+"月");



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

        ImageView mImageView;
        TextView mTextViewMonth;
        TextView mTextViewDay;

        public Holder(View view){
            mImageView= (ImageView) view.findViewById(R.id.item_pic);
            mTextViewDay= (TextView) view.findViewById(R.id.item_time_day);
            mTextViewMonth= (TextView) view.findViewById(R.id.item_time_month);
        }
    }
}
