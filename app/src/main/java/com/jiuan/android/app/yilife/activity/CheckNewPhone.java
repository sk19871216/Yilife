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
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.BangdingPhone.BangdingPhoneClient;
import com.jiuan.android.app.yilife.bean.BangdingPhone.ChangePhoneCodeHandler;
import com.jiuan.android.app.yilife.bean.BangdingPhone.GetPhoneCodeHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class CheckNewPhone extends ParentActivity {

    private EditTextWithDel ed_phone,ed_code;
    private TextView tv_num,tv_gonew,tv_tips,tv_title;
    private ImageView iv_back;
    private boolean serviceright =true,sendmsg = false;
    private int second,i;
    private ProgressDialog progressDialog;
    private String hguid,tooken,oldcode;
    private boolean hasnophone=false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what!=0) {
                tv_num.setClickable(false);
                tv_num.setText(msg.what + "秒");
            }else {
                tv_num.setClickable(true);
                tv_num.setText("重新获取");
                tv_tips.setText("友情提示：每个手机号只能绑定一个账号哦！");
//                tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#209df3"));
                tv_num.setBackgroundResource(R.drawable.button_blue);
                tv_num.setTextColor(android.graphics.Color.parseColor("#ffffff"));
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_phone);
        Intent intent = getIntent();
        oldcode = intent.getStringExtra("oldcode");
        hasnophone = intent.getBooleanExtra("hasnophone",false);
        SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
        hguid = sharedPreferences.getString("HGUID","");
        tooken = sharedPreferences.getString("AccessToken","");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载...");
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("手机绑定");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_code = (EditTextWithDel) findViewById(R.id.new_phone_code);
        ed_phone = (EditTextWithDel) findViewById(R.id.new_phone_phone);
        tv_num = (TextView) findViewById(R.id.new_phone_get_checknumber);
        tv_tips = (TextView) findViewById(R.id.tv_newphone_tips);
        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_phone.getText().toString().trim().length() ==11) {
                    tv_num.setText("60秒");
                    TimeDesc();
                    serviceright =true;
                    tv_tips.setText("验证码将发送到您的手机上");
                    sendmsg = true;
                    BangdingPhoneClient.getphonecode(CheckNewPhone.this,hguid,tooken, ed_phone.getText().toString().trim(), 2,
                            new GetPhoneCodeHandler() {
                                @Override
                                public void onLoginSuccess(String response) {
                                    super.onLoginSuccess(response);
                                    ToastOnly toastOnly = new ToastOnly(CheckNewPhone.this);
                                    toastOnly.toastShowShort("验证码已发送");

                                }

                                @Override
                                public void onInnovationExceptionFinish() {
                                    super.onInnovationExceptionFinish();
                                    Toast.makeText(CheckNewPhone.this, "网络超时", Toast.LENGTH_SHORT).show();
                                    i = -1;
                                    handler.sendEmptyMessage(0);
                                    serviceright = false;
                                    sendmsg = false;
                                }

                                @Override
                                public void onInnovationError(String value) {
                                    super.onInnovationError(value);
                                    FailMessage.showfail(CheckNewPhone.this, value);
                                    i = -1;
                                    handler.sendEmptyMessage(0);
                                    serviceright = false;
                                    sendmsg = false;
                                }

                                @Override
                                public void onInnovationFailure(String msg) {
                                    super.onInnovationFailure(msg);
                                    FailMessage.showfail(CheckNewPhone.this, msg);
                                    i = -1;
                                    handler.sendEmptyMessage(0);
                                    sendmsg = false;
                                    serviceright = false;
                                }
                            }, TestOrNot.isTest);
                }else{
                    Toast.makeText(CheckNewPhone.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_gonew = (TextView) findViewById(R.id.new_phone_gonewphone);
        tv_gonew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ed_phone.getText().toString().trim().length() ==11 && !ed_code.getText().toString().trim().equals("")) {
                    progressDialog.show();
                    if (hasnophone){
                        BangdingPhoneClient.newphone(CheckNewPhone.this, hguid, tooken, ed_phone.getText().toString().trim(),
                                ed_code.getText().toString().trim(), new GetPhoneCodeHandler() {
                                    @Override
                                    public void onLoginSuccess(String response) {
                                        super.onLoginSuccess(response);
                                        progressDialog.dismiss();
                                        Toast.makeText(CheckNewPhone.this, "绑定成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onInnovationExceptionFinish() {
                                        super.onInnovationExceptionFinish();
                                        progressDialog.dismiss();
                                        Toast.makeText(CheckNewPhone.this, "网络超时", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onInnovationError(String value) {
                                        super.onInnovationError(value);
                                        progressDialog.dismiss();
                                        FailMessage.showfail(CheckNewPhone.this, value);
                                    }

                                    @Override
                                    public void onInnovationFailure(String msg) {
                                        super.onInnovationFailure(msg);
                                        progressDialog.dismiss();
                                        FailMessage.showfail(CheckNewPhone.this, msg);
                                    }
                                }, TestOrNot.isTest);
                    }else {
                        BangdingPhoneClient.changephonecode(CheckNewPhone.this, hguid, tooken, ed_phone.getText().toString().trim(), oldcode,
                                ed_code.getText().toString().trim(), new ChangePhoneCodeHandler() {
                                    @Override
                                    public void onLoginSuccess(String response) {
                                        super.onLoginSuccess(response);
                                        progressDialog.dismiss();
                                        Toast.makeText(CheckNewPhone.this, "绑定成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onInnovationExceptionFinish() {
                                        super.onInnovationExceptionFinish();
                                        progressDialog.dismiss();
                                        Toast.makeText(CheckNewPhone.this, "网络超时", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onInnovationError(String value) {
                                        super.onInnovationError(value);
                                        progressDialog.dismiss();
                                        FailMessage.showfail(CheckNewPhone.this, value);
                                    }

                                    @Override
                                    public void onInnovationFailure(String msg) {
                                        super.onInnovationFailure(msg);
                                        progressDialog.dismiss();
                                        FailMessage.showfail(CheckNewPhone.this, msg);
                                    }
                                }, TestOrNot.isTest);
                    }
                }else if (ed_phone.getText().toString().trim().length() !=11){
                    Toast.makeText(CheckNewPhone.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CheckNewPhone.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void TimeDesc(){
//        get_checknumber.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
//        tv_get_checknum.setTextColor(android.graphics.Color.parseColor("#A3A3A3"));
        tv_num.setBackgroundResource(R.drawable.button_gray);
        tv_num.setTextColor(android.graphics.Color.parseColor("#000000"));
        i=60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                        while(i>=0) {
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
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("self",0);
        hguid = sharedPreferences.getString("HGUID","");
        tooken = sharedPreferences.getString("AccessToken","");
    }
    @Override
    public void onBackPressed() {
        if (sendmsg){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false).setNegativeButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CheckNewPhone.this.finish();
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
