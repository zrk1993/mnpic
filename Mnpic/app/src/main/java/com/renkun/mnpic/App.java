package com.renkun.mnpic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by rk on 2015/10/11.
 */
@SuppressWarnings("ALL")
public class App extends Application {
    private static Context sContext;
    private List<Activity> activitys = null;

    //广告
    public static final int ADdelay=15000;
    public static int numberAD=1;
    public static boolean isShowYM;//插屏广告是否展示了

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        activitys = new LinkedList<Activity>();
    }
    public static Context getContext() {
        return sContext;
    }
}
