package com.renkun.mnpic.module;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rk on 2015/10/11.
 */
public class Gallery {
    public int total;
    public List<Pic_List> tngou;
    public static class Pic_List {
        public int id;
        public int galleryclass;//          图片分类
        public String title;//          标题
        public String img;//          图库封面
        public int count;//          访问数
        public int rcount;//           回复数
        public int fcount;//          收藏数
        public int size;//      图片多少张
    }

    public static ContentValues[] getContentValues(Gallery gallery){
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (Gallery.Pic_List Pic_List : gallery.tngou) {
            ContentValues values = new ContentValues();
            values.put("id", Pic_List.id);
            values.put("galleryclass", Pic_List.galleryclass);
            values.put("img", Pic_List.img);
            values.put("count", Pic_List.count);
            values.put("size", Pic_List.size);
            values.put("title", Pic_List.title);
            Log.d("TAG1",Pic_List.title);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];

        return contentValues.toArray(valueArray);
    }

}
