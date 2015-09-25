package com.jiuan.android.app.yilife.bean.splashimage;

import com.innovation.android.library.http.InnovationRequestBody;
import com.innovation.android.library.http.InnovationRequestImpl;
import com.jiuan.android.app.yilife.config.ScAndSv;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SplashimageRequest extends InnovationRequestImpl {
    private static final String PATH_OA_LOGIN = "/iemylife/get_index";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;



    @Override
    protected InnovationRequestBody getInnovationRequestBody() {
        LoginRequestBody body = new LoginRequestBody();

        return body;
    }

    private class LoginRequestBody extends InnovationRequestBody{
        private LoginRequestBody() {
            setSc(ScAndSv.SC);
            setSv(ScAndSv.SV_14_THE_HOME_PAGE);
        }

    }

}
