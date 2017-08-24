package com.android.zj.ai;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.zj.ai.utils.AppUtils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    private boolean isBegin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(isBegin){
            if (AppUtils.isAccessibilitySettingsOn(MainActivity.this)) {
                AppUtils.openApp();
            } else {
                Toast.makeText(MainActivity.this, "请开启服务，否则无法运行", Toast.LENGTH_LONG).show();
            }
            isBegin = false;
        }
    }

}
