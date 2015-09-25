package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneClient;
import com.jiuan.android.app.yilife.bean.registioncheckphone.RegistionCheckphoneHandler;
import com.jiuan.android.app.yilife.bean.registiondone.RegistionDoneClient;
import com.jiuan.android.app.yilife.bean.registiondone.RegistionDoneHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class ResgActivity extends ParentActivity {
    private EditTextWithDel ed_phone,ed_msg,ed_password,ed_invitationcode;
    private int second,i;
    private TextView tv_checknumber,tv_xieyi;
    private ImageView imageView,iv_back;
    private ToastOnly toastOnly ;
    private ProgressDialog dialog;
    private String const_s;
    private CheckBox checkBox;
    private String path="http://int.iemylife.com/mobile/policy";
    private  AlertDialog.Builder builder;
    private  AlertDialog alertDialog;
    private boolean serviceright = true,sendmsg=false,opentwo = false;
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
                tv_checknumber.setTextColor(android.graphics.Color.parseColor("#ffffff"));
//                tv_checknumber.setBackground(R.drawable.button_blue);
                tv_checknumber.setBackgroundResource(R.drawable.button_blue_durk);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resg);
        registerBoradcastReceiver();
        toastOnly = new ToastOnly(ResgActivity.this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        checkBox = (CheckBox) findViewById(R.id.resg_checkbox);
        iv_back = (ImageView) findViewById(R.id.resg_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_phone = (EditTextWithDel) findViewById(R.id.resg_user);
        ed_msg = (EditTextWithDel) findViewById(R.id.resg_msg);
        ed_password = (EditTextWithDel) findViewById(R.id.resg_password);
        ed_invitationcode = (EditTextWithDel) findViewById(R.id.resg_invitationcode);
        imageView = (ImageView) findViewById(R.id.resg_done);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Boolean isSpace = false;
                for (int i = 0; i < ed_password.getText().length(); i++) {
                    if (ed_password.getText().toString().substring(i, i + 1).equals(" ")) {
                        isSpace = true;
                    }
                }
                if (checkBox.isChecked()) {
                    if (ed_phone.getText().length() != 11) {
                        toastOnly.toastShowShort("请输入正确的手机号");
                        dialog.dismiss();
                    } else if (ed_phone.getText().length() == 0) {
                        toastOnly.toastShowShort("请输入手机号");
                        dialog.dismiss();
                    } else if (ed_msg.getText().length() == 0) {
                        toastOnly.toastShowShort("请输入验证码");
                        dialog.dismiss();

                    } else if (ed_password.getText().length() == 0) {
                        toastOnly.toastShowShort("请输入密码");
                        dialog.dismiss();

                    } else if ((0 < ed_password.getText().toString().length() && ed_password.getText().toString().length() < 6) || ed_password.getText().toString().length() > 16) {
                        toastOnly.toastShowShort("密码应该为6~16位");
                        dialog.dismiss();
                    } else if (isSpace) {
                        toastOnly.toastShowShort("密码中不能出现空格");
                        dialog.dismiss();
                    } else {
                        if (TestOrNot.isTest) {
                            const_s = ScAndSv.const_salt_test;
                        } else {
                            const_s = ScAndSv.const_salt;
                        }
                        Log.e("结果incode",ed_invitationcode.getText().toString().trim());
                        RegistionDoneClient.requestLogin(ResgActivity.this, ed_phone.getText().toString().trim(),
                                ed_msg.getText().toString().trim(),
                                InnovationAlgorithm.SHA1(const_s, ed_password.getText().toString())
                                , ed_invitationcode.getText().toString().trim()
                                , new RegistionDoneHandler() {
                                    @Override
                                    public void onLoginSuccess(LoginResponse response) {
                                        super.onLoginSuccess(response);
                                        SharedPreferences mysharedPreferences = ResgActivity.this.getSharedPreferences("login", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mysharedPreferences.edit();
                                        editor.putInt("isLogin", 1).commit();
                                        SharedPreferences sharedPreferences = ResgActivity.this.getSharedPreferences("self", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                        editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                        editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                        editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
//                                    editor_self.putString("phone", phone);
                                        editor_self.putString("HGUID", response.getHguid());
                                        if (!sharedPreferences.getString("phone", "").equals("")) {
                                            if (!sharedPreferences.getString("HGUID", "").equals(response.getHguid())) {
                                                editor_self.putString("name", "");
                                                editor_self.putString("day", "");
                                                editor_self.putString("sex", "");
                                            }
                                        }
                                        editor_self.commit();
                                        Intent broad = new Intent("" + 100);
                                        sendBroadcast(broad);
                                        dialog.dismiss();
                                        ResgActivity.this.finish();
                                    }


                                    @Override
                                    public void onInnovationFailure(String msg) {
                                        super.onInnovationFailure(msg);
                                        dialog.dismiss();
                                        FailMessage.showfail(ResgActivity.this, msg);

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
                                        FailMessage.showfail(ResgActivity.this, value);
//                                        }
                                    }

                                }, TestOrNot.isTest);
                    }
                }else{
                    dialog.dismiss();
                    toastOnly.toastShowShort("请先阅读《宜生活用户使用协议》");
                }
            }
        });
        tv_checknumber = (TextView) findViewById(R.id.resg_getchecknumber);
        tv_checknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_phone.getText().toString().trim().length() !=11){
                    toastOnly.toastShowShort("请输入正确的手机号");
                }else {
                    tv_checknumber.setText("60秒");
                    sendmsg = true;
                    TimeDesc();
                    RegistionCheckphoneClient.requestLogin(ResgActivity.this, ed_phone.getText().toString().trim()
                            , new RegistionCheckphoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);
                            toastOnly.toastShowShort("验证码已发送");
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            FailMessage.showfail(ResgActivity.this, msg);
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            sendmsg = false;
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            FailMessage.showfail(ResgActivity.this, value);
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            sendmsg = false;
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            toastOnly.toastShowShort("网络异常");
                            i = -1;
                            handler.sendEmptyMessage(0);
                            serviceright = false;
                            sendmsg = false;
                        }
                    }, TestOrNot.isTest);
                }
            }
        });
        tv_xieyi = (TextView) findViewById(R.id.resg_xieyi);
        tv_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_xieyi.setClickable(false);
                    if (NetWorkInfo.isNetworkAvailable(ResgActivity.this)) {
//                    WebView webView;
                        ImageView iv;
//                    LayoutInflater inflater = getLayoutInflater();
                        View view = LayoutInflater.from(ResgActivity.this).inflate(R.layout.activity_text, null);
//                    webView = (WebView) view.findViewById(R.id.webview_secort);
                        TextView textView = (TextView) view.findViewById(R.id.text_xieyi);
                        String xieyi = "网站免责声明\n" +
                                "访问者在接受本网站服务之前，请务必仔细阅读本条款并同意本声明。访问者访问本网站的行为以及通过各类方式利用本网站的行为，都将被视作是对本声明全部内容的无异议的认可；如有异议，请立即跟本网站协商，并取得本网站的书面同意意见。\n" +
                                "第一条 访问者在从事与本网站相关的所有行为（包括但不限于访问浏览、利用、转载、宣传介绍）时，必须以善意且谨慎的态度行事；访问者不得故意或者过失的损害或者弱化本网站的各类合法权利与利益，不得利用本网站以任何方式直接或者间接的从事违反中国法律、国际公约以及社会公德的行为，且访问者应当恪守下述承诺：" +
                                "1、传输和利用信息符合中国法律、国际公约的规定、符合公序良俗；\n" +
                                "2、不将本网站以及与之相关的网络服务用作非法用途以及非正当用途； \n" +
                                "3、不干扰和扰乱本网站以及与之相关的网络服务；\n" +
                                "4、遵守与本网站以及与之相关的网络服务的协议、规定、程序和惯例等。\n" +
                                "第二条 本网站郑重提醒访问者：请在转载、上载或者下载有关作品时务必尊重该作品的版权、著作权；如果您发现有您未署名的作品，请立即和我们联系，我们会在第一时间加上您的署名或作相关处理。\n" +
                                "第三条 除我们另有明确说明或者中国法律有强制性规定外，本网站用户原创的作品，本网站及作者共同享有版权，其他网站及传统媒体如需使用，须取得本网站的书面授权，未经授权严禁转载或用于其它商业用途。\n" +
                                "第四条 本网站内容仅代表作者本人的观点，不代表本网站的观点和看法，与本网站立场无关，相关责任作者自负。";
                        textView.setText(xieyi);
//                    webView.loadData(xieyi,"text/html; charset=UTF-8",null);
//                    webView.loadUrl(path);
                        iv = (ImageView) view.findViewById(R.id.blue_back);
                        iv.setImageResource(R.drawable.icon_close);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                tv_xieyi.setClickable(true);
                            }
                        });
                        TextView tv_title = (TextView) view.findViewById(R.id.blue_title);
                        tv_title.setText("宜生活用户使用协议");
                        builder = new AlertDialog.Builder(ResgActivity.this);
                        alertDialog = builder.create();
                        alertDialog.setView(view);
//                        if (!alertDialog.isShowing()) {
                        alertDialog.show();
                        alertDialog.setCancelable(false);
//                        tv_xieyi.setClickable(true);

//                        }
//                        opentwo = false;
                    } else {
                        alertDialog.dismiss();
                        tv_xieyi.setClickable(true);
                        ToastOnly toastOnly = new ToastOnly(ResgActivity.this);
                        toastOnly.toastShowShort("请检查您的网络环境");
                    }

            }
        });

    }
    private void TimeDesc(){
//            button_get.setBackgroundColor(android.graphics.Color.parseColor("#dadada"));
//        tv_checknumber.setBackgroundColor(android.graphics.Color.parseColor("#d6d6d6"));
        tv_checknumber.setBackgroundResource(R.drawable.button_gray);
        i = 60;
        tv_checknumber.setTextColor(android.graphics.Color.parseColor("#000000"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i >= 0) {
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
                    ResgActivity.this.finish();
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
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+1000)){
                ResgActivity.this.finish();
            }
        }
    };
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(""+1000);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
