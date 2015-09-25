package com.jiuan.android.app.yilife.bean.BangdingPhone;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class NewPhoneRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/binding_phone";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un,tooken,code,phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setCode(code);
        body.setAccessToken(tooken);
        body.setUN(un);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_55_binding_phone);
        }
        @SerializedName("UN")
        private String UN;

        @SerializedName("AccessToken")
        private String accessToken;

        @SerializedName("Phone")
        private String phone;

        @SerializedName("Code")
        private String code;

        public String getUN() {
            return UN;
        }

        public void setUN(String UN) {
            this.UN = UN;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
