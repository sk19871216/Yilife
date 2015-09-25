package com.jiuan.android.app.yilife.bean.Huodong;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.activity.Huodong;
import com.jiuan.android.app.yilife.bean.JinghuaItem;

/**
 * Created by Administrator on 2015/1/6.
 */
public class HuodonglistResponse {
    @SerializedName("LeftSize") private int leftSize;
    @SerializedName("LastCamName") private String lastCamName;
    @SerializedName("Datas") private HuodongBean[] datas;

    public int getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
    }

    public String getLastCamName() {
        return lastCamName;
    }

    public void setLastCamName(String lastCamName) {
        this.lastCamName = lastCamName;
    }

    public HuodongBean[] getDatas() {
        return datas;
    }

    public void setDatas(HuodongBean[] datas) {
        this.datas = datas;
    }
}
