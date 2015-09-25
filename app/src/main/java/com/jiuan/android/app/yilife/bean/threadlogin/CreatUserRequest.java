package com.jiuan.android.app.yilife.bean.threadlogin;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CreatUserRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/create_thirdparty_user";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private ThreadInfoBean threadInfoBean;


    public ThreadInfoBean getThreadInfoBean() {
        return threadInfoBean;
    }

    public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
        this.threadInfoBean = threadInfoBean;
    }



    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setThreadInfoBean(threadInfoBean);

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_48_CREAT_T_USER);
        }
        @SerializedName("ThirdPartyUser")
        private ThreadInfoBean threadInfoBean;


        public ThreadInfoBean getThreadInfoBean() {
            return threadInfoBean;
        }

        public void setThreadInfoBean(ThreadInfoBean threadInfoBean) {
            this.threadInfoBean = threadInfoBean;
        }

    }

}
