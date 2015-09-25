package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendDetailBean {

    @SerializedName("HGUID") private String  HGUID;
    @SerializedName("NickName") private String displayName;
    @SerializedName("PhotoUrl") private String photoUrl;
    @SerializedName("Quantity") private double quantity;
    @SerializedName("Mobile") private String mobile;
    @SerializedName("IsLower") private boolean isLower;
    @SerializedName("OrderTS") private long OrderTS;

    public String getHGUID() {
        return HGUID;
    }

    public void setHGUID(String HGUID) {
        this.HGUID = HGUID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean isLower() {
        return isLower;
    }

    public void setIsLower(boolean isLower) {
        this.isLower = isLower;
    }

    public long getOrderTS() {
        return OrderTS;
    }

    public void setOrderTS(long orderTS) {
        OrderTS = orderTS;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
