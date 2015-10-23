package com.renkun.mnpic.data;

/**
 * Created by rk on 2015/10/11.
 */
public class Api {
    //img字段返回的是不完整的图片路径src，
    public static final String TNPIC_http=
            "http://tnfs.tngou.net/image";
    //取得图片分类，可以通过分类id取得热词列表
    public static final String TNPIC_CLASSIFY=
            "http://www.tngou.net/tnfs/api/classify";
    //取得图片列表，也可以用分类id作为参数
    public static final String TNPIC_LIST=
            "http://www.tngou.net/tnfs/api/list?id=%1$d&rows=%2$d&page=%3$d";
    //取得热点图片详情，通过热点id取得该对应详细内容信息
    public static final String TNPIC_SHOW=
            "http://www.tngou.net/tnfs/api/show?id=%1$d";
    //取得最新的图片，通过id取得大于该id的图片
    public static final String TNPIC_NEWS=
            "http://www.tngou.net/tnfs/api/news?id=%1$d&rows=%2$d&classify=%3$d";
    // 天行数据,http://apistore.baidu.com/apiworks/servicedetail/720.html
    public static final String apikey= "e0f0fc16b45ab692c8341e91b0c3151c";
    public static final String TianXingUrl="http://apis.baidu.com/txapi/mvtp/meinv";

}
