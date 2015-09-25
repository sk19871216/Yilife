package com.jiuan.android.app.yilife.bean.forgetdone;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ForgetDoneClient {
    public static RequestHandle requestLogin(Context context, String phone,String code,String password, ForgetDoneHandler responseHandler, boolean isTest) {
        ForgetDoneRequest helper = new ForgetDoneRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setCode(code);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ForgetDoneRequest.PATH_TEST;
        if (!isTest) {
            path = ForgetDoneRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
