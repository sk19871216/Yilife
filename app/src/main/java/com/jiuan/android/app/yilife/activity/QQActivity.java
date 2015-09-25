package com.jiuan.android.app.yilife.activity;

import android.os.SystemClock;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiuan.android.app.yilife.R;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import sample.Util;

public class QQActivity extends ParentActivity {
    private Button button;
    private final static String mAppid = ""+1104215150;
    private static Tencent mTencent;
    private static boolean isServerSideLogin = false;
    private IUiListener loginListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        button = (Button) findViewById(R.id.qq_login);
        mTencent = Tencent.createInstance(mAppid, this);
        loginListener = new BaseUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
                initOpenidAndToken(values);
//                updateUserInfo();
//                updateLoginButton();
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickServerSideLogin();
            }
        });
    }
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
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
//            updateUserInfo();
//            updateLoginButton();
        }
    }
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(QQActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(QQActivity.this, "返回为空", "登录失败");
                return;
            }
            Util.showResultDialog(QQActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(QQActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(QQActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }
 }



