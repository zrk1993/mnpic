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
//    /**
//     * 图片分类
//     */
//    public static final String CREATE_GALLERYCLASS ="create table pic_classification ("
//            +"_id integer primary key autoincrement,"
//            +"description,"
//            +"id,"
//            +"keywords,"
//            +"name,"
//            +"seq,"
//            +"title)";

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
     * http://www.tngou.net/tnfs/api/news?id=10&rows=10&classify=1提供的 图片内容
     * 图片分类所对应的表
     * id是唯一值，根据它，获得图集
     * galleryclass图集所属类别
     * time发布时间
     */
    public static final String PIC_TNGOU ="create table pic_classify("
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
        db.execSQL(PIC_TNGOU);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
