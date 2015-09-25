package com.jiuan.android.app.yilife.bean.DuihuanDone;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class DuihuanDoneRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/get_envelope_order";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;
    private String tooken;
    private int productID;
    private int quantity;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setProductID(productID);
        body.setQuantity(quantity);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_36_GET_ENVELOPE_ORDER);
        }
        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("ProductID")
        private int productID;

        @SerializedName("Quantity")
        private int quantity;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTooken() {
            return tooken;
        }

        public void setTooken(String tooken) {
            this.tooken = tooken;
        }

        public int getProductID() {
            return productID;
        }

        public void setProductID(int productID) {
            this.productID = productID;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
