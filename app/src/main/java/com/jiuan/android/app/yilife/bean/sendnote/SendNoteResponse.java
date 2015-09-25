package com.jiuan.android.app.yilife.bean.sendnote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SendNoteResponse {

    @SerializedName("Aid") private int aid;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
