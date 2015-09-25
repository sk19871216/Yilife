package com.jiuan.android.app.yilife.bean.getuserinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetForumuserinfoResponse {

    @SerializedName("NickName") private String nickName;
    @SerializedName("Avatars") private String avatars;
    @SerializedName("PhoneNumber") private String phone;
    @SerializedName("RealName") private String realName;
    @SerializedName("QQ") private String qq;
    @SerializedName("Sex") private int sex;
    @SerializedName("Birthday") private String birthday;
    @SerializedName("EmailAddress") private String emailAddress   ;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
