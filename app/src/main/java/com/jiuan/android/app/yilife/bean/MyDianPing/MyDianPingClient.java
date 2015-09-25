package com.jiuan.android.app.yilife.bean.MyDianPing;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyDianPingClient {
    public static RequestHandle request(Context context,String un,String tooken, MyDianPingHandler responseHandler, boolean isTest) {
        MyDianPingRequest helper = new MyDianPingRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);




        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = MyDianPingRequest.PATH_TEST;
        if (!isTest) {
            path = MyDianPingRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
