package com.jiuan.android.app.yilife.bean.login;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.Token;

/**
 * Created by Administrator on 2015/1/6.
 */
public class LoginResponse {

    @SerializedName("HGUID") private String hguid;

    @SerializedName("Token") private Token token;
    @SerializedName("UserName") private String username;

    public String getHguid() {
        return hguid;
    }

    public void setHguid(String hguid) {
        this.hguid = hguid;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
