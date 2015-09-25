package com.jiuan.android.app.yilife.bean.GetLink;

import com.google.gson.annotations.SerializedName;
import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetLinkRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/recommend/get_shareapp_url";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String hguid;

    public String getHguid() {
        return hguid;
    }

    public void setHguid(String hguid) {
        this.hguid = hguid;
    }

    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();
        body.setHGUID(hguid);
        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{

        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_67_get_shareapp_url);
        }

        @SerializedName("HGUID")
        private String HGUID;

        public String getHGUID() {
            return HGUID;
        }

        public void setHGUID(String HGUID) {
            this.HGUID = HGUID;
        }
    }

}
