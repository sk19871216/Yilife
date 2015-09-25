package com.jiuan.android.app.yilife.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiuan.android.app.yilife.Constants;
import com.jiuan.android.app.yilife.R;

import com.jiuan.android.app.yilife.config.ScAndSv;
import com.jiuan.android.app.yilife.utils.VolleUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiXinLoginActivity extends ParentActivity {
    private IWXAPI api;
    private Button button,button_sina;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private String uid,sina_tooken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin_login);
//        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL,"");
        button  = (Button) findViewById(R.id.weixinlogin);
        button_sina  = (Button) findViewById(R.id.sinablog);
        api = WXAPIFactory.createWXAPI(WeiXinLoginActivity.this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixin();
            }
        });
        button_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinalogin();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getSharedPreferences("self", Activity.MODE_PRIVATE);
        String code = sharedPreferences.getString("weixincode","");
        Log.e("codew",code);
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constants.APP_ID+"&secret="
        +Constants.SERECT+"&code="+code+"&grant_type=authorization_code";
        if (!code.equals("")){
            VolleUtils.initVolley(WeiXinLoginActivity.this, path, new VolleUtils.ResultCallBack() {
                public void result(JSONObject response) {
                    try {
                        Log.e("code_r",response+"");
                        String access_token = response.getString("access_token");
                        String refresh_token =response.getString("refresh_token");
                        String openid =response.getString("openid");
                        String expires_in =response.getString("expires_in");
                        String scope =response.getString("scope");
                        String path2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
                        VolleUtils.initVolley(WeiXinLoginActivity.this,path2,new VolleUtils.ResultCallBack() {
                            @Override
                            public void result(JSONObject response) {
                                try {
                                    Log.e("code_n", response+ "");
                                    String name =response.getString("nickname");
                                    Log.e("code_na", name+ "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
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
    protected void sinalogin(){
        {
            mSsoHandler = new SsoHandler(WeiXinLoginActivity.this, mAuthInfo);
            mSsoHandler. authorize(new AuthListener());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    class AuthListener  implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                Log.d("777",""+mAccessToken);
                uid = mAccessToken.getUid();
                sina_tooken = mAccessToken.getToken();
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
                Toast.makeText(WeiXinLoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onWeiboException(WeiboException e) {
        }
    }

}
