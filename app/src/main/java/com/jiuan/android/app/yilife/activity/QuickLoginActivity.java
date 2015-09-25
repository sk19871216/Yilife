package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.fastloginchecknumber.FastLoginChecknumberClient;
import com.jiuan.android.app.yilife.bean.fastloginchecknumber.FastLoginChecknumberHandler;
import com.jiuan.android.app.yilife.bean.fastloginphone.FastLoginPhoneClient;
import com.jiuan.android.app.yilife.bean.fastloginphone.FastLoginPhoneHandler;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class QuickLoginActivity extends ParentActivity {
    private ImageView iv_quick_done,iv_back;
    private EditTextWithDel ed_code,ed_phone;
    private ProgressDialog dialog;
    private TextView tv_get_checknum;
    private int second= 0,i;
    private ToastOnly toastOnly;
    private String tooken,hguid,phone_num;
    private boolean serviceright =true,sendmsg = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what!=0) {
                tv_get_checknum.setClickable(false);
                tv_get_checknum.setText(msg.what + "秒");
            }else {
                tv_get_checknum.setClickable(true);
                tv_get_checknum.setText("重新获取");
//                tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#209df3"));
                tv_get_checknum.setBackgroundResource(R.drawable.button_blue_durk);
                tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#ffffff"));
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_login);
        dialog = new ProgressDialog(QuickLoginActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(QuickLoginActivity.this);
        SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken", "");
        hguid = sharedPreferences.getString("HGUID","");
        iv_back = (ImageView) findViewById(R.id.quick_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_phone = (EditTextWithDel) findViewById(R.id.quick_user);
        ed_code = (EditTextWithDel) findViewById(R.id.quick_msg);
        iv_quick_done = (ImageView) findViewById(R.id.quick_done);
        iv_quick_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (ed_code.getText().length()!=0) {
                    if (NetWorkInfo.isNetworkAvailable(QuickLoginActivity.this)) {
                        phone_num = ed_phone.getText().toString().trim();
                        FastLoginChecknumberClient.requestLogin(QuickLoginActivity.this, phone_num, ed_code.getText().toString(),
                                new FastLoginChecknumberHandler() {
                                    @Override
                                    public void onInnovationFailure(String msg) {
                                        super.onInnovationFailure(msg);
                                        dialog.dismiss();
                                        FailMessage.showfail(QuickLoginActivity.this, msg);
                                    }

                                    @Override
                                    public void onInnovationExceptionFinish() {
                                        super.onInnovationExceptionFinish();
                                        dialog.dismiss();
                                        toastOnly.toastShowShort("网络超时");
                                    }

                                    @Override
                                    public void onInnovationError(String value) {
                                        super.onInnovationError(value);
                                        dialog.dismiss();
                                        FailMessage.showfail(QuickLoginActivity.this, value);
                                    }

                                    @Override
                                    public void onLoginSuccess(LoginResponse response) {
                                        super.onLoginSuccess(response);
                                        dialog.dismiss();
                                        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                        editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                        editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                        editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                                        if (!sharedPreferences.getString("hguid", "").equals("")) {
                                            if (!sharedPreferences.getString("hguid", "").equals(hguid)) {
                                                editor_self.putString("name", "");
                                                editor_self.putString("day", "");
                                                editor_self.putString("sex", "");
                                            }
                                        }
                                        editor_self.putString("HGUID", response.getHguid());
                                        editor_self.putString("phone", phone_num);
                                        editor_self.commit();
                                        SharedPreferences mySharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                        final SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        editor.putInt("isLogin", 1).commit();
                                        Intent broad = new Intent("" + 100);
                                        sendBroadcast(broad);
                                        QuickLoginActivity.this.finish();

                                    }
                                }

                                , TestOrNot.isTest);
                    }else{
                        dialog.dismiss();
                        toastOnly.toastShowShort("请检查您的网络环境");
                    }
                }else {
                    toastOnly.toastShowShort("请输入验证码");
                    dialog.dismiss();
                }
            }
        });
        tv_get_checknum = (TextView) findViewById(R.id.quick_button_getchecknumber);
        tv_get_checknum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_phone.getText().length() != 11){
                    toastOnly.toastShowShort("请输入正确的手机号");
                }else {
                    if (NetWorkInfo.isNetworkAvailable(QuickLoginActivity.this)) {
                        tv_get_checknum.setText("60秒");
                        sendmsg = true;
                        TimeDesc();
                        phone_num = ed_phone.getText().toString().trim();
                        FastLoginPhoneClient.requestLogin(QuickLoginActivity.this, phone_num
                                , new FastLoginPhoneHandler() {
                            @Override
                            public void onInnovationSuccess(JsonElement value) {
                                super.onInnovationSuccess(value);
                                serviceright =true;
                                dialog.dismiss();
                                toastOnly.toastShowShort("验证码已发送");
                            }

                            @Override
                            public void onInnovationFailure(String msg) {
                                super.onInnovationFailure(msg);
                                serviceright =false;
                                dialog.dismiss();
                                handler.sendEmptyMessage(0);
                                i=0;
                                FailMessage.showfail(QuickLoginActivity.this, msg);
                            }

                            @Override
                            public void onInnovationExceptionFinish() {
                                super.onInnovationExceptionFinish();
                                serviceright =false;
                                dialog.dismiss();
                                i=0;
                                handler.sendEmptyMessage(0);
                                toastOnly.toastShowShort("网络超时");
                            }

                            @Override
                            public void onInnovationError(String value) {
                                super.onInnovationError(value);
                                serviceright =false;
                                handler.sendEmptyMessage(0);
                                i=0;
                                dialog.dismiss();
                                FailMessage.showfail(QuickLoginActivity.this, value);
                            }
                        }, TestOrNot.isTest);
                    } else {
                        dialog.dismiss();
                        toastOnly.toastShowShort("请检查您的网络环境");
                    }
                }
            }
        });
    }
    private void TimeDesc(){
//        get_checknumber.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
//        tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#A3A3A3"));
        tv_get_checknum.setBackgroundResource(R.drawable.button_gray);
        i = 60;
        tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#000000"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(i>=0){
                    try {
                        second = i;
                            handler.sendEmptyMessage(i);
                        Thread.sleep(1000);
                        i--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    @Override
    public void onBackPressed() {
        if (sendmsg){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false).setNegativeButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    QuickLoginActivity.this.finish();
                }
            }).setPositiveButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setMessage("验证码已发送，是否放弃当前操作").show();
//            AlertDialog dialog = builder.create();
        }else{
            super.onBackPressed();
        }
    }
}
