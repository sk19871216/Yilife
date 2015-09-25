package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.utils.ToastOnly;

import io.fabric.sdk.android.Fabric;
import java.util.Timer;
import java.util.TimerTask;


public class Splash extends Activity {
    private ToastOnly toastOnly;
    private boolean isfirst = false;
    private SharedPreferences adSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        toastOnly = new ToastOnly(Splash.this);
        setContentView(R.layout.activity_splash);

        adSharedPreferences= getSharedPreferences("AD", Activity.MODE_PRIVATE);
        int code_get = adSharedPreferences.getInt("versioncode",0);
        SharedPreferences.Editor editor_ad = adSharedPreferences.edit();
//        editor_ad.putBoolean("isfirst",isfirst).commit();

        int versioncode =  getVersion();
        if (versioncode>code_get){
            editor_ad.putInt("versioncode",versioncode).commit();
            Timer timer = new Timer();  //定时器
            TimerTask task = new TimerTask() {

                @Override
                public void run() {

                    Intent intent = new Intent(Splash.this,AD.class);
                    startActivity(intent);
                    finish();

                }
            };
            timer.schedule(task, 3000);

        }else{
            enterMainActivity();
        }

    }
    private void enterMainActivity(){
        Timer timer = new Timer();  //定时器
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                Intent intent = new Intent(Splash.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        timer.schedule(task, 3000);
    }
    public int getVersion(){
        // 拿到android 中包管理的服务
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
//            version = info.versionName;
//            tv_version.setText(version);
            return info.versionCode;    //返回应用的版本号


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
