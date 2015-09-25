package com.jiuan.android.app.yilife.bean.CheckVersion;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/1/6.
 */
public class CheckVersionRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/api/Check_Versions";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String versionNumber,systemEnvironments,versionParameter;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getSystemEnvironments() {
        return systemEnvironments;
    }

    public void setSystemEnvironments(String systemEnvironments) {
        this.systemEnvironments = systemEnvironments;
    }

    public String getVersionParameter() {
        return versionParameter;
    }

    public void setVersionParameter(String versionParameter) {
        this.versionParameter = versionParameter;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setVersionNumber(versionNumber);
        body.setSystemEnvironments(systemEnvironments);
        body.setVersionParameter(versionParameter);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_62_Check_Versions);
        }

        @SerializedName("VersionNumber")
        private String versionNumber;
        @SerializedName("SystemEnvironments")
        private String systemEnvironments;
        @SerializedName("VersionParameter")
        private String versionParameter;

        public String getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(String versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getSystemEnvironments() {
            return systemEnvironments;
        }

        public void setSystemEnvironments(String systemEnvironments) {
            this.systemEnvironments = systemEnvironments;
        }

        public String getVersionParameter() {
            return versionParameter;
        }

        public void setVersionParameter(String versionParameter) {
            this.versionParameter = versionParameter;
        }
    }

}
