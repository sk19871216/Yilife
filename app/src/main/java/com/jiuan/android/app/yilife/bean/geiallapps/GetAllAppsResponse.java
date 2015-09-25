package com.jiuan.android.app.yilife.bean.geiallapps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetAllAppsResponse {

    @SerializedName("Datas") private AllappsBean[] datas;

    public AllappsBean[] getDatas() {
        return datas;
    }

    public void setDatas(AllappsBean[] datas) {
        this.datas = datas;
    }
}
