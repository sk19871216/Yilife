package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ReferralsDetailResponse {

    @SerializedName("HGUID") private String  HGUID;
    @SerializedName("NickName") private String nickName;
    @SerializedName("Mobile") private String mobile;
    @SerializedName("PhotoUrl") private String photoUrl;
    @SerializedName("RegisterTS") private long registerTS;
    @SerializedName("DirectRebate") private double directRebate;
    @SerializedName("Level1Rebate") private double level1Rebate;
    @SerializedName("Level2Rebate") private double level2Rebate;

    public String getHGUID() {
        return HGUID;
    }

    public void setHGUID(String HGUID) {
        this.HGUID = HGUID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getRegisterTS() {
        return registerTS;
    }

    public void setRegisterTS(long registerTS) {
        this.registerTS = registerTS;
    }

    public double getDirectRebate() {
        return directRebate;
    }

    public void setDirectRebate(double directRebate) {
        this.directRebate = directRebate;
    }

    public double getLevel1Rebate() {
        return level1Rebate;
    }

    public void setLevel1Rebate(double level1Rebate) {
        this.level1Rebate = level1Rebate;
    }

    public double getLevel2Rebate() {
        return level2Rebate;
    }

    public void setLevel2Rebate(double level2Rebate) {
        this.level2Rebate = level2Rebate;
    }
}
