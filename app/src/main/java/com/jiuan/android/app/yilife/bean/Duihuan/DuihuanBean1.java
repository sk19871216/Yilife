package com.jiuan.android.app.yilife.bean.Duihuan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/30.
 */
public class DuihuanBean1 {
    @SerializedName("ProductID") private int productID;
    @SerializedName("Name") private String name;
    @SerializedName("Description") private String description;
    @SerializedName("Price") private double price;
    @SerializedName("Logo") private String logo;
    private int p;
    private double mymoney;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public double getMymoney() {
        return mymoney;
    }

    public void setMymoney(double mymoney) {
        this.mymoney = mymoney;
    }
}
