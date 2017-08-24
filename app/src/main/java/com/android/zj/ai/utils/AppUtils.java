package com.android.zj.ai.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.zj.ai.MyApplication;
import com.android.zj.ai.service.SmartRobotService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 *
 * Created by zijian.cheng on 2017/8/24.
 */

public class AppUtils {
    private static final String TAG = "AppUtils";

    /**
     * 开启应用
     */
    public static void openApp() {
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.android.zj.vary", "com.android.zj.vary.activity.MainActivity");
        MyApplication.getApplication().startActivity(intent);
    }

    /**
     * 开启应用
     */
    public static void openApp2() {
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        MyApplication.getApplication().startActivity(intent);
    }

    // To check if service is enabled
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + SmartRobotService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    public static boolean Ping(String str) {
        boolean resault = false;
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 15 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            int times = 0;
            String temp;
            int count = 0;
            while ((line = in.readLine()) != null){
                buffer.append(line);
                Log.i(TAG, "line:" + line);
                if(line.contains("time=")){
                    temp = line.split("time=")[1];
                    String time = temp.replace("ms","").trim();
                    if(!TextUtils.isEmpty(time)){
                        try{
                            if(time.contains(".")){
                                time = time.substring(0, time.indexOf("."));
                            }
                            times += Integer.valueOf(time);
                        } catch (Exception e){}
                    }
                    count ++;
                }
            }
            Log.i(TAG, "times:" + times + ", count:" + count);

            if(times > 0){
                int mean = times / count;
                if (mean > 500) {//当平均延迟大于 100ms 时，就弃用
                    Log.i(TAG, "error : 延迟太大了: " + mean + " ms");
                    return false;
                }
                Log.i(TAG, "info : 延迟: " + mean + " ms");
            }
            resault = status == 0;
        } catch (IOException e) {
            Log.i(TAG, "error : IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.i(TAG, "error : InterruptedException: " + e.getMessage());
            e.printStackTrace();
        }

        return resault;
    }

    public static boolean Ping2(String str) {
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            //p = Runtime.getRuntime().exec("ping -c 30  " + str);
            p= Runtime.getRuntime().exec(new String[]{"su", "-c", "ping -c 3 " + str});
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

}
