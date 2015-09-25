package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class LoginGetchecknumber extends ParentActivity {
    private TextView textView;
    private EditTextWithDel editText;
    private Button button;
    private ProgressDialog dialog;
    private String phone;
    private ActionBar actionBar;
    private ImageView iv_back,iv_setting;
    private TextView tv_bar_title,get_checknumber;
    private int second;
    private ToastOnly toastOnly = new ToastOnly(this);
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle b = msg.getData();
            int i = b.getInt("time");
            if (i!=0) {
                get_checknumber.setClickable(false);
                get_checknumber.setText(i + "秒");
            }else {
                get_checknumber.setClickable(true);
                get_checknumber.setText("再次获取");
                get_checknumber.setTextColor(android.graphics.Color.parseColor("#209df3"));
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_getchecknumber);

        tv_bar_title = (TextView) findViewById(R.id.blue_icon_title);
        tv_bar_title.setText("登录");
        iv_back = (ImageView) findViewById(R.id.blue_icon_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_setting = (ImageView) findViewById(R.id.blue_icon_setting);
        iv_setting.setImageResource(R.drawable.icon_reg);
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gotireg = new Intent(LoginGetchecknumber.this, Registration.class);
                intent_gotireg.putExtra("request","woFragment");
                startActivity(intent_gotireg);
            }
        });
        dialog = new ProgressDialog(LoginGetchecknumber.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");

        textView= (TextView) findViewById(R.id.getchecknumber_textview_phone);
        editText= (EditTextWithDel) findViewById(R.id.getchecknumber_edittext_checknumber);
        get_checknumber = (TextView) findViewById(R.id.getchecknumber_button_getchecknumber);
        TimeDesc();
        get_checknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkInfo.isNetworkAvailable(LoginGetchecknumber.this)) {
                    get_checknumber.setText("60秒");
                    TimeDesc();
                    FastLoginPhoneClient.requestLogin(LoginGetchecknumber.this, phone
                            , new FastLoginPhoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);
                            dialog.dismiss();
                            toastOnly.toastShowShort("验证码已发送");
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            dialog.dismiss();
                            FailMessage.showfail(LoginGetchecknumber.this, msg);
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
                            FailMessage.showfail(LoginGetchecknumber.this, value);
                        }
                    }, TestOrNot.isTest);
                }else{
                    dialog.dismiss();
                    toastOnly.toastShowShort("请检查您的网络环境");
                }
            }
        });
        button= (Button) findViewById(R.id.getchecknumber_login_button);
        textView.setText("验证码将发送至"+intent.getStringExtra("phone"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (editText.getText().length()!=0) {
                    if (NetWorkInfo.isNetworkAvailable(LoginGetchecknumber.this)) {
                        FastLoginChecknumberClient.requestLogin(LoginGetchecknumber.this, phone, editText.getText().toString(),
                                new FastLoginChecknumberHandler() {
                                    @Override
                                    public void onInnovationFailure(String msg) {
                                        super.onInnovationFailure(msg);
                                        dialog.dismiss();
                                        FailMessage.showfail(LoginGetchecknumber.this, msg);
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
                                        FailMessage.showfail(LoginGetchecknumber.this, value);
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
                                        if (!sharedPreferences.getString("phone", "").equals("")) {
                                            if (!sharedPreferences.getString("phone", "").equals(phone)) {
                                                editor_self.putString("name", "");
                                                editor_self.putString("day", "");
                                                editor_self.putString("sex", "");
                                            }
                                        }
                                        editor_self.putString("phone", phone);
                                        editor_self.commit();
                                        SharedPreferences mySharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                        final SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        editor.putInt("isLogin", 1).commit();
                                        if (LoginFrom.getLoginfrom() == 0) {
//                                            Intent intent_finsh = new Intent(LoginGetchecknumber.this, MainActivity.class);
//                                            intent_finsh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            intent_finsh.putExtra("position", 2);
//                                            startActivity(intent_finsh);
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
//                                            Intent gointent = new Intent(LoginGetchecknumber.this,MyBBsNote.class);
//                                            startActivity(gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 1) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            Intent gointent = new Intent(LoginGetchecknumber.this, MyBBsNote.class);
                                            startActivity(gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 2) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            Intent gointent = new Intent(LoginGetchecknumber.this, SendNote.class);
                                            startActivity(gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 3) {
                                            Intent gointent = new Intent();
                                            setResult(RESULT_OK, gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 4) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            Intent gointent = new Intent(LoginGetchecknumber.this, PingLun.class);
                                            startActivity(gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 5) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 6) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            Intent gointent = new Intent(LoginGetchecknumber.this, MyRedbag.class);
                                            startActivity(gointent);
                                            LoginGetchecknumber.this.finish();
                                        } else if (LoginFrom.getLoginfrom() == 7) {
                                            Intent broad = new Intent("" + 100);
                                            sendBroadcast(broad);
                                            LoginGetchecknumber.this.finish();
                                        }
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
    }

    private void TimeDesc(){
//        get_checknumber.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
        get_checknumber.setTextColor(android.graphics.Color.parseColor("#A3A3A3"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=60;i>=0;i--){
                    try {
                        second = i;
                        Thread.sleep(1000);
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("time",i);
                        msg.setData(b);
                        handler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


}
