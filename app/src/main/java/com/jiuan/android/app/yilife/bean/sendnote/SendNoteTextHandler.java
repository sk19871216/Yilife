package com.jiuan.android.app.yilife.bean.sendnote;

import android.util.Log;

import com.google.gson.JsonElement;
import com.innovation.android.library.http.InnovationHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SendNoteTextHandler extends InnovationHttpResponseHandler {
    @Override
    public void onInnovationSuccess(JsonElement value) {
        int[] oaLoginResponse = get(value.toString(), int[].class);
        onLoginSuccess(oaLoginResponse);
//        onLoginSuccess(value.toString());
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        Log.d("",""+responseString);
    }

    @Override
    public void onInnovationFailure(String msg) {
        super.onInnovationFailure(msg);
    }





    public void onLoginSuccess(int[] response) {
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
