package com.jiuan.android.app.yilife.bean.CheckVersion;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.Duihuan.DuihuanBean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CheckVersionResponse {

    @SerializedName("VersionNumber") private String versionNumber;
    @SerializedName("IsNewVersion") private int isNewVersion;
    @SerializedName("IsCompulsoryUpgrade") private int isCompulsoryUpgrade;
    @SerializedName("IsPublished") private int isPublished;
    @SerializedName("UpdateContent") private String updateContent;
    @SerializedName("DownLoadUrl") private String downLoadUrl;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getIsNewVersion() {
        return isNewVersion;
    }

    public void setIsNewVersion(int isNewVersion) {
        this.isNewVersion = isNewVersion;
    }

    public int getIsCompulsoryUpgrade() {
        return isCompulsoryUpgrade;
    }

    public void setIsCompulsoryUpgrade(int isCompulsoryUpgrade) {
        this.isCompulsoryUpgrade = isCompulsoryUpgrade;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
