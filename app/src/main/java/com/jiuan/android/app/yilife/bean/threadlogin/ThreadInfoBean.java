package com.jiuan.android.app.yilife.bean.threadlogin;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kai on 2015/5/22.
 */
public class ThreadInfoBean {
    @SerializedName("OpenId") private String  openId;
    @SerializedName("NickName") private String nickName;
    @SerializedName("Gender") private int  gender;
    @SerializedName("Avatar") private String  avatar;
    @SerializedName("AvatarHd") private String avatarHd;
    @SerializedName("SourceType") private int sourceType;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatarHd) {
        this.avatarHd = avatarHd;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
}
