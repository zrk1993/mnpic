package com.renkun.mnpic.module;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rk on 2015/10/23.
 */
public class RandomImg {
    int code;
    String msg;
    ArrayList<Picture_List> mList;

    public static class Picture_List {
        public String title;
        public String description;
        public String picUrl;
        public String url;
    }

    public static ArrayList<Picture_List> getList(String s, int l) {
        ArrayList<Picture_List> mList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            for (int i = 0; i < l; i++) {
                JSONObject object=jsonObject.getJSONObject(String.valueOf(1));
                Picture_List picture_list=new Picture_List();
                picture_list.title=object.getString("title");
                picture_list.description=object.getString("description");
                picture_list.picUrl=object.getString("picUrl");
                picture_list.url=object.getString("url");
                mList.add(picture_list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
    //返回string，对应数据库insert的
    public static ContentValues[] getContentValues(String s,int l){
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            for (int i = 0; i < l; i++) {
                JSONObject object=jsonObject.getJSONObject(String.valueOf(1));
                ContentValues values = new ContentValues();

                values.put("title",object.getString("title"));
                values.put("description",object.getString("description"));
                values.put("picUrl",object.getString("picUrl"));
                values.put("url",object.getString("url"));

                contentValues.add(values);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues[] valueArray = new ContentValues[contentValues.size()];

        return contentValues.toArray(valueArray);
    }
}
