package com.android.zj.ai.utils;

import android.app.ActivityManager;
import android.content.pm.IPackageDataObserver;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by zijian on 16/12/8.
 */

public class CacheUtil {
    private static final String TAG = "CacheUtil";

    /**
     * 删除缓存文件
     * <p>
     * android, /Android/data/.class
     * CacheTime.dat, /Android/data/cache
     * UnPackage.dat, /Android/data/cache
     * 1612011518.dat, /Android/data/cache/popCache
     */
    public static void delCacheFile() {
        String path = getSDPath();
        deleteFile(new File(path + "/Android/data/cache"), false);
        deleteFile(new File(path + "/Android/data/.class"), false);
        deleteFile(new File(path + "/Android/data/.um"), false);
        deleteFile(new File(path + "/Android/data/.nomedia"));
        deleteFile(new File(path + "/Android/data/com.chengzj.zd/cache/.nomedia"));
        deleteFile(new File(path + "/download"), false);
    }

    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    private static void deleteFile(File dir) {
        if (!dir.isDirectory()) {
            System.gc();    //加上确保文件能删除，不然可能删不掉
            dir.delete();
        } else {
            deleteFile(dir, false);
        }
    }

    //递归删除文件夹
    private static void deleteFile(File dir, boolean delDir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                System.gc();    //加上确保文件能删除，不然可能删不掉
                file.delete();
            } else if (file.isDirectory()) {
                deleteFile(file, true); // 递规的方式删除文件夹
            }
        }
        if (delDir) {
            System.gc();    //加上确保文件能删除，不然可能删不掉
            dir.delete();
        }
    }
}
