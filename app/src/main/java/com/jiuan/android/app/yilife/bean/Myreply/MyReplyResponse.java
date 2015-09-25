package com.jiuan.android.app.yilife.bean.Myreply;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.splashimage.MyReplyBean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyReplyResponse {

    @SerializedName("LastPostTS") private long lastPostTS;
    @SerializedName("LeftSize") private int leftSize;
    @SerializedName("Datas") private MyReplyBean[] bean;

    public long getLastPostTS() {
        return lastPostTS;
    }

    public void setLastPostTS(long lastPostTS) {
        this.lastPostTS = lastPostTS;
    }

    public int getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
    }

    public MyReplyBean[] getBean() {
        return bean;
    }

    public void setBean(MyReplyBean[] bean) {
        this.bean = bean;
    }
}
