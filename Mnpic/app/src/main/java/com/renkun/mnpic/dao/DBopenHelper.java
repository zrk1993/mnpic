package com.renkun.mnpic.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rk on 2015/10/10.
 */
public class DBopenHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DB_NAME = "mnpic.db";
    // 数据库版本
    private static final int VERSION = 1;

    /**
     * 最新图集的表
     * http://www.tngou.net/tnfs/api/news?id=10&rows=10&classify=1提供的 图片内容
     * 图片分类所对应的表
     * id是唯一值，根据它，获得图集
     * galleryclass图集所属类别
     * time发布时间
     */
    public static final String PIC_NEWEST ="create table pic_newest("
            +"_id integer primary key autoincrement,"
            +"galleryclass integer,"
            +"id integer unique,"
            +"img,"
            +"size integer,"
            +"count,"
            +"time,"
            +"title)";
    /**
     *
     * 百度图片
     */
    public static final String PIC_baidu ="create table pic_baidu("
            +"_id integer primary key autoincrement,"
            +"id integer unique,"
            +"image_url,"
            +"thumbnail_url,"
            +"thumb_large_url)";

    /**
     * 随机的壁纸
     * 图片分类所对应的表
     * id是唯一值，根据它，获得图集
     * galleryclass图集所属类别
     * time发布时间
     */
    public static final String PIC_TgClassify ="create table pic_classify("
            +"_id integer primary key autoincrement,"
            +"galleryclass integer,"
            +"id integer unique,"
            +"img,"
            +"size integer,"
            +"count,"
            +"time,"
            +"title)";

    public DBopenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PIC_NEWEST);
        db.execSQL(PIC_baidu);
        db.execSQL(PIC_TgClassify);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
