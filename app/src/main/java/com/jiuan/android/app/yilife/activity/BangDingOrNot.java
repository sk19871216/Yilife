package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.threadlogin.CreatUserHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadInfoBean;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.WeiXinGet;
import com.jiuan.android.app.yilife.utils.TestOrNot;

public class BangDingOrNot extends ParentActivity {
    private TextView textView;
    private ImageView imageView,iv_back;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_ding_or_not);
        registerBoradcastReceiver();
        dialog = new ProgressDialog(BangDingOrNot.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(false);
        textView = (TextView) findViewById(R.id.tv_mashangbangding);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BangDingOrNot.this,Bangding.class);
                startActivity(intent);
            }
        });
        imageView = (ImageView) findViewById(R.id.suibianguangguang);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //广播
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
                ThreadLoginClient.creatuser(BangDingOrNot.this,threadInfoBean,new CreatUserHandler(){
                    @Override
                    public void onLoginSuccess(VerfityOpenIdResponse response) {
                        super.onLoginSuccess(response);
                        dialog.dismiss();
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
                        BangDingOrNot.this.finish();
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        dialog.dismiss();
                        Toast.makeText(BangDingOrNot.this,"网络超时",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        dialog.dismiss();
                        FailMessage.showfail(BangDingOrNot.this,msg);
                    }

                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        dialog.dismiss();
                        FailMessage.showfail(BangDingOrNot.this,value);
                    }
                }, TestOrNot.isTest);
            }
        });
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+100)){
                BangDingOrNot.this.finish();
            }
        }
    };
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("" + 100);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
