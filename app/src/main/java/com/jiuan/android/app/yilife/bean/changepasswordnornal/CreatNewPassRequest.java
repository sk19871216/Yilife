package com.jiuan.android.app.yilife.bean.changepasswordnornal;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CreatNewPassRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/set_password";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un;

    private String tooken;

    private String password;


    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
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

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setTooken(tooken);
        body.setPassword(password);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_56_set_password);
        }

        @SerializedName("UN")
        private String un;

        @SerializedName("AccessToken")
        private String tooken ;

        @SerializedName("Password")
        private String password;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
