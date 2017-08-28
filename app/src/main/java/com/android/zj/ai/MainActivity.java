package com.android.zj.ai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zj.ai.db.DaoMaster;
import com.android.zj.ai.db.MySQLiteOpenHelper;
import com.android.zj.ai.db.PhoneData;
import com.android.zj.ai.db.PhoneDataDao;
import com.android.zj.ai.utils.AppUtils;
import com.android.zj.ai.utils.IpProxyUtil;
import com.android.zj.ai.utils.SuUtil;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private boolean isBegin;
    private DaoMaster daoMaster;
    private PhoneDataDao mPhoneDataDao;
    private TextView tv_catcheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_catcheSize = (TextView) findViewById(R.id.tv_catcheSize);

        MigrationHelper.DEBUG = true;
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "test.db", null);
        daoMaster = new DaoMaster(helper.getEncryptedWritableDb("12342"));
        mPhoneDataDao = daoMaster.newSession().getPhoneDataDao();

        findViewById(R.id.id_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CacheUtil.delCacheFile();
//                SuUtil.kill("com.chengzj.zd", false);
//                initDB();
                IpProxyUtil.getIpProxy();
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

    private void initDB() {
        PhoneData test = new PhoneData(
                "960",
                "540",
                "G22+X715E+RUBY/AMAZE 4G",
                "",
                "HTC",
                "2.3",
                "35726504",
                "HTC X715e（Amaze 4G/G22）"
        );
        mPhoneDataDao.insert(test);

        //mPhoneDataDao.loadByRowId(0);
        Log.d("MigrationHelper", "TestData2 " + mPhoneDataDao.loadAll().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPhoneDataDao.getDatabase().close();
        super.onDestroy();
    }
}