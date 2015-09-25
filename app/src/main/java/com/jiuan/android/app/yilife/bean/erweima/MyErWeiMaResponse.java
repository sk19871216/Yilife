package com.jiuan.android.app.yilife.bean.erweima;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyErWeiMaResponse {

    @SerializedName("QRCodeUrl") private String qRCodeUrl;
    @SerializedName("QRCodeContent") private String qRCodeContent;
    @SerializedName("MobilePhone") private String mobilePhone;
    @SerializedName("Nickname") private String nickname;
    @SerializedName("PhotoUrl") private String photoUrl;
    @SerializedName("InvitationCode") private String invitationCode;
    @SerializedName("IsInvited") private boolean isInvited;

    public String getqRCodeUrl() {
        return qRCodeUrl;
    }

    public void setqRCodeUrl(String qRCodeUrl) {
        this.qRCodeUrl = qRCodeUrl;
    }

    public String getqRCodeContent() {
        return qRCodeContent;
    }

    public void setqRCodeContent(String qRCodeContent) {
        this.qRCodeContent = qRCodeContent;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setIsInvited(boolean isInvited) {
        this.isInvited = isInvited;
    }
}
