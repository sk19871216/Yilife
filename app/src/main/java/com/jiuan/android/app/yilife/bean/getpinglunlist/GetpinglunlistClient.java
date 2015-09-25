package com.jiuan.android.app.yilife.bean.getpinglunlist;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetpinglunlistClient {
    public static RequestHandle request(Context context,String name,int pagesize,long ts,GetpinglunlistHandler responseHandler, boolean isTest) {
        GetpinglunlistRequest helper = new GetpinglunlistRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setName(name);
        helper.setPageSize(pagesize);
        helper.setTs(ts);

        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetpinglunlistRequest.PATH_TEST;
        if (!isTest) {
            path = GetpinglunlistRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
