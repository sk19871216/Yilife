package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendDetailRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/recommend/get_rebate_detail";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un;
    private String tooken;
    private long orderTS;
    private int pageSize;

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getOrderTS() {
        return orderTS;
    }

    public void setOrderTS(long orderTS) {
        this.orderTS = orderTS;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setTooken(tooken);
        body.setOrderTS(orderTS);
        body.setPageSize(pageSize);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_64_get_tebate_detail);
        }
        @SerializedName("UN")
        private String un;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("OrderTS")
        private long orderTS;

        @SerializedName("PageSize")
        private int pageSize;

        public String getUn() {
            return un;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public String getTooken() {
            return tooken;
        }

        public void setTooken(String tooken) {
            this.tooken = tooken;
        }

        public long getOrderTS() {
            return orderTS;
        }

        public void setOrderTS(long orderTS) {
            this.orderTS = orderTS;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

}
