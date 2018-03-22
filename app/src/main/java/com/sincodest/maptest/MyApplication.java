package com.sincodest.maptest;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
