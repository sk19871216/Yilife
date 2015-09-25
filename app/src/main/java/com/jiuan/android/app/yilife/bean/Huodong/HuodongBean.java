package com.jiuan.android.app.yilife.bean.Huodong;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kai on 2015/8/12.
 */
public class HuodongBean {
    @SerializedName("CamName") private String camName;
    @SerializedName("DisplayName") private String displayName;
    @SerializedName("BannerForApp") private String bannerForApp;
    @SerializedName("StartTime") private long startTime;
    @SerializedName("EndTime") private long endTime;
    @SerializedName("State") private int state;
    @SerializedName("CampaignID") private int campaignID;
    @SerializedName("CamH5Url") private String camH5Url;
    @SerializedName("CamDesc") private String camDesc;
    @SerializedName("CamIcon") private String camIcon;

    public String getCamName() {
        return camName;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBannerForApp() {
        return bannerForApp;
    }

    public void setBannerForApp(String bannerForApp) {
        this.bannerForApp = bannerForApp;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCamH5Url() {
        return camH5Url;
    }

    public void setCamH5Url(String camH5Url) {
        this.camH5Url = camH5Url;
    }

    public String getCamDesc() {
        return camDesc;
    }

    public void setCamDesc(String camDesc) {
        this.camDesc = camDesc;
    }

    public String getCamIcon() {
        return camIcon;
    }

    public void setCamIcon(String camIcon) {
        this.camIcon = camIcon;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }
}
