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

    public ClassifyAdapter(Context context, int resource) {
        super(context, resource);
        mContext=context;
    }

    @Override
    public int getCount() {
        return 8;
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
        Uri uri = Uri.parse("http://tnfs.tngou.net/image/ext/150714/e76407c9a23da57a0f30690aa7917f3e.jpg");

        holder.mImageView.setAspectRatio(0.75f);
        holder.mImageView.setImageURI(uri);
        return convertView;
    }

    static class Holder{

        SimpleDraweeView mImageView;

        public Holder(View view){
            mImageView= (SimpleDraweeView) view.findViewById(R.id.item_pic);

        }
    }
}
