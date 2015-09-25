package com.jiuan.android.app.yilife.bean.getuserinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetuserinfoResponse {

    @SerializedName("HGUID") private String hguid;

    @SerializedName("Phone") private String phone;
    @SerializedName("Logo") private String logo;
    @SerializedName("UserName") private String userName;
    @SerializedName("HasPassword") private int hasPassword;
    @SerializedName("Sex") private String sex;
    @SerializedName("Birthday") private String birthday;
    @SerializedName("InvitationCode") private String invitationCode;
    @SerializedName("Address") private String addr;

    public String getHguid() {
        return hguid;
    }

    public void setHguid(String hguid) {
        this.hguid = hguid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(int hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
