package com.jiuan.android.app.yilife.bean.BangdingPhone;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangePhoneCodeRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/change_alter_phone";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;
    private String un,accessToken,newPhone,oldCode,newCode;

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

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setAccessToken(accessToken);
        body.setNewPhone(newPhone);
        body.setOldCode(oldCode);
        body.setNewCode(newCode);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_54_change_alter_phone);
        }

        @SerializedName("UN")
        private String un;

        @SerializedName("AccessToken")
        private String accessToken;

        @SerializedName("NewPhone")
        private String newPhone;

        @SerializedName("OldCode")
        private String oldCode;

        @SerializedName("NewCode")
        private String newCode;

        public String getNewCode() {
            return newCode;
        }

        public void setNewCode(String newCode) {
            this.newCode = newCode;
        }

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

        public String getNewPhone() {
            return newPhone;
        }

        public void setNewPhone(String newPhone) {
            this.newPhone = newPhone;
        }

        public String getOldCode() {
            return oldCode;
        }

        public void setOldCode(String oldCode) {
            this.oldCode = oldCode;
        }
    }

}
