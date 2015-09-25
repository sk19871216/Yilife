package com.jiuan.android.app.yilife.bean.Duihuan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/1/30.
 */
public class DuihuanBean {
    @SerializedName("ProductID") private int productID;
    @SerializedName("Name") private String name;
    @SerializedName("Description") private String description;
    @SerializedName("Price") private double price;
    @SerializedName("Logo") private String logo;

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
}
