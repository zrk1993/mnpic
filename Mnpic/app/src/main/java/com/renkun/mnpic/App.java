package com.renkun.mnpic;

import android.app.Application;
import android.content.Context;


/**
 * Created by rk on 2015/10/11.
 */
public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
    public static Context getContext() {
        return sContext;
    }
}
