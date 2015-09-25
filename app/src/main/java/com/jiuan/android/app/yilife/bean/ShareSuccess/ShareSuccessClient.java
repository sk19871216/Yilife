package com.jiuan.android.app.yilife.bean.ShareSuccess;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ShareSuccessClient {
    public static RequestHandle request(Context context,int id, int type, ShareSuccessHandler responseHandler, boolean isTest) {
        ShareSuccessRequest helper = new ShareSuccessRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setId(id);
        helper.setType(type);


        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ShareSuccessRequest.PATH_TEST;
        if (!isTest) {
            path = ShareSuccessRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
