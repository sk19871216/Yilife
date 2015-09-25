package com.jiuan.android.app.yilife.bean.changepasswordnornal;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangepasswordnornalRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/change_password";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String phone;

    private String tooken;

    private String oldpassword;

    private String password;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPhone(phone);
        body.setTooken(tooken);
        body.setOldpassword(oldpassword);
        body.setPassword(password);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_13_CHANGE_PASSWORD);
        }

        @SerializedName("UN")
        private String phone;

        @SerializedName("AccessToken")
        private String tooken ;

        @SerializedName("OldPassword")
        private String oldpassword;

        @SerializedName("NewPassword")
        private String password;

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

        public String getOldpassword() {
            return oldpassword;
        }

        public void setOldpassword(String oldpassword) {
            this.oldpassword = oldpassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
