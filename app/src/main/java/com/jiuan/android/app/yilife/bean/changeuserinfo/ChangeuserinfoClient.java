package com.jiuan.android.app.yilife.bean.changeuserinfo;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChangeuserinfoClient {
    public static RequestHandle requestLogin(Context context, String name,String tooken,UserInfo[] array, ChangeuserinfoHandler responseHandler, boolean isTest) {
        ChangeuserinfoRequest helper = new ChangeuserinfoRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(name);
        helper.setTooken(tooken);
        helper.setArray(array);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ChangeuserinfoRequest.PATH_TEST;
        if (!isTest) {
            path = ChangeuserinfoRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static RequestHandle changeforuminfo(Context context, String name,String tooken,UserInfo[] array, ChangeuserinfoHandler responseHandler, boolean isTest) {
        ChangeForumuserinfoRequest helper = new ChangeForumuserinfoRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setPhone(name);
        helper.setTooken(tooken);
        helper.setArray(array);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ChangeForumuserinfoRequest.PATH_TEST;
        if (!isTest) {
            path = ChangeForumuserinfoRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
