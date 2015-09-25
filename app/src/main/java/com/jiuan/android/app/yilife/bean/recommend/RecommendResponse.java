package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.splashimage.MyReplyBean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendResponse {

    @SerializedName("Total") private double total;
    @SerializedName("FromLevel1") private double fromLevel1;
    @SerializedName("FromLevel2") private double fromLevel2;
    @SerializedName("FromLevel3") private double fromLevel3;
    @SerializedName("Level1Count") private int level1Count;
    @SerializedName("Level2Count") private int level2Count;
    @SerializedName("Level3Count") private int level3Count;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFromLevel1() {
        return fromLevel1;
    }

    public void setFromLevel1(double fromLevel1) {
        this.fromLevel1 = fromLevel1;
    }

    public double getFromLevel2() {
        return fromLevel2;
    }

    public void setFromLevel2(double fromLevel2) {
        this.fromLevel2 = fromLevel2;
    }

    public double getFromLevel3() {
        return fromLevel3;
    }

    public void setFromLevel3(double fromLevel3) {
        this.fromLevel3 = fromLevel3;
    }

    public int getLevel1Count() {
        return level1Count;
    }

    public void setLevel1Count(int level1Count) {
        this.level1Count = level1Count;
    }

    public int getLevel2Count() {
        return level2Count;
    }

    public void setLevel2Count(int level2Count) {
        this.level2Count = level2Count;
    }

    public int getLevel3Count() {
        return level3Count;
    }

    public void setLevel3Count(int level3Count) {
        this.level3Count = level3Count;
    }
}
