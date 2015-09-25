package com.jiuan.android.app.yilife.bean.MyDianPing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyDianPingResponse {

    @SerializedName("AppUniqueName") private String appUniqueName;
    @SerializedName("AppName") private String appName;
    @SerializedName("LogoPath") private String logoPath;
    @SerializedName("Title") private String title;
    @SerializedName("Content") private String neirong;
    @SerializedName("CreateTime") private long createTime;
    @SerializedName("OSType") private int OSType;
    @SerializedName("Rate") private double rate;

    public String getAppUniqueName() {
        return appUniqueName;
    }

    public void setAppUniqueName(String appUniqueName) {
        this.appUniqueName = appUniqueName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getOSType() {
        return OSType;
    }

    public void setOSType(int OSType) {
        this.OSType = OSType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
