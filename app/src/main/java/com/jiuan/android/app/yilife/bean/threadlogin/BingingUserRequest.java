package com.jiuan.android.app.yilife.bean.threadlogin;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class BingingUserRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/binding_thirdparty_user";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private ThreadInfoBean threadInfoBean;
    private String user;
    private String password;

    public ThreadInfoBean getThreadInfoBean() {
        return threadInfoBean;
    }

    public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
        this.threadInfoBean = threadInfoBean;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setPasswordd(password);
        body.setThreadInfoBean(threadInfoBean);
        body.setUser(user);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_47_BINGING_T_USER);
        }
        @SerializedName("ThirdPartyUser")
        private ThreadInfoBean threadInfoBean;
        @SerializedName("UN")
        private String user;
        @SerializedName("Password")
        private String passwordd;

        public ThreadInfoBean getThreadInfoBean() {
            return threadInfoBean;
        }

        public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
            this.threadInfoBean = threadInfoBean;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPasswordd() {
            return passwordd;
        }

        public void setPasswordd(String passwordd) {
            this.passwordd = passwordd;
        }
    }

}
