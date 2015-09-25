package com.jiuan.android.app.yilife.bean.getpinglunlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/28.
 */
public class PinglunlistBean {
    @SerializedName("Title") private String title;
    @SerializedName("Content") private String content;
    @SerializedName("FromUser") private String fromUser;
    @SerializedName("CreateTime") private long createTime;
    @SerializedName("Rate") private double rate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
