package com.jiuan.android.app.yilife.bean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class Token {
    private String AccessToken;
    private Long AccessExpire;
    private String RefreshToken;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public Long getAccessExpire() {
        return AccessExpire;
    }

    public void setAccessExpire(Long accessExpire) {
        AccessExpire = accessExpire;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }
}
