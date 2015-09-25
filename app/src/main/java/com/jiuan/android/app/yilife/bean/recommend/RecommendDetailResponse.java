package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendDetailResponse {

    @SerializedName("Rebates") private RecommendDetailBean[] tebates;
    @SerializedName("LastOrderTS") private long LastOrderTS;
    @SerializedName("LeftSize") private int LeftSize;

    public RecommendDetailBean[] getTebates() {
        return tebates;
    }

    public void setTebates(RecommendDetailBean[] tebates) {
        this.tebates = tebates;
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
