package com.renkun.mnpic.module;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rk on 2015/10/23.
 */
public class BDpic {
    public String tag1;
    public String tag2;
    public int totalNum;
    public int start_index;
    public int return_number;
    public ArrayList<DATA> data;
    //图片信息
    public static class DATA {
        public long id;
        public String image_url;//原图
//        public int image_width;
//        public int image_height;

        public String thumbnail_url;//小图
//        public int thumbnail_width;
//        public int thumbnail_height;

        public String thumb_large_url;//大图
//        public int thumb_large_width;
//        public int thumb_large_height;

    }
    public static ContentValues[] getContentValues(BDpic bDpic){
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (BDpic.DATA data : bDpic.data) {
            if (data.id==0)continue;
            ContentValues values = new ContentValues();
            values.put("id", data.id);
            values.put("image_url", data.image_url);
            values.put("thumbnail_url", data.thumbnail_url);
            values.put("thumb_large_url", data.thumb_large_url);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];

        return contentValues.toArray(valueArray);
    }
}
