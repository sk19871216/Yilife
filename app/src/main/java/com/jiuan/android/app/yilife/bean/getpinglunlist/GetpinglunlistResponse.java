package com.jiuan.android.app.yilife.bean.getpinglunlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetpinglunlistResponse {

    @SerializedName("Datas") private PinglunlistBean[] bean;
    @SerializedName("LeftSize") private int leftSize;

    public PinglunlistBean[] getBean() {
        return bean;
    }

    public void setBean(PinglunlistBean[] bean) {
        this.bean = bean;
    }

    public int getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
    }
}
