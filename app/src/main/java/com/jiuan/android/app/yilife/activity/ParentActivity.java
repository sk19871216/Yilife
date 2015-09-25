package com.jiuan.android.app.yilife.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.config.LoginFrom;

import java.util.List;

public class ParentActivity extends ActionBarActivity {
    private static boolean dialog_1 = false;
    private static boolean dialog_2 = false;
    private  AlertDialog.Builder builder=null;
    private static Handler handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

    }

    public static class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("2010.1")){
                if (handle != null)
                handle.sendEmptyMessage(1);
            }
            if (intent.getAction().equals("2010.2")){
                if (handle != null)
                handle.sendEmptyMessage(2);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1 && !isForeground(ParentActivity.this,"com.jiuan.android.app.yilifeT.activity.MainActivity")) {
                    if (builder == null) {
                        builder = new AlertDialog.Builder(ParentActivity.this);
//                builder.setMessage("您的账号异常,否要重新登录")
                        builder.setMessage("您的账号异地登录")
                                .setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(ParentActivity.this, LoginNormal.class);
                                        LoginFrom.setLoginfrom(7);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivity(intent);
                                        builder = null;
                                        dialog_1 = false;
                                    }
                                }).setCancelable(false).show();
                    }
                }
                if (msg.what==2 && !isForeground(ParentActivity.this,"com.jiuan.android.app.yilifeT.activity.MainActivity")) {
                    if (builder == null) {
                        builder = new AlertDialog.Builder(ParentActivity.this);
                        builder.setMessage("你的账号长时间未登录已过期")
                                .setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(ParentActivity.this, LoginNormal.class);
                                        LoginFrom.setLoginfrom(7);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivity(intent);
                                        builder = null;
                                        dialog_2= false;
                                    }
                                })
                                .setCancelable(false).show();
                        AlertDialog alert = builder.create();
                    }
                }
            }
        };


    }
    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }
}
