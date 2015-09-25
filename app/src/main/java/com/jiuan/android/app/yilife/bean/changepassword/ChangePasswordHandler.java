package com.jiuan.android.app.yilife.bean.changepassword;

import android.util.Log;

import com.google.gson.JsonElement;
import com.innovation.android.library.http.InnovationHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangePasswordHandler extends InnovationHttpResponseHandler {




    @Override
    public void onInnovationSuccess(JsonElement value) {
//        ChangePasswordResponse oaLoginResponse = get(value.toString(), ChangePasswordResponse.class);
//        onLoginSuccess(oaLoginResponse);
        onLoginSuccess(value.toString());
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        Log.d("",""+responseString);
    }

    @Override
    public void onInnovationFailure(String msg) {
        super.onInnovationFailure(msg);
//        if (msg.equals(FAILURE_2001)) {
//            onLoginFailure("请求缺少必须参数");
//        } else if (msg.equals(FAILURE_2002)) {
//            onLoginFailure("Sc或Sv未授权");
//        } else if (msg.equals(FAILURE_2004)) {
//            onLoginFailure("错误的手机号格式");
//        }else if (msg.equals(FAILURE_2005)) {
//            onLoginFailure("发送短信失败");
//        } else if (msg.equals(FAILURE_2008)) {
//            onLoginFailure("用户不存在或者密码错误");
//        }
    }





    public void onLoginSuccess(String response) {
//        Log.d("",""+response.getUserna cme());
//        Log.d("getAccessKey", "" + oaLoginResponse.getAccessKey());
//        Log.d("getAccount", "" + oaLoginResponse.getAccount());
//        Log.d("getPassword", "" + oaLoginResponse.getPassword());
//        Log.d("getUserID", "" + oaLoginResponse.getUserID());
//        Log.d("getUserName", "" + oaLoginResponse.getUserName());
//        Log.d("getUserType", "" + oaLoginResponse.getUserType());
//        Log.d("getUserMnemonicCode", "" + oaLoginResponse.getUserMnemonicCode());
//        Log.d("getDepartmentID", "" + oaLoginResponse.getDepartmentID());
//        Log.d("getDepartmentName", "" + oaLoginResponse.getDepartmentName());
//        Log.d("getDepartmentMnemonicCode", "" + oaLoginResponse.getDepartmentMnemonicCode());
//        Log.d("getCompanyID", "" + oaLoginResponse.getCompanyID());
//        Log.d("getCompanyName", "" + oaLoginResponse.getCompanyName());
//        Log.d("getCompanyMnemonicCode", "" + oaLoginResponse.getCompanyMnemonicCode());
    }

    public void onLoginFailure(String msg) {
        Log.d("onLoginFailure", "" + msg);
    }

}
