package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class RecommendReferralsRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/recommend/get_direct_referrals";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un;
    private String tooken;
    private long ts;
    private int pagesize;

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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setTooken(tooken);
        body.setRegisterTS(ts);
        body.setPageSize(pagesize);


        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_65_get_direct_referrals);
        }
        @SerializedName("UN")
        private String un;

        @SerializedName("AccessToken")
        private String tooken;

        @SerializedName("RelevanceTS")
        private long registerTS;

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

        public long getRegisterTS() {
            return registerTS;
        }

        public void setRegisterTS(long registerTS) {
            this.registerTS = registerTS;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

}
