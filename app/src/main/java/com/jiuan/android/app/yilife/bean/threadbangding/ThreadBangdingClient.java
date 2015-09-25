package com.jiuan.android.app.yilife.bean.threadbangding;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.android.app.yilife.bean.threadlogin.ThreadInfoBean;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ThreadBangdingClient {
    public static RequestHandle showthirdpartystatus(Context context,String un,String tooken, ThreadBangdingStuHandler responseHandler, boolean isTest) {
        ThreadBangdingStuRequest helper = new ThreadBangdingStuRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setAccessToken(tooken);

        InnovationClient client = InnovationClient.getInstance();

        client.setSSLSocketFactory();

        String path = ThreadBangdingStuRequest.PATH_TEST;
        if (!isTest) {
            path = ThreadBangdingStuRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle unbangdingThird(Context context,String un,String tooken,int sourceType, ThreadUnBangdingHandler creatUserHandler, boolean isTest) {
        ThreadUnBangdingRequest helper = new ThreadUnBangdingRequest();

        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setAccessToken(tooken);
        helper.setSourceType(sourceType);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();
//        client.getClient().setTimeout(30000);


        String path = ThreadUnBangdingRequest.PATH_TEST;
        if (!isTest) {
            path = ThreadUnBangdingRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), creatUserHandler);
    }



    public static RequestHandle bangdingThird(Context context,ThreadInfoBean threadInfoBean,String un,int isVerify, ThreadBangdingHandler creatUserHandler, boolean isTest) {
        ThreadBangdingRequest helper = new ThreadBangdingRequest();

        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setThreadInfoBean(threadInfoBean);
        helper.setIsVerify(isVerify);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();
//        client.getClient().setTimeout(30000);


        String path = ThreadBangdingRequest.PATH_TEST;
        if (!isTest) {
            path = ThreadBangdingRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), creatUserHandler);
    }


    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
