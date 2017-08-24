package com.android.zj.ai;

import android.app.Application;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zijian.cheng on 2017/5/8.
 */

public class MyApplication extends Application {

    private final static String TAG = MyApplication.class.getSimpleName();

    /**
     * Application实例
     */
    protected static MyApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static MyApplication getApplication() {
        return sApplication;
    }
}
