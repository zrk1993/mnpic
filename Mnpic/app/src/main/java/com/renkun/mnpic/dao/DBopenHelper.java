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
     * 图片分类
     */
    public static final String CREATE_GALLERYCLASS ="create table Image_classification ("
            +"_id integer primary key autoincrement,"
            +"description,"
            +"id,"
            +"keywords,"
            +"name,"
            +"seq,"
            +"title)";

    /**
     * http://www.tngou.net/doc/gallery提供的 图片内容
     * 总共7个表，从pic_1到pic_7
     * id是唯一值，根据它，获得图集
     * galleryclass图集所属类别
     */
    public static final String PIC_TNGOU ="create table pic_%1$d ("
            +"_id integer primary key autoincrement,"
            +"galleryclass,"
            +"id,"
            +"img,"
            +"size,"
            +"count,"
            +"title)";

    public DBopenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG","数据库");
        db.execSQL(CREATE_GALLERYCLASS);
        for (int i=1;i<=7;i++){
            db.execSQL(String.format(PIC_TNGOU, i));
        }


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
