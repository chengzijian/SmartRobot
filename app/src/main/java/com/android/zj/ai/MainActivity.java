package com.android.zj.ai;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zj.ai.utils.AppUtils;
import com.android.zj.ai.utils.CacheUtil;
import com.android.zj.ai.utils.SuUtil;

import java.lang.reflect.Method;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private boolean isBegin;
    private TextView tv_catcheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_catcheSize = (TextView) findViewById(R.id.tv_catcheSize);
        findViewById(R.id.id_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SuUtil.kill("com.android.daoway");
//                CacheUtil.delCacheFile();

                SuUtil.kill("com.chengzj.zd", false);
            }
        });

        findViewById(R.id.open_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isAccessibilitySettingsOn(MainActivity.this)) {
                    isBegin = false;
                    AppUtils.openApp();
                } else {
                    isBegin = true;
                    try {
                        //打开系统设置中辅助功能
                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "请开启服务", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                        Toast.makeText(MainActivity.this, "无法打开设置", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBegin) {
            if (AppUtils.isAccessibilitySettingsOn(MainActivity.this)) {
                AppUtils.openApp();
            } else {
                Toast.makeText(MainActivity.this, "请开启服务，否则无法运行", Toast.LENGTH_LONG).show();
            }
            isBegin = false;
        }
    }

    private final int MSG_GET_DATASIZE = 0x00;
    private final int MSG_CLEAR_DATA_SUCCESS = 0x01;
    private long catcheSize;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_GET_DATASIZE:
                    String size = Formatter.formatFileSize(MainActivity.this, catcheSize);
                    tv_catcheSize.setText(size);
                    break;
                case MSG_CLEAR_DATA_SUCCESS:
                    String pkgname = (String) msg.obj;
                    queryPacakgeSize(pkgname);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public void queryPacakgeSize(String pkgName) {
        if (pkgName != null) {
            //使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo
            PackageManager pm = getPackageManager();  //得到pm对象
            try {
                //通过反射机制获得该隐藏函数
                Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
                getPackageSizeInfo.invoke(pm, pkgName, statsObserver);
            } catch (Exception ex) {
                Log.e(TAG, "NoSuchMethodException");
                ex.printStackTrace();
            }
        }
    }

//    private void getSize(String packageName) {
//        if (!TextUtils.isEmpty(packageName)) {
//            PackageManager pManager = getPackageManager();
//            pManager.getPackageSizeInfo(packageName, statsObserver);
//        }
//    }

    IPackageStatsObserver statsObserver = new IPackageStatsObserver.Stub() {
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            // TODO Auto-generated method stub
            catcheSize = pStats.dataSize;
            handler.sendEmptyMessage(MSG_GET_DATASIZE);
        }
    };

    class ClearUserDataObserver extends IPackageDataObserver.Stub {
        public void onRemoveCompleted(final String packageName, final boolean succeeded) {
            Log.d(TAG, "packageName " + packageName + "   succeeded  " + succeeded);
            if (succeeded) {
                Message msg = Message.obtain();
                msg.what = MSG_CLEAR_DATA_SUCCESS;
                msg.obj = packageName;
                handler.sendMessage(msg);
            }
        }
    }

    private ClearUserDataObserver mClearDataObserver;

    public void clear(String packageName) {
        if (mClearDataObserver == null) {
            mClearDataObserver = new ClearUserDataObserver();
        }
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        try {
            Class<?> amClass = Class.forName(am.getClass().getName());
            Method clearApp = amClass.getMethod("clearApplicationUserData", String.class, IPackageDataObserver.class);
            Log.d("MainActivity", "clearApp: " + clearApp.getName());
            clearApp.invoke(am, packageName, mClearDataObserver);
        } catch (Exception e) {
            Log.d("MainActivity", "Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
