package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendReferralsResponse {

    @SerializedName("DirectReferrals") private RecommendReferralsBean[] DirectReferrals;
    @SerializedName("LastOrderTS") private long LastOrderTS;
    @SerializedName("LeftSize") private int LeftSize;

    public RecommendReferralsBean[] getDirectReferrals() {
        return DirectReferrals;
    }

    public void setDirectReferrals(RecommendReferralsBean[] directReferrals) {
        DirectReferrals = directReferrals;
    }

    public long getLastOrderTS() {
        return LastOrderTS;
    }

    public void setLastOrderTS(long lastOrderTS) {
        LastOrderTS = lastOrderTS;
    }

    public int getLeftSize() {
        return LeftSize;
    }

    public void setLeftSize(int leftSize) {
        LeftSize = leftSize;
    }
}
