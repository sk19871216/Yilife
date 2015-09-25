package com.jiuan.android.app.yilife.bean.getredbag;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetredbagisgetResponse {
    @SerializedName("HasJoined") private int hasJoined;
    @SerializedName("HasGot") private int hasGot;
    @SerializedName("HasShared") private int hasShared;
    @SerializedName("Url") private  String url;

    public int getHasJoined() {
        return hasJoined;
    }

    public void setHasJoined(int hasJoined) {
        this.hasJoined = hasJoined;
    }

    public int getHasShared() {
        return hasShared;
    }

    public void setHasShared(int hasShared) {
        this.hasShared = hasShared;
    }

    public int getHasGot() {
        return hasGot;
    }

    public void setHasGot(int hasGot) {
        this.hasGot = hasGot;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
