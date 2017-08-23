package com.android.zj.ai.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilityUtils {

    /**
     * 在当前页面查找文字内容并点击
     *
     * @param text
     */
    public static boolean findTextAndClick(AccessibilityService accessibilityService, String text) {
        boolean result = false;

        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return result;
        }

        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (text.equals(nodeInfo.getText().toString()) || text.equals(nodeInfo.getContentDescription()))) {
                    performClick(nodeInfo);
                    return true;
                }
            }
        }
        return result;
    }


    /**
     * 检查viewId进行点击
     *
     * @param accessibilityService
     * @param id
     */
    public static void findViewIdAndClick(AccessibilityService accessibilityService, String id) {

        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }

        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    performClick(nodeInfo);
                    break;
                }
            }
        }
    }


    /**
     * 在当前页面查找对话框文字内容并点击
     *
     * @param text1 默认点击text1
     * @param text2
     */
    public static void findDialogAndClick(AccessibilityService accessibilityService, String text1, String text2) {

        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return;
        }

        List<AccessibilityNodeInfo> dialogWait = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text1);
        List<AccessibilityNodeInfo> dialogConfirm = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text2);
        if (!dialogWait.isEmpty() && !dialogConfirm.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : dialogWait) {
                if (nodeInfo != null && text1.equals(nodeInfo.getText().toString())) {
                    performClick(nodeInfo);
                    break;
                }
            }
        }

    }

    //模拟点击事件
    public static void performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }

        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            performClick(nodeInfo.getParent());
        }
    }

    //模拟返回事件
    public static void performBack(AccessibilityService service) {
        if (service == null) {
            return;
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    //检查NodeInfo类型是否可用
    public static boolean checkNodeInfoType(List<AccessibilityNodeInfo> node, String type) {
        return !node.isEmpty() && node.get(0).getClassName().toString().trim().equals(type);
    }

    //检查NodeInfo内容是否可用
    public static boolean checkNodeInfoText(List<AccessibilityNodeInfo> node, String text) {
        return !node.isEmpty() && node.get(0).getText().toString().trim().equals(text);
    }

    /**
     * 是否进入了某个界面
     *
     * @return
     */
    public static boolean isCurrPageForStr(AccessibilityEvent accessibilityEvent, String pageName) {
        return isCurrPageForStr(accessibilityEvent, pageName, -1);
    }

    public static boolean isCurrPageForStr(AccessibilityEvent accessibilityEvent, String pageName, int type) {
        if (type >= 0) {
            return accessibilityEvent.getEventType() == type && pageName.equals(accessibilityEvent.getClassName().toString());
        } else {
            return pageName.equals(accessibilityEvent.getClassName().toString());
        }
    }
}
