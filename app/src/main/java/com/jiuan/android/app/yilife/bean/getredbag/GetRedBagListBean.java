package com.jiuan.android.app.yilife.bean.getredbag;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/30.
 */
public class GetRedBagListBean {
    @SerializedName("CreateTS") private long createTS;
    @SerializedName("Money") private double money;
    @SerializedName("Type") private int type;
    @SerializedName("Campaign") private String campaign;
    @SerializedName("FromUser") private String fromUser;

    public long getCreateTS() {
        return createTS;
    }

    public void setCreateTS(long createTS) {
        this.createTS = createTS;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
