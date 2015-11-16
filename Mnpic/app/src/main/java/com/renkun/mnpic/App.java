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
    public static boolean isShowAD;
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
