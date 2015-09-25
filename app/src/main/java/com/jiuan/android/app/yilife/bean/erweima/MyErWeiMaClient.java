package com.jiuan.android.app.yilife.bean.erweima;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MyErWeiMaClient {
    public static RequestHandle request(Context context,String un,String tooken, MyErWeiMaHandler responseHandler, boolean isTest) {
        MyErWeiMaRequest helper = new MyErWeiMaRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setUn(un);
        helper.setTooken(tooken);




        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = MyErWeiMaRequest.PATH_TEST;
        if (!isTest) {
            path = MyErWeiMaRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
