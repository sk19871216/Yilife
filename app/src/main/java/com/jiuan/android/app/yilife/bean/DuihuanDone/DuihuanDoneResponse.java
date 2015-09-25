package com.jiuan.android.app.yilife.bean.DuihuanDone;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/6.
 */
public class DuihuanDoneResponse {

    @SerializedName("TotalPrice") private double TotalPrice;
    @SerializedName("OrderNumber") private String orderNumber;

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
