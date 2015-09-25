package com.jiuan.android.app.yilife.bean.threadlogin;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.Token;

/**
 * Created by Administrator on 2015/1/6.
 */
public class VerfityOpenIdResponse {

    @SerializedName("IsExist") private int  isExist;
    @SerializedName("HGUID") private String  HGUID;
    @SerializedName("UserName") private String userName;
    @SerializedName("Token") private Token token;

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    public String getHGUID() {
        return HGUID;
    }

    public void setHGUID(String HGUID) {
        this.HGUID = HGUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
