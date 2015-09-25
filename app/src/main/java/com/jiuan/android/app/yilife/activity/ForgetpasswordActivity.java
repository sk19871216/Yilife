package com.jiuan.android.app.yilife.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.forgetpasswordchecknumber.ForgetPasswordChecknumberClient;
import com.jiuan.android.app.yilife.bean.forgetpasswordchecknumber.ForgetPasswordChecknumberHandler;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneClient;
import com.jiuan.android.app.yilife.bean.forgetpasswordphone.ForgetPasswordPhoneHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class ForgetpasswordActivity extends ParentActivity {
    private TextView tv_user_agreement,tv_checknumber;
    private ProgressDialog dialog;
    private EditTextWithDel ed_phone,ed_msg;
    private ImageView imageView,iv_back;
    private ToastOnly toastOnly;
    private boolean serviceright = true,sendmsg =false;
    private int second,i;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what!=0) {
                tv_checknumber.setClickable(false);
                tv_checknumber.setText(i + "秒");
            }else {
                tv_checknumber.setClickable(true);
                tv_checknumber.setText("重新获取");
//                tv_checknumber.setTextColor(android.graphics.Color.parseColor("#209df3"));
                tv_checknumber.setBackgroundResource(R.drawable.button_blue_durk);
                tv_checknumber.setTextColor(android.graphics.Color.parseColor("#ffffff"));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        toastOnly = new ToastOnly(ForgetpasswordActivity.this);
        registerBoradcastReceiver();
        iv_back = (ImageView) findViewById(R.id.forget1_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        tv_user_agreement = (TextView) findViewById(R.id.forgetpassword_xieyi);
        tv_checknumber = (TextView) findViewById(R.id.forgetpassword_button_getchecknumber);
        tv_checknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_phone.getText().toString().trim().length()!=11){
                    toastOnly.toastShowShort("请输入正确的手机号");
                }else {
                    sendmsg = true;
                    tv_checknumber.setText("60秒");
                    TimeDesc();
                    ForgetPasswordPhoneClient.requestLogin(ForgetpasswordActivity.this, ed_phone.getText().toString().trim()
                            , new ForgetPasswordPhoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);
                            toastOnly.toastShowShort("验证码已发送");
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(ForgetpasswordActivity.this, value);
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            sendmsg = false;
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(ForgetpasswordActivity.this, msg);
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            sendmsg = false;
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            toastOnly.toastShowShort("网络超时");
                            sendmsg = false;
                        }
                    }, TestOrNot.isTest);
                }
            }
        });
        ed_phone = (EditTextWithDel) findViewById(R.id.forgetpassword_user);
        ed_msg = (EditTextWithDel) findViewById(R.id.forgetpassword_msg);
        imageView = (ImageView) findViewById(R.id.forgetpassword_done);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TimeDesc();
                dialog.show();
                if (ed_phone.getText().toString().trim().length() != 11) {
                    toastOnly.toastShowShort("手机号应为11位数字");
                    dialog.dismiss();
                } else {
                    if (ed_msg.getText().toString().trim().length() == 0) {
                        toastOnly.toastShowShort("请输入验证码");
                        dialog.dismiss();
                    } else {
                        ForgetPasswordChecknumberClient.requestLogin(ForgetpasswordActivity.this, ed_phone.getText().toString().trim()
                                , ed_msg.getText().toString().trim()
                                , new ForgetPasswordChecknumberHandler() {
                            @Override
                            public void onInnovationSuccess(JsonElement value) {
                                super.onInnovationSuccess(value);
                                dialog.dismiss();
                                if (value.toString().equals("1")) {
                                    Intent intent = new Intent(ForgetpasswordActivity.this,Forgetpassword2Activity.class);
                                    intent.putExtra("f_checknumber",ed_msg.getText().toString().trim());
                                    intent.putExtra("f_phone",ed_phone.getText().toString().trim());
                                    startActivity(intent);
                                } else {
                                    toastOnly.toastShowShort("验证码错误");
                                }
                            }

                            @Override
                            public void onInnovationFailure(String msg) {
                                super.onInnovationFailure(msg);
                                dialog.dismiss();
                                FailMessage.showfail(ForgetpasswordActivity.this, msg);
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
                                FailMessage.showfail(ForgetpasswordActivity.this, value);
                            }
                        }, TestOrNot.isTest);
                    }
                }
            }
        });

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+100)){
                ForgetpasswordActivity.this.finish();
            }
        }
    };
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(""+100);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void TimeDesc(){
//            button_get.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
//        tv_checknumber.setTextColor(android.graphics.Color.parseColor("#A3A3A3"));
        tv_checknumber.setBackgroundResource(R.drawable.button_gray);
        tv_checknumber.setTextColor(android.graphics.Color.parseColor("#000000"));
        i = 60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(i>=0){
                    second = i;
                        handler.sendEmptyMessage(i);
                    try {
                          Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                }
//                for (int i=60;i>=0;i--){
//                    try {
//                        second = i;
//                        Thread.sleep(1000);
//                        Message msg = new Message();
//                        Bundle b = new Bundle();
//                        b.putInt("time",i);
//                        msg.setData(b);
//                        if (serviceright) {
//                            handler.sendMessage(msg);
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
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
                    ForgetpasswordActivity.this.finish();
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
