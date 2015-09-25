package com.jiuan.android.app.yilife.bean.threadbangding;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadInfoBean;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadBangdingRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/userCenter_binding_thirdparty";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String un;

    private ThreadInfoBean threadInfoBean;

    private int isVerify;

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public ThreadInfoBean getThreadInfoBean() {
        return threadInfoBean;
    }

    public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
        this.threadInfoBean = threadInfoBean;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        isVerify = isVerify;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setUn(un);
        body.setThreadInfoBean(threadInfoBean);
        body.setIsVerify(isVerify);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_51_BANGDING);
        }
        @SerializedName("UN")
        private String un;
        @SerializedName("ThirdPartyUser")
        private ThreadInfoBean threadInfoBean;
        @SerializedName("IsVerify")
        private int isVerify;

        public String getUn() {
            return un;
        }

        public void setUn(String un) {
            this.un = un;
        }

        public ThreadInfoBean getThreadInfoBean() {
            return threadInfoBean;
        }

        public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
            this.threadInfoBean = threadInfoBean;
        }

        public int getIsVerify() {
            return isVerify;
        }

        public void setIsVerify(int isVerify) {
            this.isVerify = isVerify;
        }
    }

}
