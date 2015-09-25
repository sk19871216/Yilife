package com.jiuan.android.app.yilife.bean.threadbangding;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadBangdingStuRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/get_thirdparty_authorizationstatus";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un,accessToken;

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

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setAccessToken(accessToken);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_50_GET_THIRD_STATUS);
        }
        @SerializedName("UN")
        private String un;
        @SerializedName("AccessToken")
        private String accessToken;

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
    }

}
