package com.android.zj.ai.utils;

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
        }
        close();
    }

    /**
     * 初始化进程
     */
    private static void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
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
}  