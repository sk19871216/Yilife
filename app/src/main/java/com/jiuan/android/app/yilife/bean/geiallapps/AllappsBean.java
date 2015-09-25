package com.jiuan.android.app.yilife.bean.geiallapps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/28.
 */
public class AllappsBean {
    @SerializedName("AppUniqueName") private String appUniqueName;
    @SerializedName("AppName") private String appName;
    @SerializedName("LogoPath") private String logoPath;
    @SerializedName("DownloadPath") private String downloadPath;
    @SerializedName("Attributes") private int[] attributes;
    @SerializedName("Version") private String version;
    @SerializedName("VersionName") private String versionName;
    @SerializedName("AppIdentifier") private String appIdentitifier;
    @SerializedName("Keywords") private String[] keywords;
    @SerializedName("Rate") private double rate;
    private String downstats;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppIdentitifier() {
        return appIdentitifier;
    }

    public void setAppIdentitifier(String appIdentitifier) {
        this.appIdentitifier = appIdentitifier;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDownstats() {
        return downstats;
    }

    public void setDownstats(String downstats) {
        this.downstats = downstats;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
