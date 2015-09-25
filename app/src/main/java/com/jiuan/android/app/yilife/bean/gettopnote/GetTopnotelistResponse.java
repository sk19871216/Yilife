package com.jiuan.android.app.yilife.bean.gettopnote;

import com.google.gson.annotations.SerializedName;
import com.jiuan.android.app.yilife.bean.BBsNoteListBean;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetTopnotelistResponse {

    @SerializedName("Datas") private BBsNoteListBean[] bean;



    public BBsNoteListBean[] getBean() {
        return bean;
    }

    public void setBean(BBsNoteListBean[] bean) {
        this.bean = bean;
    }

}
