package com.jiuan.android.app.yilife.bean.isredbagshare;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class IsRedbagShareResponse {

    @SerializedName("IsSuccess") private int isSuccess;

    @SerializedName("Errors") private String errors;

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
