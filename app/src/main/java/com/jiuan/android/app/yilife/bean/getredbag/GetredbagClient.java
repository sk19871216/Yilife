package com.jiuan.android.app.yilife.bean.getredbag;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class GetredbagClient {
    public static RequestHandle requestbalance(Context context, String phone, String tooken, GetredbagHandler responseHandler, boolean isTest) {
        GetredbagRequest helper = new GetredbagRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetredbagRequest.PATH_TEST;
        if (!isTest) {
            path = GetredbagRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle requestlist(Context context, String phone, String tooken,long ts, GetredbaglistHandler responseHandler, boolean isTest) {
        GetredbaglistRequest helper = new GetredbaglistRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setTs(ts);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetredbaglistRequest.PATH_TEST;
        if (!isTest) {
            path = GetredbaglistRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle isgetredbag(Context context, String phone, String tooken,String gprs, GetredbagisgetHandler responseHandler, boolean isTest) {
        GetredbagisgetRequest helper = new GetredbagisgetRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(phone);
        helper.setTooken(tooken);
        helper.setGprs(gprs);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = GetredbagisgetRequest.PATH_TEST;
        if (!isTest) {
            path = GetredbagRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
