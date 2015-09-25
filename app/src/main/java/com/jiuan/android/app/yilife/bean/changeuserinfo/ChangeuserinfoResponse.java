package com.jiuan.android.app.yilife.bean.changeuserinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangeuserinfoResponse {

    @SerializedName("IsSuccess") private int IsSuccess;

    @SerializedName("Errors") private String[] Errors;

    public int getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        IsSuccess = isSuccess;
    }

    public String[] getErrors() {
        return Errors;
    }

    public void setErrors(String[] errors) {
        Errors = errors;
    }
}
