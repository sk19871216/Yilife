package com.jiuan.android.app.yilife.bean.login;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class LoginClient{
    public static RequestHandle requestLogin(Context context, String phone, String password, LoginHandler responseHandler, boolean isTest) {
        LoginRequest helper = new LoginRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = LoginRequest.PATH_TEST;
        if (!isTest) {
            path = LoginRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle refreshtooken(Context context, String phone, String password, RefreshTookenHandler responseHandler, boolean isTest) {
        RefreshTookenRequest helper = new RefreshTookenRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setPassword(password);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = RefreshTookenRequest.PATH_TEST;
        if (!isTest) {
            path = RefreshTookenRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
