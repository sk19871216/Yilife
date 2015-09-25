package com.jiuan.android.app.yilife.bean.recommend;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ReferralsDetailRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/recommend/get_referrals_detail";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un;
    private String tooken;
    private String  hguid;

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

    public String getHguid() {
        return hguid;
    }

    public void setHguid(String hguid) {
        this.hguid = hguid;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setTooken(tooken);
        body.setReferralHGUID(hguid);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_66_get_referrals_detail);
        }
        @SerializedName("UN")
        private String un;

        @SerializedName("AccessToken")
        private String tooken;


        @SerializedName("RecommendedHGUID")
        private String referralHGUID;

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

        public String getReferralHGUID() {
            return referralHGUID;
        }

        public void setReferralHGUID(String referralHGUID) {
            this.referralHGUID = referralHGUID;
        }
    }

}
