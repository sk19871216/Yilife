package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovation.android.library.util.InnovationAlgorithm;
import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;
import com.jiuan.android.app.yilife.bean.login.LoginClient;
import com.jiuan.android.app.yilife.bean.login.LoginHandler;
import com.jiuan.android.app.yilife.bean.login.LoginResponse;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadLoginClient;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdHandler;
import com.jiuan.android.app.yilife.bean.threadlogin.VerfityOpenIdResponse;
import com.jiuan.android.app.yilife.config.EditTextWithDel;
import com.jiuan.android.app.yilife.config.FailMessage;
import com.jiuan.android.app.yilife.config.LoginFrom;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.config.WeiXinGet;
import com.jiuan.android.app.yilife.utils.TestOrNot;
import com.jiuan.android.app.yilife.utils.ToastOnly;
import com.jiuan.android.app.yilife.utils.VolleUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sample.Util;

public class LoginNormal extends Activity implements View.OnClickListener {
    private ActionBar actionBar;
    private TextView tv_title,tv_forget,tv_login_normal,tv_login_phone,tv_phone,tv_resg;
    private LinearLayout layout_normal,layout_phone;
    private Button button_normal,button_phone;
    private EditTextWithDel editText,editText_phone,editText_password;
    private ImageView imageView_phone,imageView_normal,iv_weixin,iv_qq,iv_sina,iv_alipay;
    private ProgressDialog dialog,progressDialog;
    private ImageView iv_back,iv_setting;
    private String const_s;
    private IWXAPI api;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private String uid,sina_tooken,qq_openid,qq_tooken;
    private final static String mAppid = ""+1104215150;
    private static Tencent mTencent;
    private static boolean isServerSideLogin = false;
    private IUiListener loginListener,loginListener_user;
    private UserInfo info;
    private RelativeLayout layout_title;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public void onClick(View v) {
        int id =v.getId();
        Animation shake = AnimationUtils.loadAnimation(LoginNormal.this,
                R.anim.shake);
        switch (id){
            case R.id.blue_icon_setting:
                Intent intent_gotireg = new Intent(LoginNormal.this, Registration.class);
                intent_gotireg.putExtra("request","woFragment");
                startActivity(intent_gotireg);
                break;
            case R.id.weixinloginbutton:
                weixin();
                v.startAnimation(shake);
                break;
            case R.id.sinabutton:
                sina();
                v.startAnimation(shake);
                break;
            case R.id.qqbutton:
                qqlogin();
                v.startAnimation(shake);
                break;
            case R.id.login_textview_forgetpassword:
                Intent intent = new Intent(LoginNormal.this, ForgetpasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_new_resg:
                Intent broad = new Intent("" + 1000);
                sendBroadcast(broad);
                Intent intent_resg = new Intent(LoginNormal.this, ResgActivity.class);
                startActivity(intent_resg);
                break;
            case R.id.login_button_getchecknumber:
/*                dialog.show();
                ToastOnly toastOnly = new ToastOnly(LoginNormal.this);
                if (!editText.getText().toString().equals("")) {
                    FastLoginPhoneClient.requestLogin(LoginNormal.this, editText.getText().toString()
                            , new FastLoginPhoneHandler() {
                        @Override
                        public void onInnovationSuccess(JsonElement value) {
                            super.onInnovationSuccess(value);
                            dialog.dismiss();*/
                            Intent intent_next = new Intent(LoginNormal.this, QuickLoginActivity.class);
//                            intent_next.putExtra("phone", editText.getText().toString());
                            startActivity(intent_next);
   /*                     }
                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            dialog.dismiss();
                            FailMessage.showfail(LoginNormal.this, msg);
                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            dialog.dismiss();
                            Toast.makeText(LoginNormal.this,"网络超时",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            dialog.dismiss();
                            FailMessage.showfail(LoginNormal.this, value);
                        }
                    }, TestOrNot.isTest);
                }else if (editText.getText().toString().equals("")) {
                    Toast.makeText(LoginNormal.this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }*/
                break;
            case R.id.login_button_noamal:
                loginnormal();
                closeSoftInput();
                break;

        }
    }
    protected  void closeSoftInput(){
        if (getCurrentFocus() !=null){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus()
                            .getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    protected void weixin(){
        {
            ScAndSv.WX="login";
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }
    }
    protected void sina(){
        {
            mSsoHandler = new SsoHandler(LoginNormal.this, mAuthInfo);
            mSsoHandler. authorize(new AuthListener());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        progressDialog = new ProgressDialog(LoginNormal.this);
        layout_title = (RelativeLayout) findViewById(R.id.relativelayout_titile);
        layout_title.setBackgroundColor(android.graphics.Color.parseColor("#209df3"));
        initialize();
        mTencent = Tencent.createInstance(mAppid, this);

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL,"");
//        if(LoginFrom.getLoginfrom() ==5){
//            Toast.makeText(LoginNormal.this,"请先登录",Toast.LENGTH_SHORT).show();
//        }
        api = WXAPIFactory.createWXAPI(LoginNormal.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        dialog = new ProgressDialog(LoginNormal.this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");
//        loginListener_user = new BaseUiListener(this, "get_simple_userinfo"){
        loginListener_user = new BaseUiListener(this, "get_user_info"){
            @Override
            protected void doComplete(JSONObject values) {
                super.doComplete(values);
                try {
                    String asd = values.getString("nickname");
                    Log.d("123123asd",asd);
                }catch (Exception e){
                }
            }
        };
        loginListener = new BaseUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
//                initOpenidAndToken(values);
                updateUserInfo();
//                updateLoginButton();

                progressDialog.setMessage("正在跳转。。。");
                progressDialog.setCancelable(false);
                progressDialog.show();
                try {

                    qq_openid = values.getString("openid");
                    qq_tooken = values.getString("access_token");
                }catch(Exception e) {
                }
                ThreadLoginClient.verfityopenid(LoginNormal.this, qq_openid, new VerfityOpenIdHandler() {
                    @Override
                    public void onLoginSuccess(VerfityOpenIdResponse response) {
                        super.onLoginSuccess(response);
                        progressDialog.dismiss();
                        int isExist = response.getIsExist();
                        if (isExist == 1) {
                            SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mysharedPreferences.edit();
                            editor.putInt("isLogin", 1).commit();
                            SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor_self = sharedPreferences.edit();
                            editor_self.putString("AccessToken", response.getToken().getAccessToken());
                            editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                            editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                            editor_self.putString("HGUID", response.getHGUID());
                            editor_self.commit();
                            Log.d("结果123g", response.getHGUID());
//                            editor_self.putString("NikeName", response.getHGUID());
//                            editor_self.putString("HGUID", response.getHGUID());

//                            Intent intent_sina = new Intent(LoginNormal.this,BangDingOrNot.class);
//                            startActivity(intent_sina);
                            getuserInfo();
                            comingfrom();
                            LoginNormal.this.finish();

                        } else if (isExist == 0) {
                            Intent intent = new Intent(LoginNormal.this, BangDingOrNot.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onInnovationFailure(String msg) {
                        super.onInnovationFailure(msg);
                        progressDialog.dismiss();
                        FailMessage.showfail(LoginNormal.this,msg);
//                        Toast.makeText(LoginNormal.this, "网络不好，请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInnovationError(String value) {
                        super.onInnovationError(value);
                        progressDialog.dismiss();
                        FailMessage.showfail(LoginNormal.this,value);
                    }

                    @Override
                    public void onInnovationExceptionFinish() {
                        super.onInnovationExceptionFinish();
                        progressDialog.dismiss();
                        Toast.makeText(LoginNormal.this, "网络不好，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }, TestOrNot.isTest);
            }
        };
        tv_title = (TextView) findViewById(R.id.blue_title);
        tv_title.setText("");
        iv_back = (ImageView) findViewById(R.id.blue_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_weixin = (ImageView) findViewById(R.id.weixinloginbutton);
        iv_weixin.setOnClickListener(this);
        iv_sina = (ImageView) findViewById(R.id.sinabutton);
        iv_sina.setOnClickListener(this);
        iv_qq = (ImageView) findViewById(R.id.qqbutton);
        iv_qq.setOnClickListener(this);
        tv_forget.setOnClickListener(this);

//        tv_login_normal.setOnClickListener(this);
//        tv_login_phone.setOnClickListener(this);
        button_normal.setOnClickListener(this);
        registerBoradcastReceiver();

    }
    private void  initialize(){
        tv_forget= (TextView) findViewById(R.id.login_textview_forgetpassword);
        tv_resg = (TextView) findViewById(R.id.login_new_resg);
        tv_resg.setOnClickListener(this);

        tv_phone = (TextView) findViewById(R.id.login_button_getchecknumber);
        tv_phone.setOnClickListener(this);
        button_normal = (Button) findViewById(R.id.login_button_noamal);

        editText_phone = (EditTextWithDel) findViewById(R.id.login_edittext_phone);
        editText_password = (EditTextWithDel) findViewById(R.id.login_edittext_password);
//        editText_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
////                if(v.getId()==R.id.login_edittext_password){
////                    Log.d("event",""+event.getKeyCode());
////                }
//
//
//                Log.d("asdasdasd","asdasdasdasd");
//
//                if(event!=null){
//                    Log.d("getKeyCode",""+event.getKeyCode());
//                    Log.d("actionId",""+actionId);
//                }
//
//
//
////                if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && event.getAction() == KeyEvent.ACTION_DOWN) {
////                    loginnormal();
////                    return true;
////                }
//                return false;
//            }
//        });
    }
    private void loginnormal(){
        final ToastOnly toastOnly = new ToastOnly(LoginNormal.this);
        dialog.show();
        if (editText_phone.getText().toString().equals("")){
            toastOnly.toastShowShort("请输入您的手机号");
            dialog.dismiss();
        }else if(editText_phone.getText().length() !=11){
            toastOnly.toastShowShort("请输入正确的手机号");
            dialog.dismiss();
        }else if (editText_password.getText().toString().equals("")) {
            toastOnly.toastShowShort("请输入您的密码");
            dialog.dismiss();
//        }else if(!isPhone(editText_phone.getText().toString())){
//            toastOnly.toastShowShort("请输入正确的手机号");
//            dialog.dismiss();
        }else {
            if (TestOrNot.isTest){
                const_s = ScAndSv.const_salt_test;
            }else{
                const_s = ScAndSv.const_salt;
            }
            LoginClient.requestLogin(LoginNormal.this, editText_phone.getText().toString()
                    , InnovationAlgorithm.SHA1(const_s, editText_password.getText().toString())
                    , new LoginHandler() {
                @Override
                public void onLoginSuccess(LoginResponse response) {
                    super.onLoginSuccess(response);
                    dialog.dismiss();
//                        Log.d("log-ss",value.toString());
                    SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mysharedPreferences.edit();
                    editor.putInt("isLogin", 1).commit();
                    SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor_self = sharedPreferences.edit();
                    editor_self.putString("AccessToken", response.getToken().getAccessToken());
                    editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                    editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                    editor_self.putString("HGUID", response.getHguid());
                    if (!sharedPreferences.getString("phone", "").equals("")) {
                        if (!sharedPreferences.getString("phone", "").equals(editText_phone.getText().toString())) {
                            editor_self.putString("name", "");
                            editor_self.putString("day", "");
                            editor_self.putString("sex", "");
                        }
                    }
                    editor_self.putString("phone", editText_phone.getText().toString());

                    editor_self.commit();
                    comingfrom();
                }

                @Override
                public void onInnovationFailure(String msg) {
                    super.onInnovationFailure(msg);
                    dialog.dismiss();
                    FailMessage.showfail(LoginNormal.this, msg);
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
                    FailMessage.showfail(LoginNormal.this, value);
                }
            }, TestOrNot.isTest);
        }
    }
    private void comingfrom(){
        if (LoginFrom.getLoginfrom() == 0) {
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 1) {
            Intent gointent = new Intent(LoginNormal.this, MyBBsNote.class);
            startActivity(gointent);
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 2) {
            Intent gointent = new Intent(LoginNormal.this, SendNote.class);
            startActivity(gointent);
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 3) {
//                        Intent gointent = new Intent(LoginNormal.this,MyBBsNote.class);
//                        startActivityForResult(gointent, 5);
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 4) {
            Intent gointent = new Intent(LoginNormal.this, PingLun.class);
            startActivity(gointent);
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 5) {
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 6) {
            Intent gointent = new Intent(LoginNormal.this, MyRedbag.class);
            startActivity(gointent);
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 7) {
            LoginNormal.this.finish();
        } else if (LoginFrom.getLoginfrom() == 8) {
            Intent gointent = new Intent(LoginNormal.this, BBSinfoActivity.class);
            startActivity(gointent);
            LoginNormal.this.finish();
        }else if (LoginFrom.getLoginfrom() == 9) {
//            Intent gointent = new Intent(LoginNormal.this, BBSinfoActivity.class);
//            startActivity(gointent);
//            LoginNormal.this.finish();
//            MainActivity.flag = 2;
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            Intent broad = new Intent("" + 100);
            sendBroadcast(broad);
        }
    }
   public Boolean isPhone(String phonenumber){
       String value=phonenumber;

      String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

       Pattern p = Pattern.compile(regExp);

       Matcher m = p.matcher(value);

       return m.find();

   }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==5 && resultCode == RESULT_OK) {
            Intent intentback = new Intent();
            setResult(RESULT_OK,intentback);
            LoginNormal.this.finish();
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(""+100)){
                LoginNormal.this.finish();
            }else if (action.equals("2010.2")){

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
    class AuthListener  implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            progressDialog.show();
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                Log.d("777",""+mAccessToken);
                uid = mAccessToken.getUid();

                sina_tooken = mAccessToken.getToken();
                String path2 = "https://api.weibo.com/2/users/show.json?access_token=" + sina_tooken + "&uid=" + uid;
                VolleUtils.initVolley(LoginNormal.this, path2, new VolleUtils.ResultCallBack() {
                    @Override
                    public void result(JSONObject response) {
                        try {
                            Log.e("code_n", response + "");
                            WeiXinGet.sina_name = response.getString("name");

                            if (response.getString("gender").equals("m")){
                                WeiXinGet.sina_gender = 1;
                            }else if(response.getString("gender").equals("f")){
                                WeiXinGet.sina_gender = 2;
                            }else {
                                WeiXinGet.sina_gender = 0;
                            }
                            WeiXinGet.sina_hd = response.getString("avatar_hd");
                            WeiXinGet.sina_avatar = response.getString("profile_image_url");
                            WeiXinGet.sina_uid = uid;
                            WeiXinGet.unicode = "";
                            WeiXinGet.qq_openid = "";
                            WeiXinGet.sourceType = 5;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
//                if (WeiXinGet.sina_name.equals("")){
//                    Toast.makeText(LoginNormal.this,"获取三方信息失败，请重新授权",Toast.LENGTH_SHORT).show();
//                }else{
                    ThreadLoginClient.verfityopenid(LoginNormal.this, uid, new VerfityOpenIdHandler() {
                        @Override
                        public void onLoginSuccess(VerfityOpenIdResponse response) {
                            super.onLoginSuccess(response);
                            int isExist = response.getIsExist();
                            progressDialog.dismiss();
                            if (isExist == 1) {
                                SharedPreferences mysharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mysharedPreferences.edit();
                                editor.putInt("isLogin", 1).commit();
                                SharedPreferences sharedPreferences = getSharedPreferences("self", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor_self = sharedPreferences.edit();
                                editor_self.putString("AccessToken", response.getToken().getAccessToken());
                                editor_self.putLong("AccessExpire", response.getToken().getAccessExpire());
                                editor_self.putString("RefreshToken", response.getToken().getRefreshToken());
                                editor_self.putString("HGUID", response.getHGUID());
                                editor_self.commit();
                                comingfrom();
                                Log.d("结果123g", response.getHGUID());
//                            editor_self.putString("NikeName", response.getHGUID());
//                            editor_self.putString("HGUID", response.getHGUID());
                                WeiXinGet.sourceType = 5;
//                            Intent intent_sina = new Intent(LoginNormal.this,BangDingOrNot.class);
//                            startActivity(intent_sina);
                                LoginNormal.this.finish();

                            } else if (isExist == 0) {
                                Intent intent = new Intent(LoginNormal.this, BangDingOrNot.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onInnovationFailure(String msg) {
                            super.onInnovationFailure(msg);
                            progressDialog.dismiss();
                            FailMessage.showfail(LoginNormal.this,msg);
//                        Toast.makeText(LoginNormal.this, "网络不好，请稍后重试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onInnovationError(String value) {
                            super.onInnovationError(value);
                            progressDialog.dismiss();
                            FailMessage.showfail(LoginNormal.this,value);

                        }

                        @Override
                        public void onInnovationExceptionFinish() {
                            super.onInnovationExceptionFinish();
                            progressDialog.dismiss();
                            Toast.makeText(LoginNormal.this, "网络不好，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }, TestOrNot.isTest);
//                }



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
                Toast.makeText(LoginNormal.this, message, Toast.LENGTH_LONG).show();
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
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
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
    private void onClickServerSideLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.loginServerSide(this, "all", loginListener);
            isServerSideLogin = true;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (!isServerSideLogin) { // SSO模式的登陆，先退出，再进行Server-Side模式登陆
                mTencent.logout(this);
                mTencent.loginServerSide(this, "all", loginListener);
                isServerSideLogin = true;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            isServerSideLogin = false;
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

                    Log.d("123123asd1",""+response);
                    JSONObject json = (JSONObject)response;
                    try {
                        String gender_qq = json.getString("gender");
                        if (gender_qq.equals("男")){
                            WeiXinGet.qq_gender = 1;
                        }else if (gender_qq.equals("女")){
                            WeiXinGet.qq_gender = 2;
                        }else{
                            WeiXinGet.qq_gender = 0;
                        }
                        WeiXinGet.qq_name = json.getString("nickname");
                        WeiXinGet.qq_avatar = json.getString("figureurl_qq_1");
                        WeiXinGet.qq_hd = json.getString("figureurl_qq_2");
                        WeiXinGet.qq_openid = qq_openid;
                        WeiXinGet.unicode = "";
                        WeiXinGet.sina_uid = "";
                        WeiXinGet.sourceType = 4;
                    }catch (Exception e){}

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
    public void getuserInfo()
    {
//        mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
//                "get", new BaseApiListener("get_simple_userinfo", false),
//                null);
        info = new UserInfo(this, LoginNormal.mTencent.getQQToken());
        info.getUserInfo(loginListener_user);
    }


}
