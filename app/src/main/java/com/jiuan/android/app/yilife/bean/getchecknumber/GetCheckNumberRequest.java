package com.jiuan.android.app.yilife.bean.getchecknumber;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetCheckNumberRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/refresh_token";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;

    private String freshtooken;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFreshtooken() {
        return freshtooken;
    }

    public void setFreshtooken(String freshtooken) {
        this.freshtooken = freshtooken;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setFreshtooken(freshtooken);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_8_FRESH_TOKEN);
        }

        @SerializedName("UN")
        private String phone;

        @SerializedName("RefreshToken")
        private String freshtooken;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFreshtooken() {
            return freshtooken;
        }

        public void setFreshtooken(String freshtooken) {
            this.freshtooken = freshtooken;
        }
    }

}
