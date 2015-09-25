package com.jiuan.android.app.yilife.bean.getappdetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetAppsDetailResponse {

    @SerializedName("AppUniqueName") private String appUniqueName;
    @SerializedName("AppName") private String appName;
    @SerializedName("Screenshots") private String[] screenshots;
    @SerializedName("Size") private String size;
    @SerializedName("DownloadCounter") private int downloadCounter;
    @SerializedName("ShortDescription") private String shortDescription;
    @SerializedName("FullDescription") private String fullDescription;
    @SerializedName("Rate") private double rate;
    @SerializedName("Comments") private int comments;
    @SerializedName("PublishTS") private long publishTS;
    @SerializedName("Version") private String version;
    @SerializedName("VersionName") private String versionname;
    @SerializedName("AppIdentifier") private String appIdentifier;
    @SerializedName("Language") private String language;
    @SerializedName("Supporting") private String supporting;
    @SerializedName("Vendor") private String vendor;
    @SerializedName("DownloadUrl") private String downloadUrl;
    @SerializedName("Categories") private String[] categories;
    @SerializedName("Attributes") private int[] attributes;

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
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

    public String[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(String[] screenshots) {
        this.screenshots = screenshots;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDownloadCounter() {
        return downloadCounter;
    }

    public void setDownloadCounter(int downloadCounter) {
        this.downloadCounter = downloadCounter;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSupporting() {
        return supporting;
    }

    public void setSupporting(String supporting) {
        this.supporting = supporting;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public long getPublishTS() {
        return publishTS;
    }

    public void setPublishTS(long publishTS) {
        this.publishTS = publishTS;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }
}
