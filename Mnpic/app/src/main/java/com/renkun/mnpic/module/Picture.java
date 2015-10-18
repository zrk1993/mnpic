package com.renkun.mnpic.module;

import java.util.List;

/**
 * Created by rk on 2015/10/17.
 */
public class Picture {
    public int count;
    public int fcount;
    public int galleryclass;
    public int id;
    public String img;
    public int size;
    public long time;
    public String title;
    public List<Picture_List> list;
    public static class Picture_List {
        public String gallery;
        public int id;
        public String src;
    }
}
