package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.forgetdone.ForgetDoneClient;
import com.jiuan.android.app.yilife.bean.forgetdone.ForgetDoneHandler;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;

public class Forgetpassword2Activity extends ParentActivity {
    private ImageView imageView,iv_back;
    private ProgressDialog dialog;
    private String const_s,code,f_phone;
    private EditTextWithDel editText,editText1;
    private ToastOnly toastOnly ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword2);
        Intent intent = getIntent();

        code = intent.getStringExtra("f_checknumber");
        f_phone = intent.getStringExtra("f_phone");
        iv_back = (ImageView) findViewById(R.id.forget2_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialog = new ProgressDialog(Forgetpassword2Activity.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        toastOnly = new ToastOnly(Forgetpassword2Activity.this);
        editText = (EditTextWithDel) findViewById(R.id.forgetpassword2_firstpassword);
        editText1 = (EditTextWithDel) findViewById(R.id.forgetpassword2_sec_password);
        imageView = (ImageView) findViewById(R.id.forgetpassword2_done);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Boolean isSpace =false;
                for (int i=0;i<editText.getText().length();i++){
                    if (editText.getText().toString().substring(i,i+1).equals(" ")){
                        isSpace = true;
                    }
                }
                if (!editText.getText().toString().equals(editText1.getText().toString())) {
                    toastOnly.toastShowShort("两次密码输入不一致");
                    dialog.dismiss();
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    editText.setText("");
                    editText1.setText("");
                }else if (editText.getText().length()==0) {
                    toastOnly.toastShowShort("请输入密码");
                    dialog.dismiss();
                } else if (editText1.getText().length()==0) {
                    toastOnly.toastShowShort("请输入确认密码");
                    dialog.dismiss();
                } else if((0<editText.getText().toString().length() && editText.getText().toString().length()<6) || editText.getText().toString().length()>16){
                    toastOnly.toastShowShort("密码应该为6~16位");
                    dialog.dismiss();
                }else if(isSpace) {
                    toastOnly.toastShowShort("密码中不能出现空格");
                    dialog.dismiss();

                }else {
                    if (TestOrNot.isTest){
                        const_s = ScAndSv.const_salt_test;
                    }else{
                        const_s = ScAndSv.const_salt;
                    }
                    ForgetDoneClient.requestLogin(Forgetpassword2Activity.this,
                            f_phone,
                            code,
                            InnovationAlgorithm.SHA1(const_s, editText.getText().toString())
//                                editText.getText().toString()
                            , new ForgetDoneHandler() {
                                @Override
                                public void onLoginSuccess(LoginResponse response) {
                                    super.onLoginSuccess(response);
                                    SharedPreferences mysharedPreferences =Forgetpassword2Activity.this.getSharedPreferences("login", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mysharedPreferences.edit();
                                    editor.putInt("isLogin", 1).commit();
                                    SharedPreferences sharedPreferences = Forgetpassword2Activity.this.getSharedPreferences("self", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                    editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                    editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                    editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                                    editor_self.putString("HGUID", response.getHguid());
//                                    editor_self.putString("phone", phone);
//                                    if (!sharedPreferences.getString("HGUID", "").equals("")) {
//                                        if (!sharedPreferences.getString("HGUID", "").equals(phone)) {
//                                            editor_self.putString("name", "");
//                                            editor_self.putString("day", "");
//                                            editor_self.putString("sex", "");
//                                        }
//                                    }
                                    editor_self.commit();
                                    toastOnly.toastShowShort("设置成功");
                                    dialog.dismiss();
                                    Intent broad = new Intent("" + 100);
                                    sendBroadcast(broad);
                                    finish();
                                }


                                @Override
                                public void onInnovationFailure(String msg) {
                                    super.onInnovationFailure(msg);
                                    dialog.dismiss();
                                    FailMessage.showfail(Forgetpassword2Activity.this ,msg);
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
                                    FailMessage.showfail(Forgetpassword2Activity.this, value);
                                }
                            }, TestOrNot.isTest);

                }
            }
        });
    }

}
