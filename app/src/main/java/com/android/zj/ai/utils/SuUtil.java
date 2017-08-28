package com.android.zj.ai.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class SuUtil {

    private static Process process;

    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill(String packageName, boolean unInstall) {
        initProcess();
        killProcess(packageName);
        if(unInstall){
            unInstall(packageName);
        } else {
            clearCache(packageName);
            clearCache();
        }
        close();
    }

    /**
     * 初始化进程
     */
    public static void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static void clearCache(){
//        String dir = getSDPath();
        String dir = "/storage/emulated/legacy";
        clearCacheDir(dir +"/Android/data/cache", true);
        clearCacheDir(dir +"/Android/data/com.chengzj.zd/cache", true);
        clearCacheDir(dir +"/Android/data/.class", false);
        clearCacheDir(dir +"/Android/data/.um", false);
        clearCacheDir(dir +"/Android/data/.nomedia", false);
        clearCacheDir(dir +"/download", true);
    }

    /**
     * 清除缓存
     */
    private static void clearCacheDir(String dir, boolean mk) {
        OutputStream out = process.getOutputStream();
        String cmd = "rm -r -f "+dir+" \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mk){
            mkdir(dir);
        }
    }

    private static void mkdir(String dir){
        OutputStream out = process.getOutputStream();
        String cmd = "mkdir -p "+dir+" \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */
    private static void clearCache(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "pm clear " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束进程
     */
    private static void killProcess(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "am force-stop " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 卸载应用
     */
    private static void unInstall(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "pm uninstall " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     */
    private static void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}  