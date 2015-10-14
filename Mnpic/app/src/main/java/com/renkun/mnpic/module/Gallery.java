package com.renkun.mnpic.module;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**该类是网址
 * http://www.tngou.net/tnfs/api/news?id=10&rows=10&classify=1
 * 返回的数据
 * Created by rk on 2015/10/11.
 */
public class Gallery {
    public int total;
    public List<Pic_List> tngou;
    public static class Pic_List {
        public int count;//          访问数
        public int fcount;//          收藏数
        public int galleryclass;//          图片分类
        public int id;          //图片的唯一编号
        public String img;//          图库封面，的地址
        public String title;//          标题
        public long time;
        public int rcount;//           回复数
        public int size;//      图集的图片多少张
    }

    public static ContentValues[] getContentValues(Gallery gallery){
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (Gallery.Pic_List Pic_List : gallery.tngou) {
            ContentValues values = new ContentValues();
            values.put("id", Pic_List.id);
            values.put("galleryclass", Pic_List.galleryclass);
            values.put("img", Pic_List.img);
            values.put("count", Pic_List.count);
            values.put("time",Pic_List.time);
            values.put("size", Pic_List.size);
            values.put("title", Pic_List.title);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];

        return contentValues.toArray(valueArray);
    }

}
