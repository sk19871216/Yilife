package com.jiuan.android.app.yilife.bean.Duihuan;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class DuihuanClient {
    public static RequestHandle requestLogin(Context context,DuihuanHandler responseHandler, boolean isTest) {
        DuihuanRequest helper = new DuihuanRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = DuihuanRequest.PATH_TEST;
        if (!isTest) {
            path = DuihuanRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
