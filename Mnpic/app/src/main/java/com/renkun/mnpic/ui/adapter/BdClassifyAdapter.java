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
import com.renkun.mnpic.data.Config;
import com.renkun.mnpic.util.Screenutil;

/**
 * Created by rk on 2015/10/13.
 */
public class BdClassifyAdapter extends ArrayAdapter {
    private Resources mResources;
    private LayoutInflater mLayoutInflater;
    private GridView mGridView;
    private Context mContext;
    //屏幕宽高
    private int widthPixels;
    private int heightPixels;
    //分类图片
    public static final int pic[]={
            R.drawable.bd0,R.drawable.bd1,R.drawable.bd2,R.drawable.bd3,
            R.drawable.bd4,R.drawable.bd5,R.drawable.bd6,R.drawable.bd7,
            R.drawable.bd8,R.drawable.bd9,R.drawable.bd10,R.drawable.bd11,
            R.drawable.bd12,R.drawable.bd13,R.drawable.bd14};
    //分类标题
    public  String title1[]={"小清新","甜素纯","清纯","校花","唯美","气质","嫩萝莉","时尚",
                                        "长发","可爱","古典美女","素颜","非主流","短发","高雅大气很有范"};
    public   String title[]={"小清新","甜素纯","清纯","校花","气质","嫩萝莉",
            "长发","可爱","古典美女","素颜","非主流","短发"};
    public BdClassifyAdapter(Context context, int resource) {
        super(context, resource);
        mContext=context;
        //图片宽高比3：4
        widthPixels= Screenutil.getScreenHeightANDheight(context)[0];
        heightPixels= (int) (widthPixels*1.4);
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (Config.isSb){title=title1;}

    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){

            convertView = mLayoutInflater.inflate(R.layout.fragment_bd_classify_item, null);
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
