package com.renkun.mnpic.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.util.Screenutil;

/**
 * Created by rk on 2015/10/13.
 */
public class ClassifyAdapter extends ArrayAdapter {
    private Resources mResources;
    private LayoutInflater mLayoutInflater;
    private GridView mGridView;
    private Context mContext;
    //屏幕宽高
    private int widthPixels;
    private int heightPixels;
    //分类图片
    public static final int pic[]={
            R.mipmap.pic_1,R.mipmap.pic_2,R.mipmap.pic_3,R.mipmap.pic_4,
            R.mipmap.pic_5,R.mipmap.pic_6,R.mipmap.pic_7,R.mipmap.pic_1,
            R.mipmap.pic_2,R.mipmap.pic_3,R.mipmap.pic_4,R.mipmap.pic_4,
            R.mipmap.pic_5,R.mipmap.pic_6,R.mipmap.pic_7};
    //分类标题
    public static final String title[]={"小清新","甜素纯","清纯","校花","唯美","气质","嫩萝莉","时尚",
                                        "长发","可爱","古典美女","素颜","非主流","短发","高雅大气很有范"};
    public ClassifyAdapter(Context context, int resource) {
        super(context, resource);
        mContext=context;
        //图片宽高比3：4
        widthPixels= Screenutil.getScreenHeightANDheight(context)[0];
        heightPixels= (int) (widthPixels*1.4);
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){

            convertView = mLayoutInflater.inflate(R.layout.fragment_classify_item, null);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.mTextView.setText(title[position]);
        holder.mImageView.setImageResource(pic[position]);
//        Picasso.with(mContext).load(pic[position])
//                .resize(widthPixels,heightPixels)
//                .centerCrop()
//                .into(holder.mImageView);
        return convertView;
    }

    static class Holder{

        ImageView mImageView;
        TextView mTextView;

        public Holder(View view){
            mImageView= (ImageView) view.findViewById(R.id.item_pic);
            mTextView= (TextView) view.findViewById(R.id.item_text);
        }
    }
}
