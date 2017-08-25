package com.android.zj.ai.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.android.zj.ai.utils.AppUtils;
import com.android.zj.ai.utils.SuUtil;

import java.util.List;
import java.util.Random;

/**
 *
 * Created by zijian.cheng on 2017/8/23.
 */
public class SmartRobotService extends AccessibilityService {
    public static final String TAG = "SmartRobotService";
    public static Random localRandom = new Random();
    private String installApkPackName;
    private AccessibilityNodeInfo accessibilityNodeInfo;

    public Handler mHandler = new Handler();
    @Override
    protected void onServiceConnected() {//辅助服务被打开后 执行此方法
        super.onServiceConnected();
        Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
    }

    /**
     * 开启广告软件
     */
    private Runnable mOpenAdApp = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "openApp2");
            AppUtils.openApp2();//com.wzcy.vibrator.StartupActivity, 32， com.chengzj.zd
        }
    };

    /**
     * 更新VPN
     */
    private Runnable mUpdateProxy = new Runnable() {
        @Override
        public void run() {
            clickButton("com.android.zj.vary:id/update_proxy", "android.widget.Button");
        }
    };

    private void clickButton(String id, String type){
        try{
            List<AccessibilityNodeInfo> button = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (AccessibilityUtils.checkNodeInfoType(button, type)) {
                AccessibilityUtils.performClick(button.get(0));
            }
        } catch (NullPointerException e){
            ;
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "eventType：" + accessibilityEvent.getClassName().toString() + ", " + accessibilityEvent.getEventType() +"， "+accessibilityEvent.getPackageName());

        accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }
        //进入首页
        if(accessibilityEvent.getPackageName().equals("com.android.zj.ai")){
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                String toastMsg = (String) accessibilityEvent.getText().get(0);
                if (!TextUtils.isEmpty(toastMsg)) {
                    //app安装成功
                    String containsStr = "<<安装>>package:";
                    if (toastMsg.contains(containsStr)) {
                        Log.e(TAG, "应用安装完成了");
                        installApkPackName = toastMsg.replace(containsStr, "");
                        if(!installApkPackName.startsWith("com.android.zj")){
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "kill app:" + installApkPackName);
                                    SuUtil.kill(installApkPackName);
                                    mHandler.postDelayed(mOpenAdApp, 1500);
                                }
                            }, 5000 + localRandom.nextInt(5000));
                        }
                    }
                }
            }
        } else if(accessibilityEvent.getPackageName().equals("com.android.zj.vary")){
            if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.zj.vary.activity.MainActivity", 32)) {
                mHandler.postDelayed(mUpdateProxy, 2000);
            } else if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                try {
                    String toastMsg = (String) accessibilityEvent.getText().get(0);
                    if (!TextUtils.isEmpty(toastMsg)) {
                        if (toastMsg.contains("==vpn is ready==")) {
                            Log.e(TAG, "VPN开启成功");
                            mHandler.postDelayed(mOpenAdApp, 2500);
                        } else if (toastMsg.contains("disconnected")) {
                            Log.e(TAG, "VPN关闭");
                        }
                    }
                } catch (Exception e) {
                    ;
                }
            }
        } else if(accessibilityEvent.getPackageName().equals("com.android.packageinstaller")){
            if(AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.packageinstaller.PackageInstallerActivity", 32)) {
                //进入安装界面，自动安装
                clickButton("com.android.packageinstaller:id/ok_button", "android.widget.Button");
            } else if(AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.packageinstaller.PackageInstallerPermsEditor", 32)) {
                //安装完成，点击完成
                clickButton("com.android.packageinstaller:id/finish", "android.widget.Button");
                //com.android.packageinstaller:id/launch
            }
        } else if(accessibilityEvent.getPackageName().equals("com.lbe.security.miui")){
            if(AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.widget.LinearLayout", 32)) {
                //弹出授权页面，自动点击拒绝
                clickButton("com.lbe.security.miui:id/reject", "android.widget.Button");
            }
        } else if(accessibilityEvent.getPackageName().equals("com.chengzj.zd")){
            if(AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.view.View", 2048)) {
                //banner刷新了

            } else if(AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.app.Notification", 64)) {
                //下载进度
            }
        }

    }

    @Override
    public void onInterrupt() {//辅助服务被关闭 执行此方法
        mHandler.removeCallbacks(mUpdateProxy);
        Toast.makeText(this, "服务被中断啦", Toast.LENGTH_SHORT).show();
    }

}