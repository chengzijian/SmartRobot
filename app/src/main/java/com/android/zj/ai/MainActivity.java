package com.android.zj.ai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

    }

    /**
     * 开启应用刷刷刷！！
     */
    private void openApp() {
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.c.z.j.guess.cb", "com.c.z.j.guess.cb.activity.WelcomeActivity");
        startActivity(intent);
    }
}
