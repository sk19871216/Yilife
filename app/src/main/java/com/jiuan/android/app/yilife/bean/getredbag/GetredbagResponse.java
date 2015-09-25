package com.jiuan.android.app.yilife.bean.getredbag;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetredbagResponse {
    @SerializedName("Datas") private  GetRedBagListBean[] beans;


    public GetRedBagListBean[] getBeans() {
        return beans;
    }

    public void setBeans(GetRedBagListBean[] beans) {
        this.beans = beans;
    }
}
