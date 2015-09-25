package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendReferralsBean {

    @SerializedName("HGUID") private String hguid;
    @SerializedName("NickName") private String nickName;
    @SerializedName("Mobile") private String mobile;
    @SerializedName("PhotoUrl") private String photoUrl;
    @SerializedName("RelevanceTS") private long registerTS;

    public String getHguid() {
        return hguid;
    }

    public void setHguid(String hguid) {
        this.hguid = hguid;
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
}
