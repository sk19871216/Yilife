package com.jiuan.android.app.yilife.bean.threadlogin;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadLoginClient {
    public static RequestHandle verfityopenid(Context context,String openid, VerfityOpenIdHandler responseHandler, boolean isTest) {
        VerfityOpenIdRequest helper = new VerfityOpenIdRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setOpenid(openid);

        InnovationClient client = InnovationClient.getInstance();

        client.setSSLSocketFactory();

        String path = VerfityOpenIdRequest.PATH_TEST;
        if (!isTest) {
            path = VerfityOpenIdRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle creatuser(Context context,ThreadInfoBean threadInfoBean, CreatUserHandler creatUserHandler, boolean isTest) {
        CreatUserRequest helper = new CreatUserRequest();

        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setThreadInfoBean(threadInfoBean);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();
//        client.getClient().setTimeout(30000);


        String path = CreatUserRequest.PATH_TEST;
        if (!isTest) {
            path = CreatUserRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), creatUserHandler);
    }
    public static RequestHandle binginguser(Context context,ThreadInfoBean threadInfoBean,String user,String password, BingingUserHandler bingingUserHandler, boolean isTest) {
        BingingUserRequest helper = new BingingUserRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setThreadInfoBean(threadInfoBean);
        helper.setUser(user);
        helper.setPassword(password);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();
        client.getClient().setConnectTimeout(30000);

        String path = BingingUserRequest.PATH_TEST;
        if (!isTest) {
            path = BingingUserRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), bingingUserHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
