package com.jiuan.android.app.yilife.bean.sendpinglun;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class SendpinglunClient {
    public static RequestHandle request(Context context,String phone,String tooken,String appname,String title,String neirong,int rate,SendpinglunHandler responseHandler, boolean isTest) {
        SendpinglunRequest helper = new SendpinglunRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setAppname(appname);
        helper.setTitle(title);
        helper.setNeirong(neirong);
        helper.setRate(rate);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = SendpinglunRequest.PATH_TEST;
        if (!isTest) {
            path = SendpinglunRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
