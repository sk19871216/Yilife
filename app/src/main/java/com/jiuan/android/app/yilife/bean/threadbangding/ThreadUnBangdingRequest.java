package com.jiuan.android.app.yilife.bean.threadbangding;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadUnBangdingRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/unbundling_thirdparty";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un,accessToken;

    private int SourceType;

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int sourceType) {
        SourceType = sourceType;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setAccessToken(accessToken);
        body.setSourceType(SourceType);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_49_UNBANGDING_THIRD);
        }
        @SerializedName("UN")
        private String un;
        @SerializedName("AccessToken")
        private String accessToken;
        @SerializedName("SourceType")
        private int sourceType;

        public String getUn() {
            return un;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }
    }

}
