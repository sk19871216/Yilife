package com.jiuan.android.app.yilife.bean.Duihuan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class DuihuanResponse {

    @SerializedName("Datas") private DuihuanBean[] datas;

    public DuihuanBean[] getDatas() {
        return datas;
    }

    public void setDatas(DuihuanBean[] datas) {
        this.datas = datas;
    }
}
