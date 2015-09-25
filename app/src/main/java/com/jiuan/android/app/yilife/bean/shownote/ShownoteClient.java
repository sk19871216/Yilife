package com.jiuan.android.app.yilife.bean.shownote;

import android.content.Context;

import com.innovation.android.library.http.HeadInfo;
import com.innovation.android.library.http.InnovationClient;
import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestHandle;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ShownoteClient {
    public static RequestHandle request(Context context,int topicid,int pageIndex,int pagesize, ShownoteHandler responseHandler, boolean isTest) {
        ShownoteRequest helper = new ShownoteRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context);
        builder.app("IEMyLife");
        builder.appID(Installation.id(context));
        helper.setHeadInfo(builder.build());
        helper.setTopicid(topicid);
        helper.setPageIndex(pageIndex);
        helper.setPagesize(pagesize);



        InnovationClient client = InnovationClient.getInstance();
        client.setSSLSocketFactory();

        String path = ShownoteRequest.PATH_TEST;
        if (!isTest) {
            path = ShownoteRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        InnovationClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
