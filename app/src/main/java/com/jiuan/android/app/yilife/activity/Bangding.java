package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.threadlogin.BingingUserHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadInfoBean;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.config.WeiXinGet;
import com.jiuan.android.app.yilife.utils.TestOrNot;

public class Bangding extends ParentActivity {
    private EditTextWithDel ed_user,ed_pass;
    private TextView tv_bangding,tv_title;
    private ImageView iv_back;
    private String const_s;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangding);
        dialog = new ProgressDialog(Bangding.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("绑定已有账号");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_user = (EditTextWithDel) findViewById(R.id.bangding_user);
        ed_pass = (EditTextWithDel) findViewById(R.id.bangding_password);
        tv_bangding = (TextView) findViewById(R.id.bangding_bangding);
        tv_bangding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                ThreadInfoBean threadInfoBean = new ThreadInfoBean();
                if (!WeiXinGet.unicode.equals("")) {
                    threadInfoBean.setOpenId(WeiXinGet.unicode);
                    threadInfoBean.setNickName(WeiXinGet.nikename);
                    threadInfoBean.setGender(WeiXinGet.gender);
                    threadInfoBean.setAvatar(WeiXinGet.avatar);
                    threadInfoBean.setSourceType(WeiXinGet.sourceType);
                }else if (!WeiXinGet.sina_uid.equals("")) {
                    threadInfoBean.setOpenId(WeiXinGet.sina_uid);
                    threadInfoBean.setNickName(WeiXinGet.sina_name);
                    threadInfoBean.setGender(WeiXinGet.sina_gender);
                    threadInfoBean.setAvatar(WeiXinGet.sina_avatar);
                    threadInfoBean.setAvatarHd(WeiXinGet.sina_hd);
                    threadInfoBean.setSourceType(WeiXinGet.sourceType);
                }else if (!WeiXinGet.qq_openid.equals("")){
                    threadInfoBean.setOpenId(WeiXinGet.qq_openid);
                    threadInfoBean.setNickName(WeiXinGet.qq_name);
                    threadInfoBean.setGender(WeiXinGet.qq_gender);
                    threadInfoBean.setAvatar(WeiXinGet.qq_avatar);
                    threadInfoBean.setAvatarHd(WeiXinGet.qq_hd);
                    threadInfoBean.setSourceType(WeiXinGet.sourceType);
                }
                if (TestOrNot.isTest){
                    const_s = ScAndSv.const_salt_test;
                }else{
                    const_s = ScAndSv.const_salt;
                }
                ThreadLoginClient.binginguser(Bangding.this,threadInfoBean,ed_user.getText().toString().trim(),
                        InnovationAlgorithm.SHA1(const_s, ed_pass.getText().toString().trim())
                ,new BingingUserHandler(){
                            @Override
                            public void onLoginSuccess(VerfityOpenIdResponse response) {
                                super.onLoginSuccess(response);
                                dialog.dismiss();
//                                Intent broad = new Intent("" + 100);
//                                sendBroadcast(broad);
                                SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mysharedPreferences.edit();
                                editor.putInt("isLogin", 1).commit();
                                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                                editor_self.putString("HGUID", response.getHGUID()).commit();
                                Intent broad = new Intent("" + 100);
                                sendBroadcast(broad);
                                Bangding.this.finish();
                            }

                            @Override
                            public void onInnovationExceptionFinish() {
                                super.onInnovationExceptionFinish();
                                dialog.dismiss();
                                Toast.makeText(Bangding.this, "网络超时", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onInnovationFailure(String msg) {
                                super.onInnovationFailure(msg);
                                dialog.dismiss();
                                FailMessage.showfail(Bangding.this, msg);
                            }

                            @Override
                            public void onInnovationError(String value) {
                                super.onInnovationError(value);
                                dialog.dismiss();
                                FailMessage.showfail(Bangding.this,value);
                            }
                        },TestOrNot.isTest);
            }
        });

    }
}
