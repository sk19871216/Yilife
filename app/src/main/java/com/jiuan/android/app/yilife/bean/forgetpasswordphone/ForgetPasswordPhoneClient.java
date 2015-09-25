package com.jiuan.android.app.yilife.bean.forgetpasswordphone;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ForgetPasswordPhoneClient {
    public static RequestHandle requestLogin(Context context, String phone, ForgetPasswordPhoneHandler responseHandler, boolean isTest) {
        ForgetPasswordPhoneRequest helper = new ForgetPasswordPhoneRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ForgetPasswordPhoneRequest.PATH_TEST;
        if (!isTest) {
            path = ForgetPasswordPhoneRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
