package com.jiuan.android.app.yilife.bean.jinghualist;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class JinghualistClient {
    public static RequestHandle request(Context context, JinghualistHandler responseHandler, boolean isTest) {
        JinghualistRequest helper = new JinghualistRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = JinghualistRequest.PATH_TEST;
        if (!isTest) {
            path = JinghualistRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
