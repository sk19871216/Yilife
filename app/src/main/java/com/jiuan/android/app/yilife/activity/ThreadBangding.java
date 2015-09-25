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
import android.os.SystemClock;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.threadbangding.ThreadBangdingClient;
import com.jiuan.android.app.yilife.bean.threadbangding.ThreadBangdingHandler;
import com.jiuan.android.app.yilife.bean.threadbangding.ThreadBangdingStuHandler;
import com.jiuan.android.app.yilife.bean.threadbangding.ThreadBangdingStuResponse;
import com.jiuan.android.app.yilife.bean.threadbangding.ThreadUnBangdingHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadInfoBean;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import sample.Util;

public class ThreadBangding extends ParentActivity {
    private  AlertDialog.Builder builder;
    private ImageView toggleButton_weixin,toggleButton_qq,toggleButton_sina,toggleButton_alipay,iv_back;
    private TextView tv_weixin,tv_qq,tv_sina,tv_alipay,tv_title;
    private String tooken,hguid,qq_openid,sina_openid;
    private IWXAPI api;
    private UserInfo info;
    private AuthInfo mAuthInfo;
    private static Tencent mTencent;
    private final static String mAppid = ""+1104215150;
    private static boolean isServerSideLogin = false;
    private IUiListener loginListener;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private ToastOnly toastOnly = new ToastOnly(ThreadBangding.this);
    private ProgressDialog progressDialog;
    private boolean weixin_ison = false,qq_ison=false,sina_ison=false,alipay_ison=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_bangding);
        registerBoradcastReceiver();
        progressDialog = new ProgressDialog(ThreadBangding.this);
        api = WXAPIFactory.createWXAPI(ThreadBangding.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL,"");
        mTencent = Tencent.createInstance(mAppid, this);
        init();
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loginListener = new BaseUiListener(){
            protected void doComplete(JSONObject values) {
                super.doComplete(values);
                try {
                    qq_openid = values.getString("openid");
                    updateUserInfo();
                }catch (Exception e){
                }
            }
        };
        SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken", "");
        hguid = sharedPreferences.getString("HGUID","");
        ThreadBangdingClient.showthirdpartystatus(ThreadBangding.this,hguid,tooken,new ThreadBangdingStuHandler(){
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                FailMessage.showfail(ThreadBangding.this, value);
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                FailMessage.showfail(ThreadBangding.this,msg);
                progressDialog.dismiss();
            }

            @Override
            public void onLoginSuccess(ThreadBangdingStuResponse response) {
                super.onLoginSuccess(response);
                if (response.getWeixinStatus()==1){
                    weixin_ison = true;
                    toggleButton_weixin.setImageResource(R.drawable.bangding_on);
                }
                if (response.getQQStatus()==1){
                    qq_ison = true;
                    toggleButton_qq.setImageResource(R.drawable.bangding_on);
                }
                if (response.getSinaStatus()==1){
                    sina_ison = true;
                    toggleButton_sina.setImageResource(R.drawable.bangding_on);
                }
                if (response.getZhiFuBaoStatus()==1){
                    alipay_ison = true;
                    toggleButton_alipay.setImageResource(R.drawable.bangding_on);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                toastOnly.toastShowShort("网络异常");
                progressDialog.dismiss();
            }
        }, TestOrNot.isTest);

    }
    private  void init(){
        iv_back = (ImageView)findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("第三方账号绑定");
        tv_weixin = (TextView) findViewById(R.id.bangdingstate_weixin);
        tv_qq = (TextView) findViewById(R.id.bangdingstate_qq);
        tv_sina = (TextView) findViewById(R.id.bangdingstate_sina);
        tv_alipay = (TextView) findViewById(R.id.bangdingstate_alipay);

        toggleButton_weixin = (ImageView) findViewById(R.id.on_off_button_weixin);
        toggleButton_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weixin_ison){
//                    toastOnly.toastShowShort("是不是解绑");
                    unbangding(3);
                }else{
                    weixin();
                }
            }
        });

        toggleButton_qq = (ImageView) findViewById(R.id.on_off_button_QQ);
        toggleButton_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qq_ison){
//                    toastOnly.toastShowShort("是不是解绑");
                    unbangding(4);
                }else{
                    onClickLogin();
                }
            }
        });

        toggleButton_sina = (ImageView) findViewById(R.id.on_off_button_sina);
        toggleButton_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sina_ison){
//                    toastOnly.toastShowShort("是不是解绑");
                    unbangding(5);
                }else{
                    sina();
                }
            }
        });

        toggleButton_alipay = (ImageView) findViewById(R.id.on_off_button_alipay);

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+200)){
                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                String weixincode = sharedPreferences.getString("unicode","");
                if (!weixincode.equals("")){
                    bangding(3,weixincode);
                }

            }
        }
    };
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(""+200);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
    private void unbangding(final int type){
        final int ftype = type;
        SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        tooken = sharedPreferences.getString("AccessToken", "");
        hguid = sharedPreferences.getString("HGUID","");
        progressDialog.show();
        if (builder==null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("您是否要解除绑定关系")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {
                            ThreadBangdingClient.unbangdingThird(ThreadBangding.this, hguid, tooken, ftype, new ThreadUnBangdingHandler() {
                                @Override
                                public void onInnovationError(String value) {
                                    super.onInnovationError(value);
                                    FailMessage.showfail(ThreadBangding.this, value);
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onInnovationFailure(String msg) {
                                    super.onInnovationFailure(msg);
                                    FailMessage.showfail(ThreadBangding.this, msg);
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onLoginSuccess(String response) {
                                    super.onLoginSuccess(response);
                                    if (type == 3){
                                        tv_weixin.setText("未绑定");
                                        weixin_ison = false;
                                        toggleButton_weixin.setImageResource(R.drawable.bangding_off);
                                    }else if (type ==4){
                                        tv_qq.setText("未绑定");
                                        qq_ison = false;
                                        toggleButton_qq.setImageResource(R.drawable.bangding_off);
                                    }else if (type ==5){
                                        tv_sina.setText("未绑定");
                                        sina_ison = false;
                                        toggleButton_sina.setImageResource(R.drawable.bangding_off);
                                    }else if (type ==6){
                                        tv_alipay.setText("未绑定");
                                        alipay_ison = false;
                                        toggleButton_alipay.setImageResource(R.drawable.bangding_off);
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onInnovationExceptionFinish() {
                                    super.onInnovationExceptionFinish();
                                    toastOnly.toastShowShort("网络超时，请稍后重试");
                                    progressDialog.dismiss();
                                }
                            }, TestOrNot.isTest);
                            builder = null;
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            builder = null;
                            progressDialog.dismiss();
                        }
                    }).setCancelable(false).show();
            AlertDialog alert = builder.create();
        }

    }
    protected void weixin(){
        {
            ScAndSv.WX="bangding";
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }
    }
    protected void sina(){
        {
            mSsoHandler = new SsoHandler(ThreadBangding.this, mAuthInfo);
            mSsoHandler. authorize(new AuthListener());
        }
    }
    class AuthListener  implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                Log.d("777",""+mAccessToken);
                sina_openid = mAccessToken.getUid();
                bangding(5,sina_openid);
//                tv_sina.setText("已绑定");
//                sina_ison =true;
//                toggleButton_sina.setImageResource(R.drawable.bangding_on);
                // 保存 Token 到 SharedPreferences
//                AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);
//                .........
            } else {
                // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
//                String code = values.getString("code", "");
//                .........
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(ThreadBangding.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onWeiboException(WeiboException e) {
        }
    }
    private void qqlogin(){
        onClickLogin();
    }
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref1", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
//            updateLoginButton();
        }
    }
    private void updateUserInfo() {
        Log.d("123123asd1","start");

        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {

//                    tv_qq.setText("已绑定");
                    bangding(4,qq_openid);
//                    qq_ison = true;
//                    toggleButton_qq.setImageResource(R.drawable.bangding_on);
                }

                @Override
                public void onCancel() {

                }
            };
            info = new UserInfo(this, mTencent.getQQToken());
            info.getUserInfo(listener);

        } else {
//            mUserInfo.setText("");
//            mUserInfo.setVisibility(android.view.View.GONE);
//            mUserLogo.setVisibility(android.view.View.GONE);
        }
    }
    private void bangding(int type ,String openid){
        final int bangtype = type;
        ThreadInfoBean threadInfoBean = new ThreadInfoBean();
        threadInfoBean.setOpenId(openid);
        threadInfoBean.setSourceType(type);
        ThreadBangdingClient.bangdingThird(ThreadBangding.this,threadInfoBean,hguid,1,new ThreadBangdingHandler(){
            @Override
            public void onInnovationError(String value) {
                super.onInnovationError(value);
                FailMessage.showfail(ThreadBangding.this,value);
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationFailure(String msg) {
                super.onInnovationFailure(msg);
                FailMessage.showfail(ThreadBangding.this,msg);
                progressDialog.dismiss();
            }

            @Override
            public void onLoginSuccess(VerfityOpenIdResponse response) {
                super.onLoginSuccess(response);
                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                editor_self.putString("AccessToken", response.getToken().getAccessToken());
                editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                editor_self.putString("HGUID", response.getHGUID());
                editor_self.commit();
                if (bangtype == 3){
                    tv_weixin.setText("已绑定");
                    weixin_ison=true;
                    toggleButton_weixin.setImageResource(R.drawable.bangding_on);
                }else if (bangtype ==4){
                    tv_qq.setText("已绑定");
                    qq_ison=true;
                    toggleButton_qq.setImageResource(R.drawable.bangding_on);
                }else if (bangtype ==5){
                    tv_sina.setText("已绑定");
                    sina_ison=true;
                    toggleButton_sina.setImageResource(R.drawable.bangding_on);
                }else if (bangtype ==6){
                    tv_alipay.setText("已绑定");
                    alipay_ison=true;
                    toggleButton_alipay.setImageResource(R.drawable.bangding_on);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onInnovationExceptionFinish() {
                super.onInnovationExceptionFinish();
                progressDialog.dismiss();
                toastOnly.toastShowShort("网络超时");
            }
        },TestOrNot.isTest);
    }
    public class BaseUiListener implements IUiListener {
        private Context mContext;
        private String mScope;
        public BaseUiListener() {
            super();
        }
        public BaseUiListener(Context mContext, String mScope) {
            super();
            this.mContext = mContext;
            this.mScope = mScope;
        }
        @Override
        public void onComplete(Object response) {
            if (null == response) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
//                Util.showResultDialog(LoginNormal.this, "返回为空", "登录失败");
                return;
            }
//            Util.showResultDialog(LoginNormal.this, response.toString(), "登录成功");
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
//            Util.toastMessage(LoginNormal.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
//            Util.toastMessage(LoginNormal.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
