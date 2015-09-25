package com.jiuan.android.app.yilife.bean.threadbangding;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadBangdingStuResponse {

    @SerializedName("WeixinStatus") private int  weixinStatus;
    @SerializedName("QQStatus") private int  QQStatus   ;
    @SerializedName("SinaStatus") private int  sinaStatus;
    @SerializedName("ZhiFuBaoStatus") private int  zhiFuBaoStatus;

    public int getWeixinStatus() {
        return weixinStatus;
    }

    public void setWeixinStatus(int weixinStatus) {
        this.weixinStatus = weixinStatus;
    }

    public int getQQStatus() {
        return QQStatus;
    }

    public void setQQStatus(int QQStatus) {
        this.QQStatus = QQStatus;
    }

    public int getSinaStatus() {
        return sinaStatus;
    }

    public void setSinaStatus(int sinaStatus) {
        this.sinaStatus = sinaStatus;
    }

    public int getZhiFuBaoStatus() {
        return zhiFuBaoStatus;
    }

    public void setZhiFuBaoStatus(int zhiFuBaoStatus) {
        this.zhiFuBaoStatus = zhiFuBaoStatus;
    }
}
