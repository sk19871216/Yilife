package com.jiuan.android.app.yilife.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.changepasswordnornal.ChangepasswordnornalClient;
import com.jiuan.android.app.yilife.bean.changepasswordnornal.CreatNewPassHandler;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.NetWorkInfo;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class CreatNewPassword extends ParentActivity {
    private TextView tv_title,tv_setting;
    private ImageView iv_back;
    private EditTextWithDel editText_first,editText_second;
    private ProgressDialog dialog;
    private ToastOnly toastOnly;
    private String const_s,hguid,tooken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_password);
        SharedPreferences sharedPreferences = getSharedPreferences("self",0);
        hguid = sharedPreferences.getString("HGUID","");
        tooken = sharedPreferences.getString("AccessToken","");
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(this);
        editText_first = (EditTextWithDel) findViewById(R.id.creat_new_firstpassword);
        editText_second = (EditTextWithDel) findViewById(R.id.creat_new_secondpassword);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("密码管理");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_setting = (TextView) findViewById(R.id.blue_setting);
        tv_setting.setText("保存");
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_first.getText().toString().equals(editText_second.getText().toString())) {
                    toastOnly.toastShowShort("两次输入的密码不一致");
                    dialog.dismiss();
                    editText_first.setText("");
                    editText_second.setText("");
                } else {
                    Boolean isSpace = false;
                    for (int i = 0; i < editText_first.getText().length(); i++) {
                        if (editText_first.getText().toString().substring(i, i + 1).equals(" ")) {
                            isSpace = true;
                        }
                    }
                    if ((0 < editText_first.getText().toString().length() && editText_first.getText().toString().length() < 6) || editText_first.getText().toString().length() > 16) {
                        toastOnly.toastShowShort("密码应该为6~16位");
                        dialog.dismiss();
                    } else if (isSpace) {
                        toastOnly.toastShowShort("密码中不能出现空格");
                        dialog.dismiss();
                        dialog.dismiss();

                    } else if (editText_first.getText().toString().equals("")) {
                        toastOnly.toastShowShort("请输入密码");
                        dialog.dismiss();
                    } else if (editText_second.getText().toString().equals("")) {
                        toastOnly.toastShowShort("请输入确认密码");
                        dialog.dismiss();

                    } else {
                        if (NetWorkInfo.isNetworkAvailable(CreatNewPassword.this)) {
                            if (TestOrNot.isTest) {
                                const_s = ScAndSv.const_salt_test;
                            } else {
                                const_s = ScAndSv.const_salt;
                            }

                            ChangepasswordnornalClient.creatnewpassword(CreatNewPassword.this, hguid, tooken,
                                    InnovationAlgorithm.SHA1(const_s, editText_first.getText().toString()),
                                    new CreatNewPassHandler() {
                                        @Override
                                        public void onInnovationSuccess(JsonElement value) {
                                            super.onInnovationSuccess(value);
                                            dialog.dismiss();
                                            toastOnly.toastShowShort("设置成功");
//                                            Intent broad = new Intent("" + 100);
//                                            sendBroadcast(broad);
//                                            SharedPreferences mySharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = mySharedPreferences.edit();
//                                            editor.putInt("isLogin", 0).commit();
                                            CreatNewPassword.this.finish();
                                        }

                                        @Override
                                        public void onInnovationFailure(String msg) {
                                            super.onInnovationFailure(msg);
                                            dialog.dismiss();
                                            FailMessage.showfail(CreatNewPassword.this, msg);
                                        }

                                        @Override
                                        public void onInnovationExceptionFinish() {
                                            super.onInnovationExceptionFinish();
                                            toastOnly.toastShowShort("网络超时");
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onInnovationError(String value) {
                                            super.onInnovationError(value);
                                            dialog.dismiss();
                                            FailMessage.showfail(CreatNewPassword.this, value);
                                        }
                                    }, TestOrNot.isTest);
                        } else {
                            dialog.dismiss();
                            toastOnly.toastShowShort("请检查您的网络环境");
                        }
                    }
                }
            }
        });
        }


}
