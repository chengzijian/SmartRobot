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

//    public Handler mHandler2 = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == MSG_REQUEST_CLICK_BTN) {
//                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) msg.obj;
//                String id = (String) msg.getData().get("id");
//                String type = (String) msg.getData().get("type");
//
//                Log.e(TAG, "click btn, type:" + type);
//                if (type.equals("click")) {
//                    try {
//                        AccessibilityUtils.performClick(accessibilityNodeInfo);
//                    } catch (Exception e) {
//                        Log.e(TAG, "error -1:" + e.getMessage());
//                    }
//                } else if (type.equals("back")) {
//                    AccessibilityUtils.performBack(SmartRobotService.this);
//                } else {
//                    Log.e(TAG, "click btn, id:" + id);
//                    List<AccessibilityNodeInfo> sendBtn = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
//                    if (AccessibilityUtils.checkNodeInfoType(sendBtn, type)) {
//                        AccessibilityUtils.performClick(sendBtn.get(0));
//                    }
//                }
//            } else if (msg.what == MSG_REQUEST_CLOSE) {
//                //关闭webview 或 退出新打开的应用
//                if (TextUtils.isEmpty(installApkPackName)) {
//                    Log.e(TAG, "request back");
//                    AccessibilityUtils.performBack(SmartRobotService.this);
//                } else {
//                    Log.e(TAG, "kill app:" + installApkPackName);
//                    SuUtil.kill(installApkPackName);
//                }
//            } else if (msg.what == MSG_REQUEST_INTERSTITIAL) {
//                //插屏广告
//                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) msg.obj;
//                int type = localRandom.nextInt(2);
//                AccessibilityUtils.performClick(accessibilityNodeInfo.getChild(type));//0点击, 1关闭
//                if (type == 0) {
//                    AccessibilityUtils.performClick(accessibilityNodeInfo.getChild(1));//1关闭
//                }
//
//                Log.e(TAG, "Interstitial type:" + type);
//                installApkPackName = "com.android.browser";
//            } else if (msg.what == MSG_REQUEST_CUSTOM_SINGLE) {
//                //自定义单条广告
//                if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "立即免费下载")) {
//                    Log.e(TAG, "click download btn successful！");
//                } else {
//                    Log.e(TAG, "click download btn fail！");
//                }
//            } else if (msg.what == MSG_REQUEST_WARM_HINT) {
//                //温馨提示
//                if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "重新下载")) {
//                    Log.e(TAG, "warm hint! again download successful!");
//                } else {
//                    Log.e(TAG, "warm hint! again download fail!");
//                }
//            } else if (msg.what == MSG_REQUEST_AUTO_INSTALL) {
//                //自动安装应用
//                boolean isNext = true;
//                while (isNext) {
//                    try {
//                        isNext = AccessibilityUtils.findTextAndClick(SmartRobotService.this, "下一步");
//                        Log.e(TAG, "auto install, click next btn " + (isNext ? "success" : "fail"));
//                        Thread.sleep(700);
//                        if (!isNext) {
//                            isAutoInstall = AccessibilityUtils.findTextAndClick(SmartRobotService.this, "安装");
//                            Log.e(TAG, "auto install, click install btn " + (isAutoInstall ? "success" : "fail"));
//                            Thread.sleep(700);
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            } else if (msg.what == MSG_REQUEST_INSTALL_DONE) {
//                //安装完成
//                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) msg.obj;
//                List<AccessibilityNodeInfo> hintText = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.packageinstaller:id/center_text");
//
//                boolean isInstallFail = false;
//                if (AccessibilityUtils.checkNodeInfoType(hintText, "android.widget.TextView")) {
//                    isInstallFail = !isAutoInstall && hintText.get(0).getText().toString().contains("应用未安装");
//                }
//
//                if ((!isAutoInstall && isInstallFail) || !TextUtils.isEmpty(installApkPackName)) {
//                    if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "完成")) {
//                        Log.e(TAG, "install done, click 'done' successful!");
//                    } else {
//                        Log.e(TAG, "install done, click 'done' fail!");
//                    }
//                    installApkPackName = null;
//                } else {
//                    if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "打开")) {
//                        Log.e(TAG, "install done, click 'open' successful!");
//                    } else {
//                        AccessibilityUtils.findTextAndClick(SmartRobotService.this, "完成");
//                        Log.e(TAG, "install done, click 'open' fail! click done button!");
//                    }
//                    isAutoInstall = false;
//                }
//            } else if (msg.what == MSG_REQUEST_GO_BACK) {
//                //返回
//                AccessibilityUtils.performBack(SmartRobotService.this);
//                Log.e(TAG, "go back!");
//            } else if (msg.what == MSG_REQUEST_AUTHORIZE) {
//                //进入 6.0 权限获取 授权界面
//                if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "拒绝")) {
//                    Log.e(TAG, "authorize msg, click successful!");
//                } else {
//                    Log.e(TAG, "authorize msg, click fail!");
//                }
//            } else if (msg.what == MSG_REQUEST_EXIT_APP) {
//                if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, " 退 出 ")) {
//                    Log.e(TAG, "exit App, click successful!");
//                } else {
//                    Log.e(TAG, "exit App, click fail!");
//                }
//            }
//        }
//    };

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

//        //6.0 权限获取 自动授权
//        if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.widget.FrameLayout", 16384)
//                || AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.packageinstaller.permission.ui.GrantPermissionsActivity", 32)) {
//            //授权界面
//            if (accessibilityNodeInfo.getPackageName().equals("com.android.packageinstaller")
//                    && accessibilityNodeInfo.getClassName().equals("android.widget.FrameLayout")) {
//                Log.e(TAG, "进入授权界面");
//                handlerEnter(MSG_REQUEST_AUTHORIZE, null, 500 + localRandom.nextInt(500));//0.5到1秒
//            }
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "eu.chainfire.supersu.PromptActivity", 32)) {
//            //root 权限授权
//            if (AccessibilityUtils.findTextAndClick(SmartRobotService.this, "授权")) {
//                Log.e(TAG, "root 权限授权成功");
//            }
//        }

//        if (isInstallApkDone && TextUtils.isEmpty(installApkPackName) && checkAccessPaceName(accessibilityNodeInfo.getPackageName().toString())) {
//            installApkPackName = accessibilityNodeInfo.getPackageName().toString();
//        }
//        if (accessibilityNodeInfo.getPackageName().equals(installApkPackName)/* || (accessibilityNodeInfo.getChildCount() == 2
//                && accessibilityNodeInfo.getChild(0).getClassName().equals("android.webkit.WebView")
//                && accessibilityNodeInfo.getChild(1).getClassName().equals("android.widget.ProgressBar"))*/) {
//
//            Log.e(TAG, "enter out view!");
//            handlerEnter(MSG_REQUEST_CLOSE, null, 10000 + localRandom.nextInt(10000));//10到20秒
//            return;
//        }
//
//        if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.c.z.j.guess.cb.activity.MainActivity", 32)) {
//            //进入了游戏主界面  随机获取一种类型
//
//            List<AccessibilityNodeInfo> sendBtn = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.c.z.j.guess.cb:id/diyAdButton");
//            if (AccessibilityUtils.checkNodeInfoType(sendBtn, "android.widget.Button")) {
//                AccessibilityUtils.performClick(sendBtn.get(0));
//            }
//
//
//            /*String t = btnIds[localRandom.nextInt(btnIds.length)];
//            if (t.equals("back")) {
//                handlerEnter(MSG_REQUEST_GO_BACK, null, 1000);
//            } else {
//                clickNodeInfo(accessibilityNodeInfo, t, "android.widget.Button");
//            }*/
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.c.z.j.guess.cb.activity.GuessActivity", 32)) {
//            //进入继续游戏。
//            Log.e(TAG, "进入继续游戏");
//            String t = guessBtnIds[localRandom.nextInt(guessBtnIds.length)];
//            if (!t.equals("back") && accessibilityNodeInfo.getChildCount() >= 4 && accessibilityNodeInfo.getChild(4).getClassName()
//                    .toString().trim().equals("android.widget.LinearLayout") && accessibilityNodeInfo.getChild(4).getChildCount() == 1) {
//                clickNodeInfo(accessibilityNodeInfo.getChild(4).getChild(0), null, "click");
//            } else {
//                clickNodeInfo(accessibilityNodeInfo, null, "back");
//            }
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.c.z.j.guess.cb.activity.LevelMainActivity", 32)) {
//            //进入选择大关卡
//            Log.e(TAG, "进入选择大关卡");
//            String t = levelMainBtnIds[localRandom.nextInt(levelMainBtnIds.length)];
//            if (!t.equals("back") && accessibilityNodeInfo.getChildCount() >= 4 && accessibilityNodeInfo.getChild(4).getClassName()
//                    .toString().trim().equals("android.widget.LinearLayout") && accessibilityNodeInfo.getChild(4).getChildCount() == 1) {
//                clickNodeInfo(accessibilityNodeInfo.getChild(4).getChild(0), null, "click");
//            } else {
//                clickNodeInfo(accessibilityNodeInfo, null, "back");
//            }
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.c.z.j.guess.cb.activity.LevelItemActivity", 32)) {
//            //进入选择小关卡
//            Log.e(TAG, "进入选择小关卡");
//            clickNodeInfo(accessibilityNodeInfo, null, "back");
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.app.Dialog", 32)) {
//
//            //判断界面显示的是插屏广告。 随机 N秒 点击下载  或者 关闭
//            if (accessibilityNodeInfo.getClassName().equals("android.widget.FrameLayout") && accessibilityNodeInfo.getChildCount() == 2
//                    && accessibilityNodeInfo.getChild(0).getClassName().equals("android.widget.ImageView")) {
//                Log.e(TAG, "进入插屏广告页");
//                handlerEnter(MSG_REQUEST_INTERSTITIAL, accessibilityNodeInfo, 1000 + localRandom.nextInt(9000));//1到10秒
//            } else if (accessibilityNodeInfo.getClassName().equals("android.widget.FrameLayout") && accessibilityNodeInfo.getChildCount() == 2
//                    && accessibilityNodeInfo.getChild(1).getClassName().equals("android.widget.ScrollView")) {
//                //自定义广告(单条)
//                Log.e(TAG, "进入 自定义广告(单条)");
//                handlerEnter(MSG_REQUEST_CUSTOM_SINGLE, null, 1000 + localRandom.nextInt(9000));//1到10秒
//            } else if (accessibilityNodeInfo.getChildCount() == 5 && accessibilityNodeInfo.getChild(0).getClassName().equals("android.widget.TextView")
//                    && accessibilityNodeInfo.getChild(0).getText().toString().trim().contains("确定要退出吗")) {
//                Log.e(TAG, "进入退出界面");
//
//                int type = localRandom.nextInt(2);
//                if (type == 0) {
//                    handlerEnter(MSG_REQUEST_EXIT_APP, null, 1000 + localRandom.nextInt(4000));//1到5秒
//                } else {
//                    if (accessibilityNodeInfo.getChild(1).getClassName().toString().trim().equals("android.widget.ImageView")) {
//                        AccessibilityUtils.performClick(accessibilityNodeInfo.getChild(1));
////                        clickNodeInfo(accessibilityNodeInfo.getChild(1), null, "click");
//                    }
//                }
//            } else if(accessibilityNodeInfo.getChildCount() == 2 && accessibilityNodeInfo.getChild(0).getClassName().equals("android.webkit.WebView")
//                    && accessibilityNodeInfo.getChild(1).getClassName().equals("android.widget.ProgressBar")){
//                //进入内部webview
//                Log.e(TAG, "dialog 进入内部webview");
//
//            } else {
//                Log.e(TAG, "进入未知类型");
//                handlerEnter(MSG_REQUEST_GO_BACK, null, 1000);
//            }
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "android.app.AlertDialog", 32)) {
//            //温馨提示   文件已经存在 是否重新下载
//            Log.e(TAG, "进入 温馨提示");
//            handlerEnter(MSG_REQUEST_WARM_HINT, null, 1000 + localRandom.nextInt(9000));//1到10秒
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.packageinstaller.PackageInstallerActivity", 32)) {
//            Log.e(TAG, "进入 应用安装中");
//            handlerEnter(MSG_REQUEST_AUTO_INSTALL, null, 5000 + localRandom.nextInt(5000));//5到10秒
//
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.packageinstaller.InstallAppProgress", 32)) {
//            //应用安装完成界面
//            Log.e(TAG, "进入 应用安装完成界面");//123
//            handlerEnter(MSG_REQUEST_INSTALL_DONE, accessibilityNodeInfo, 1000 + localRandom.nextInt(2000));//1到3秒
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.server.am.AppErrorDialog", 32)) {
//            //已停止运行
//            Log.e(TAG, "已停止运行");
//            clickNodeInfo(accessibilityNodeInfo, "android:id/button1", "android.widget.Button");
//
//        } else if (AccessibilityUtils.isCurrPageForStr(accessibilityEvent, "com.android.org.chromium.content.browser.ContentViewCore", 2048)) {
//            //返回
//            if(accessibilityNodeInfo.getChild(0).getClassName().toString().trim().equals("android.webkit.WebView")){
//                Log.e(TAG, "ContentViewCore WebView");
//
//            } else {
//                Log.e(TAG, "ContentViewCore 自动返回");
//                handlerEnter(MSG_REQUEST_GO_BACK, null, 1000);
//            }
//
//        } else if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
//            try {
//                String toastMsg = (String) accessibilityEvent.getText().get(0);
//                String containsStr = "<<安装>>package:";
//                if (!TextUtils.isEmpty(toastMsg) && toastMsg.contains(containsStr)) {
//                    Log.e(TAG, "应用安装完成了");
//                    installApkPackName = toastMsg.replace(containsStr, "");
//
//                    ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                    List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
//                    if (list != null) {
//                        for (int i = 0; i < list.size(); ++i) {
//                            if (installApkPackName.matches(list.get(i).processName)) {
//                                isInstallApkDone = true;
//                                break;
//                            }
//                        }
//                    }
//
//                    handlerEnter(MSG_REQUEST_CLOSE, null, 10000 + localRandom.nextInt(10000));//10到20秒
//                }
//            } catch (Exception e) {
//                ;
//            }
//        }
//    }
//
//    //等待 1到5秒 之后，点击按钮
//    private void clickNodeInfo(AccessibilityNodeInfo nodeInfo, String id, String type) {
//        Message msg = new Message();
//        msg.what = MSG_REQUEST_CLICK_BTN;
//        msg.obj = nodeInfo;
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putString("type", type);
//        msg.setData(bundle);
//        handlerEnter(-1, msg, 1000 + localRandom.nextInt(1000));//1到3秒
//    }
//
//    private void handlerEnter(int what, Object obj, long delayMillis) {
//        for (int i = 0; i < MSG_REQUEST_MAX_COUNT; i++) {
//            mHandler2.removeMessages(i);
//        }
//
//        if (what < 0) {
//            mHandler2.sendMessageDelayed((Message) obj, 1000 + localRandom.nextInt(9000));//1到10秒
//        } else {
//            mHandler2.sendMessageDelayed(Message.obtain(mHandler2, what, obj), delayMillis);//1到3秒
//        }
    }

    @Override
    public void onInterrupt() {//辅助服务被关闭 执行此方法
        mHandler.removeCallbacks(mUpdateProxy);
        Toast.makeText(this, "服务被中断啦", Toast.LENGTH_SHORT).show();
    }

}